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
	private boolean flagUpdate;
	
	private static Map<OpCode, byte[]> CodeCfgs; 
	/*cada byte[] dentro do mapa CodeCfgs tem na posição:
		0 - a linha do firmware onde comecam as microinstrucoes do seu ciclo de execução
		1 - se usa o ir da seguinte forma: 
		  0 = p1 pega td; 
		  1 = p1 pega reg e p2 pega resto; 
		  2 = p2 pega reg e p1 pega resto;
		  3 = p1 pega reg e p2 pega reg; 
		  4 = divide "igualmente" p1 e p2 
			ELE SEMPRE INTERPRETA A MEMORIA DESSE JEITO: OPCODE-REGS-RESTO, o que define onde vao as coisas eh esse valor na posicao 1 do array
		2 - se usa algum tipo de indirecao e qual
			0 = nao tem
			1 = tem indirecao tipo [num] no p2
			2 = tem indirecao tipo [reg] no p2
			3 = tem indirecao tipo [reg] no p1
			4 = comparar na mao = cheat code para os jumps
	*/
	
	private final byte busca = 0; //linha do ciclo de busca
	private final byte inNumP2 = 4; //linha do firmware onde tem indirecao pra num no p2 do ir
	private final byte inRegP2 = 8; //linha do firmware onde tem indirecao pra reg no p2 do ir
	private final byte inRegP1 = 9; //linha do firmware onde tem indirecao pra reg no p1 do ir
	private final byte execucao = 10; //linha onde começa o ciclo de execução
	
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
		CodeCfgs.put(new OpCode(new byte[]{0,1,0,1,0}),new byte[]{45,1,1}); //mov reg,[num]
		CodeCfgs.put(new OpCode(new byte[]{0,1,0,1,1}),new byte[]{45,3,2}); //mov reg,[reg]
		CodeCfgs.put(new OpCode(new byte[]{0,1,1,0,0}),new byte[]{46,4,0}); //mov [num],num
		CodeCfgs.put(new OpCode(new byte[]{0,1,1,0,1}),new byte[]{49,2,0}); //mov [num],reg  
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
	
	
	public UnidadeControle(IR ir, Collection<RegistradorUtilizavel> regs, Memoria mem, ULA ula){
		this.ir = ir;
		firm = new Firmware();
		this.mem = mem;
		this.ula = ula;
		this.observers = new LinkedList<Observer>();
		this.regsUtilizaveis = new HashMap<RegCode,RegistradorUtilizavel>();
		for(RegistradorUtilizavel i : regs){
			regsUtilizaveis.put(i.getID(),i);
		}
	}
	//retorna o ponteiro da instrução atual no firmware
	public int getPointer(){
		return firm.getPointer();
	}
	
	private void executeMicroInstruction() throws Exception{
		byte ponteiro;
		atual = firm.getInstruction();
		portas = atual.getPortas();
		
		//seta as portas que faltam de acordo com o decode q vem do firmware
		/*
		Significados Decode:
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
				RegistradorUtilizavel a = regsUtilizaveis.get(new RegCode(ir.getP1().getBits(29, 31)));
				if(a!=null){
					portas[Integer.parseInt(a.getCodigo().substring(4, 6))]=1; //saida p1
				}else throw new Exception("Registrador invalido: "+a);
				break;
			case 2:
				a = regsUtilizaveis.get(new RegCode(ir.getP1().getBits(29, 31)));
				if(a!=null){
					portas[Integer.parseInt(a.getCodigo().substring(1, 3))]=1; //entrada p1
				}else throw new Exception("Registrador invalido"+a);
				break;
			case 3:
				a = regsUtilizaveis.get(new RegCode(ir.getP2().getBits(29, 31)));
				if(a!=null){
					portas[Integer.parseInt(a.getCodigo().substring(4, 6))]=1; //saida p2
				}else throw new Exception("Registrador invalido"+a);
				break;
			case 4: 
				a = regsUtilizaveis.get(new RegCode(ir.getP2().getBits(29, 31)));
				if(a!=null){
					portas[Integer.parseInt(a.getCodigo().substring(1, 3))]=1; //entrada p2
				}else throw new Exception("Registrador invalido"+a);
				break;
			case 5: 
				a = regsUtilizaveis.get(new RegCode(ir.getP1().getBits(29, 31)));
				RegistradorUtilizavel b = regsUtilizaveis.get(new RegCode(ir.getP2().getBits(29, 31)));
				if(a!=null && b !=null){
					portas[Integer.parseInt(a.getCodigo().substring(1, 3))]=1; //entrada p1
					portas[Integer.parseInt(b.getCodigo().substring(4, 6))]=1; //saida p2
				}else throw new Exception("Registrador invalido"+a+" "+b);
				break;
			case 6: 
				a = regsUtilizaveis.get(new RegCode(ir.getP1().getBits(29, 31)));
				b = regsUtilizaveis.get(new RegCode(ir.getP2().getBits(29, 31)));
				if(a!=null && b !=null){
					portas[Integer.parseInt(b.getCodigo().substring(1, 3))]=1; //entrada p2
					portas[Integer.parseInt(a.getCodigo().substring(4, 6))]=1; //saida p1
				} else throw new Exception("Registrador invalido "+a+" "+b);
				break;
			default:break;
		}
		
		mem.setFlags(atual.getRWAV());		//manda as flags pra memoria
		
		flagUpdate=false;
		notifyObservers(); 					//notifica as portas que os valores delas podem ter mudado
		flagUpdate=true;
		notifyObservers(); 					//avisa as portas de saída que podem mexer com o barramento
		
		ula.setOperacao(atual.getULA(), firm.getPointer()!=0);	//manda codigo pra ula se tiver e, caso haja, ela ja calcula
		
		ponteiro = atual.getProx(); //seta a proxima linha do firmware que será executada

		operacao = ir.getOpCode();
		
		//dependendo do ponteiro atual ele deve pular para outros lugres
		if(ponteiro==-1){ //fim do ciclo de busca, verifica se tem indireção com base no opcode
			byte[] cfg = CodeCfgs.get(operacao);
			if(cfg==null) throw new Exception("OpCode invalido "+operacao);
			switch(cfg[2]){
				case 1: ponteiro = inNumP2; break;
				case 2: ponteiro = inRegP2; break;
				case 3: ponteiro = inRegP1; break;
				default: ponteiro = execucao;
			}
		} else if(ponteiro==-2){ //inicio do ciclo de execucao, determina pulo com base no opcode
			byte[] cfg = CodeCfgs.get(operacao); //pega as configuracoes definidas para a operacao atual
			if(cfg==null) throw new Exception("OpCode invalido "+operacao); //se não existirem joga uma exceção
			if(cfg[2]==4){ //verifica se o parâmetro 2 é 4 (cheat code pros jumps)
				ponteiro=cfg[0]; //seta o ponteiro como a operacao define
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
					default: 
						ponteiro=busca;
						break;
				}
			}else //caso contrário seta o ponteiro como a operação define
				ponteiro = cfg[0];
		} 

		firm.setPointer(ponteiro);
	}
	
	//executa uma microInstrucao
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

	//retorna se a porta com o id recebido deve estar aberta ou fechada
	public boolean getStatus(int id) {
		return portas[id]==1;
	}

	public static int getQtdRegs(OpCode opcode) {
		return CodeCfgs.get(opcode)[1];
	}
	
	public void reset(){
		this.firm.setPointer(0);
	}

	public boolean podeAtualizar() {
		return flagUpdate;
	}

}
