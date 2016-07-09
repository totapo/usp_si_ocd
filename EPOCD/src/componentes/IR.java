package componentes;

import model.OpCode;
import model.Palavra;

public class IR extends Registrador{
	//classe que representa o Instruction Register, possui outros dois registradores (p1 e p2) que guardam os parâmetros 
	//da instrução atual
	private OpCode opcode;
	private Registrador p1,p2;
	
	public IR(String nome, String codigo, Registrador p1, Registrador p2) {
		super(nome, codigo);
		this.p1 = p1;
		this.p2 = p2;
	}
	
	//quando o IR recebe uma palavra ele tem que dividir os bits entre p1 e p2 dependendo do opcode
	//por esse motivo ele dá override no setPalavra de registrador
	@Override
	public void setPalavra(Palavra mem, int idPorta) {
		super.setPalavra(mem, idPorta);
		opcode = new OpCode(mem); //5 primeiros bits sempre representam o opcode
		try{
			switch(UnidadeControle.getQtdRegs(opcode)){
			case 0: 
				//significa que há apenas um número como parâmetro, e ele pode ocupar todo o resto da instrucao
				p1.setPalavra(new Palavra((int)Long.parseLong(parse(mem.getBits(5,Palavra.qtdBitsPalavra-1)),2)),-1);
				break;
			case 1: 
				//significa que há um código de registrador (3 bits) na instrução que deve ficar no p1, o p2 é um número que fica nos bits restantes
				p1.setPalavra(new Palavra(mem,5,7), -1);
				p2.setPalavra(new Palavra((int)Long.parseLong(parse(mem.getBits(8,Palavra.qtdBitsPalavra-1)),2)),-1);
				break;
			case 2:
				//significa que há um código de registrador (3 bits) na instrução que deve ficar no p2, o p1 é um número que fica nos bits restantes
				p2.setPalavra(new Palavra(mem,5,7), -1);
				p1.setPalavra(new Palavra((int)Long.parseLong(parse(mem.getBits(8,Palavra.qtdBitsPalavra-1)),2)),-1);
				break;
			case 3: 
				//significa que há dois registradores na instruçao, o resto dos bits é ignorado
				p1.setPalavra(new Palavra(mem,5,7), -1);
				p2.setPalavra(new Palavra(mem,8,10), -1);
				break;
			case 4:
				//significa que há dois números na instrução, assim sendo ele tenta dividir o espaço restante (32-5=27) entre eles (p1 e p2) 
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
	
	private String parse(byte[] bits) { //cria uma string de 32 bits que representa o número indicado no array de bytes
		//assume que se bits[0] é 1, o numero é negativo
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
