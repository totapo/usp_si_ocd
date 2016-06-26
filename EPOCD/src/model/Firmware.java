package model;

public class Firmware {
	static final Palavra [] instrucoes;
	private int pointer;
	
	static {
		instrucoes = new Palavra[90]; //n sei qntas
		
	}
	
	public Firmware(){
		this.pointer = 0;
	}
	
	//a UC recebe a instrucao pra dar jump no poiter quando um ciclo acaba
	public void setPointer(int p){
		this.pointer = p;
	}
	
	public void getInstruction(){
		
	}

	
}
