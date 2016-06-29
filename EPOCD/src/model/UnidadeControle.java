package model;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import componentes.IR;
import componentes.Memoria;
import componentes.ULA;
import halp.Observer;
import halp.Subject;

public class UnidadeControle implements Subject{
	private Firmware firm;
	private LinhaControle atual;
	private byte[] portas;
	private IR ir;
	private OpCode operacao;
	private Memoria mem;
	private ULA ula;
	byte indirecao;
	
	private static Map<OpCode, byte[]> CodeCfgs; 
	//0 é a linha do firmware onde comecam as microinstrucoes
	//1 é se usa o ir da seguinte forma: ELE SEMPRE INTERPRETA A MEMORIA DESSE JEITO: OPCODE-REGS-RESTO, o que define onde vao as coisas eh esse valor
	//  0 = p1 pega td; 
	//  1 = p1 pega reg e p2 pega resto; 
	//  2 = p2 pega reg e p1 pega resto;
	//  3 = p1 pega reg e p2 pega reg; 
	//  4 = divide "igualmente" p1 e p2 
	//2 é se usa algum tipo de indirecao e qual
	//	0 - nao tem
	//	1 - tem indirecao tipo [num] no p2
	//	2 - tem indirecao tipo [reg] no p2
	//	3 - tem indirecao tipo [reg] no p1
	//	4 - comparar na mao = cheat code
	
	//Usarei isso eventualmente, confia
	private final byte inNumP2 = 4; //linha do firmware onde tem indirecao pra num no p2 do ir
	private final byte inRegP2 = 8; //linha do firmware onde tem indirecao pra reg no p2 do ir
	private final byte inRegP1 = 9; //linha do firmware onde tem indirecao pra reg no p1 do ir
	
	private int[] flags;
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
		CodeCfgs.put(new OpCode(new byte[]{1,0,0,0,1}),new byte[]{50,0,4}); //jl
		CodeCfgs.put(new OpCode(new byte[]{1,0,0,1,0}),new byte[]{50,0,4}); //jle
		CodeCfgs.put(new OpCode(new byte[]{1,0,0,1,1}),new byte[]{50,0,4}); //jg
		CodeCfgs.put(new OpCode(new byte[]{1,0,1,0,0}),new byte[]{50,0,4}); //jge
		CodeCfgs.put(new OpCode(new byte[]{1,0,1,0,1}),new byte[]{50,0,4}); //jz
		CodeCfgs.put(new OpCode(new byte[]{1,0,1,1,0}),new byte[]{50,0,4}); //jnz
		CodeCfgs.put(new OpCode(new byte[]{1,0,1,1,1}),new byte[]{ 0,4,4}); //cmp num,num
		CodeCfgs.put(new OpCode(new byte[]{1,1,0,0,0}),new byte[]{ 0,1,4}); //cmp reg,num
		CodeCfgs.put(new OpCode(new byte[]{1,1,0,0,1}),new byte[]{ 0,2,4}); //cmp num,reg
		CodeCfgs.put(new OpCode(new byte[]{1,1,0,0,1}),new byte[]{ 0,3,4}); //cmp reg,reg
	}
	
	
	public UnidadeControle(IR ir){
		this.ir = ir;
		firm = new Firmware();
		this.observers = new LinkedList<Observer>();
	}
	
	private void executeMicroInstruction() throws Exception{
		byte ponteiro;
		atual = firm.getInstruction();
		portas = atual.getPortas();
		
		if(atual.getDecode()!=0){ //seta as portas que faltam de acordo com o decode q vem do firmware
			switch(atual.getDecode()){
			case 1: 
				
				break;
			case 2: 
				break;
			case 3: 
				break;
			case 4: 
				break;
			case 5: 
				break;
			case 6: 
				break;
			default:break;
			}
		}
		
		mem.setFlags(atual.getRWAV());
		
		notifyObservers(); 					//notifica as portas que os valores delas podem ter mudado e elas se atualizam e mexem com o barramento
		
		ula.setOperacao(atual.getULA());	//manda codigo pra ula se tiver
		
		ponteiro = atual.getProx();
		if(ponteiro==-1){ 					//ve se tem indirecao na instrucao do ir
			
		} else if(ponteiro==-2){ 			//determina pulo pelo opcode do ir
			
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
