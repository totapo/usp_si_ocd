package componentes;

import model.OpCode;
import model.Palavra;
import model.UnidadeControle;

public class IR extends Registrador{
	private OpCode opcode;
	
	public IR(String nome, String codigo) {
		super(nome, codigo);
	}
	
	@Override
	public void setPalavra(Palavra mem, int idPorta) {
		super.setPalavra(mem, idPorta);
		opcode = new OpCode(mem); //5 primeiros bits
		switch(UnidadeControle.getQtdRegs(opcode)){
		case 0:
			
			break;
		case 1:
			break;
		case 2:
			break;
		case 3:
			break;
		case 4:
			break;
		default:
			break;
		}
		//bits = ;
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
