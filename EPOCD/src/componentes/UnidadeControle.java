package componentes;

import java.util.Collection;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import halp.Observer;
import halp.Subject;
import model.LinhaControle;
import model.OpCode;
import model.RegCode;

public class UnidadeControle implements Subject{
	private Firmware firm;
	private LinhaControle atual;
	private byte[] portas;
	private Map<RegCode,RegistradorUtilizavel> regsUtilizaveis;
	private IR ir;
	private OpCode operacao;
	private Memoria mem;
	private ULA ula;
	byte indirecao;
	
	private static Map<OpCode, byte[]> CodeCfgs; 
	//0 eh a linha do firmware onde comecam as microinstrucoes
	//1 eh se usa o ir da seguinte forma: ELE SEMPRE INTERPRETA A MEMORIA DESSE JEITO: OPCODE-REGS-RESTO, o que define onde vao as coisas eh esse valor
	//  0 = p1 pega td; 
	//  1 = p1 pega reg e p2 pega resto; 
	//  2 = p2 pega reg e p1 pega resto;
	//  3 = p1 pega reg e p2 pega reg; 
	//  4 = divide "igualmente" p1 e p2 
	//2 eh se usa algum tipo de indirecao e qual
	//	0 - nao tem
	//	1 - tem indirecao tipo [num] no p2
	//	2 - tem indirecao tipo [reg] no p2
	//	3 - tem indirecao tipo [reg] no p1
	//	4 - comparar na mao = cheat code
	
	//Usarei isso eventualmente, confia
	private final byte busca = 0;
	private final byte inNumP2 = 4; //linha do firmware onde tem indirecao pra num no p2 do ir
	private final byte inRegP2 = 8; //linha do firmware onde tem indirecao pra reg no p2 do ir
	private final byte inRegP1 = 9; //linha do firmware onde tem indirecao pra reg no p1 do ir
	private final byte execucao = 10;
	
	static{
		CodeCfgs = new HashMap<OpCode,byte[]>();
		CodeCfgs.put(new OpCode(new byte[]{0,0,0,0,0}),new byte[]{11,3,0}); //add reg,reg
		CodeCfgs.put(new OpCode(new byte[]{0,0,0,0,1}),new byte[]{15,3,0}); //sub reg,reg
		CodeCfgs.put(new OpCode(new byte[]{0,0,0,1,0}),new byte[]{19,3,0}); //mov reg,reg
		CodeCfgs.put(new OpCode(new byte[]{0,0,0,1,1}),new byte[]{20,1,0}); //mul reg
		CodeCfgs.put(new OpCode(new byte[]{0,0,1,0,0}),new byte[]{24,1,0}); //div reg
		CodeCfgs.put(new OpCode(new byte[]{0,0,1,0,1}),new byte[]{31,1,0}); //inc reg
		CodeCfgs.put(new OpCode(new byte[]{0,0,1,1,0}),new byte[]{34,1,0}); //dec reg
		CodeCfgs.put(new OpCode(new byte[]{0,0,1,1,1}),new byte[]{37,1,0}); //add reg,num
		CodeCfgs.put(new OpCode(new byte[]{0,1,0,0,0}),new byte[]{41,1,0}); //sub reg.num
		CodeCfgs.put(new OpCode(new byte[]{0,1,0,0,1}),new byte[]{45,1,0}); //mov reg,num
		CodeCfgs.put(new OpCode(new byte[]{0,1,0,1,0}),new byte[]{51,1,1}); //mov reg,[num]
		CodeCfgs.put(new OpCode(new byte[]{0,1,0,1,1}),new byte[]{51,3,2}); //mov reg,[reg]
		CodeCfgs.put(new OpCode(new byte[]{0,1,1,0,0}),new byte[]{46,4,0}); //mov [num],num
		CodeCfgs.put(new OpCode(new byte[]{0,1,1,0,1}),new byte[]{49,2,0}); //mov [num],reg  - na memoria fica opcode-reg-num, isso facilita as coisas
		CodeCfgs.put(new OpCode(new byte[]{0,1,1,1,0}),new byte[]{49,3,3}); //mov [reg].reg 
		CodeCfgs.put(new OpCode(new byte[]{0,1,1,1,1}),new byte[]{46,1,3}); //mov [reg],num
		CodeCfgs.put(new OpCode(new byte[]{1,0,0,0,0}),new byte[]{50,0,0}); //jmp num
		CodeCfgs.put(new OpCode(new byte[]{1,0,0,0,1}),new byte[]{50,0,4, 0}); //jl
		CodeCfgs.put(new OpCode(new byte[]{1,0,0,1,0}),new byte[]{50,0,4, 1}); //jle
		CodeCfgs.put(new OpCode(new byte[]{1,0,0,1,1}),new byte[]{50,0,4, 2}); //jg
		CodeCfgs.put(new OpCode(new byte[]{1,0,1,0,0}),new byte[]{50,0,4, 3}); //jge
		CodeCfgs.put(new OpCode(new byte[]{1,0,1,0,1}),new byte[]{50,0,4, 4}); //jz
		CodeCfgs.put(new OpCode(new byte[]{1,0,1,1,0}),new byte[]{50,0,4, 5}); //jnz
		CodeCfgs.put(new OpCode(new byte[]{1,0,1,1,1}),new byte[]{55,4,0}); //cmp num,num
		CodeCfgs.put(new OpCode(new byte[]{1,1,0,0,0}),new byte[]{57,1,0}); //cmp reg,num
		CodeCfgs.put(new OpCode(new byte[]{1,1,0,0,1}),new byte[]{59,2,0}); //cmp num,reg
		CodeCfgs.put(new OpCode(new byte[]{1,1,0,1,0}),new byte[]{61,3,0}); //cmp reg,reg
		CodeCfgs.put(new OpCode(new byte[]{1,1,0,1,1}),new byte[]{63,0,0}); //mul num
		CodeCfgs.put(new OpCode(new byte[]{1,1,1,0,0}),new byte[]{65,0,0}); //div num
	}
	
	
	public UnidadeControle(IR ir, Collection<RegistradorUtilizavel> regs){
		this.ir = ir;
		firm = new Firmware();
		this.observers = new LinkedList<Observer>();
		this.regsUtilizaveis = new HashMap<RegCode,RegistradorUtilizavel>();
		for(RegistradorUtilizavel i : regs){
			regsUtilizaveis.put(i.getID(),i);
		}
	}
	
	private void executeMicroInstruction() throws Exception{
		byte ponteiro;
		atual = firm.getInstruction();
		portas = atual.getPortas();
		operacao = ir.getOpCode();
		//seta as portas que faltam de acordo com o decode q vem do firmware

		
		/*
		Nada 0
		Saida p1 1
		Entrada p1 2
		Saida p2 3
		Entrada p2 4
		Entrada p1 saida p2 5
		Entrada p2 saida p1 6
		*/
		switch(atual.getDecode()){
			case 1: 
				RegistradorUtilizavel a = regsUtilizaveis.get(new RegCode(ir.getP1().getBits(0, 3)));
				if(a!=null){
					portas[Integer.parseInt(a.getCodigo().substring(4, 6))]=1; //saida p1
				}else throw new Exception("Registrador invalido");
				break;
			case 2:
				a = regsUtilizaveis.get(new RegCode(ir.getP1().getBits(0, 3)));
				if(a!=null){
					portas[Integer.parseInt(a.getCodigo().substring(1, 3))]=1; //entrada p1
				}else throw new Exception("Registrador invalido");
				break;
			case 3:
				a = regsUtilizaveis.get(new RegCode(ir.getP2().getBits(0, 3)));
				if(a!=null){
					portas[Integer.parseInt(a.getCodigo().substring(4, 6))]=1; //saida p2
				}else throw new Exception("Registrador invalido");
				break;
			case 4: 
				a = regsUtilizaveis.get(new RegCode(ir.getP2().getBits(0, 3)));
				if(a!=null){
					portas[Integer.parseInt(a.getCodigo().substring(1, 3))]=1; //entrada p2
				}else throw new Exception("Registrador invalido");
				break;
			case 5: 
				a = regsUtilizaveis.get(new RegCode(ir.getP1().getBits(0, 3)));
				RegistradorUtilizavel b = regsUtilizaveis.get(new RegCode(ir.getP2().getBits(0, 3)));
				if(a!=null && b !=null){
					portas[Integer.parseInt(a.getCodigo().substring(1, 3))]=1; //entrada p1
					portas[Integer.parseInt(b.getCodigo().substring(4, 6))]=1; //saida p2
				}else throw new Exception("Registrador invalido");
				break;
			case 6: 
				a = regsUtilizaveis.get(new RegCode(ir.getP1().getBits(0, 3)));
				b = regsUtilizaveis.get(new RegCode(ir.getP2().getBits(0, 3)));
				if(a!=null && b !=null){
					portas[Integer.parseInt(b.getCodigo().substring(1, 3))]=1; //entrada p2
					portas[Integer.parseInt(a.getCodigo().substring(4, 6))]=1; //saida p1
				} else throw new Exception("Registrador invalido");
				break;
			default:break;
		}
		
		mem.setFlags(atual.getRWAV());		//manda as flags pra memoria
		
		notifyObservers(); 					//notifica as portas que os valores delas podem ter mudado e elas se atualizam e mexem com o barramento
		
		ula.setOperacao(atual.getULA());	//manda codigo pra ula se tiver e, caso haja, ela ja calcula
		
		
		ponteiro = atual.getProx(); //seta a proxima linha do firmware que será executada

		byte[] cfg = CodeCfgs.get(operacao.getCode());
		if(ponteiro==-1){ //ve se tem indirecao na instrucao do ir
			if(cfg==null) throw new Exception("OpCode invalido "+operacao.getCode());
			switch(cfg[2]){
				case 1: ponteiro = inNumP2; break;
				case 2: ponteiro = inRegP2; break;
				case 3: ponteiro = inRegP1; break;
				default: ponteiro = execucao;
			}
		} else if(ponteiro==-2){ 			//determina pulo pelo opcode do ir
			if(cfg==null) throw new Exception("OpCode invalido "+operacao.getCode());
			if(cfg[2]==4)
				switch(cfg[3]){ //se as condicoes do jump nao foram atingidas comeca o ciclo de busca normalmente
					case 0: //jl
						if(ula.flagSignal()) ponteiro = busca;
						break;
					case 1: //jle
						if(ula.flagSignal() && !ula.flagZero()) ponteiro = busca;
						break;
					case 2: //jg 
						if(!ula.flagSignal() && !ula.flagZero()) ponteiro = busca;
						break;
					case 3: //jge 
						if(!ula.flagSignal()) ponteiro = busca;
						break;
					case 4: //jz 
						if(!ula.flagZero()) ponteiro = busca;
						break;
					case 5: //jnz 
						if(ula.flagZero()) ponteiro = busca;
						break;
					default: break;
				}
			else
				ponteiro = cfg[0];
		} 

		firm.setPointer(ponteiro);
	}
	
	public void advanceClock() throws Exception{
		executeMicroInstruction();
	}
	
	private List<Observer> observers;
	
	@Override
	public void addObserver(Observer o) {
		this.observers.add(o);
	}

	@Override
	public void notifyObservers() {
		for(Observer o : observers)
			o.notify(this);
	}

	public boolean getStatus(int id) {
		return portas[id]==1;
	}

	public static int getQtdRegs(OpCode opcode) {
		return CodeCfgs.get(opcode)[1];
	}

}
