package componentes;

import java.util.Arrays;

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
		try{
			switch(UnidadeControle.getQtdRegs(opcode)){
			case 0:
				p1.setPalavra(new Palavra((int)Long.parseLong(parse(mem.getBits(5,Palavra.qtdBitsPalavra-1)),2)),-1);
				//p1.setPalavra(new Palavra(mem,5,Palavra.qtdBitsPalavra-1), -1);
				break;
			case 1:
				p1.setPalavra(new Palavra(mem,5,7), -1);
				p2.setPalavra(new Palavra((int)Long.parseLong(parse(mem.getBits(8,Palavra.qtdBitsPalavra-1)),2)),-1);
				break;
			case 2:
				p2.setPalavra(new Palavra(mem,5,7), -1);
				p1.setPalavra(new Palavra((int)Long.parseLong(parse(mem.getBits(8,Palavra.qtdBitsPalavra-1)),2)),-1);
				break;
			case 3:
				p1.setPalavra(new Palavra(mem,5,7), -1);
				p2.setPalavra(new Palavra(mem,8,10), -1);
				break;
			case 4:
				int fim = ((Palavra.qtdBitsPalavra-5)/2)+4;
				p1.setPalavra(new Palavra((int)Long.parseLong(parse(mem.getBits(5,fim)),2)),-1);
				p2.setPalavra(new Palavra((int)Long.parseLong(parse(mem.getBits(fim+1,Palavra.qtdBitsPalavra-1)),2)),-1);
				break;
			default:
				break;
			}
		}catch(Exception e){
			e.printStackTrace();
		}
	}
	
	private String parse(byte[] bits) { //assume que se bits[0] é 1, o numero é negativo
		String resp="";
		if(bits.length>0){
			int size = 32;
			for(int i=bits.length-1; i>=0; i--){
				resp=bits[i]+resp;
				size--;
			}
			while(size>0){
				resp=((bits[0]==1)?"1":"0")+resp;
				size--;
			}
		}
		return resp;
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
