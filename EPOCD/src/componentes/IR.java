package componentes;

import model.OpCode;
import model.Palavra;

public class IR extends Registrador{
	private OpCode opcode;
	private Registrador p1,p2;
	
	public IR(String nome, String codigo, Registrador p1, Registrador p2) {
		super(nome, codigo);
		this.p1 = p1;
		this.p2 = p2;
	}
	
	@Override
	public void setPalavra(Palavra mem, int idPorta) {
		super.setPalavra(mem, idPorta);
		opcode = new OpCode(mem); //5 primeiros bits
		switch(UnidadeControle.getQtdRegs(opcode)){
		case 0:
			p1.setPalavra(mem, -1);
			break;
		case 1:
			p1.setPalavra(new Palavra(mem,0,2), -1);
			p2.setPalavra(new Palavra(mem,3,Palavra.qtdBitsPalavra-1), -1);
			break;
		case 2:
			p2.setPalavra(new Palavra(mem,0,2), -1);
			p1.setPalavra(new Palavra(mem,3,Palavra.qtdBitsPalavra-1), -1);
			break;
		case 3:
			p1.setPalavra(new Palavra(mem,0,2), -1);
			p2.setPalavra(new Palavra(mem,3,5), -1);
			break;
		case 4:
			int fim = (Palavra.qtdBitsPalavra/2)-1;
			p1.setPalavra(new Palavra(mem,0,fim), -1);
			p2.setPalavra(new Palavra(mem,fim+1,Palavra.qtdBitsPalavra-1), -1);
			break;
		default:
			break;
		}
	}
	
	public Palavra getP1(){
		return p1.getPalavra();
	}
	
	public Palavra getP2(){
		return p2.getPalavra();
	}
	
	public OpCode getOpCode(){
		return opcode;
	}
}
