package componentes;

import java.util.Arrays;

import model.OpCode;
import model.Palavra;
import model.UnidadeControle;

public class IR extends Registrador{
	private OpCode opcode;
	
	public IR(String nome) {
		super(nome);
	}
	
	@Override
	public void setMem(Palavra mem) {
		super.setMem(mem);
		opcode = new OpCode(mem); //5 primeiros bits
		if(UnidadeControle.getQtdRegs(opcode)==2){
			
		} else {
			
		}
		//bits = ;
	}

	private int intValue(){
		//Integer.
		return 0;
	}
	
	/*public int getP1(){
		return p1;
	}
	
	public int getP2(){
		return p2;
	}
	*/
	public OpCode getOpCode(){
		return opcode;
	}
}
