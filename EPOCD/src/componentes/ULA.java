package componentes;

import model.Componente;
import model.Palavra;

public class ULA implements Componente{
	public static final byte NDA=0;
	public static final byte ADD=1;
	public static final byte SUB=2; //sub num2 por num1
	public static final byte DIV=3; //divide num2 por num1
	public static final byte MUL=4; 
	public static final byte INC=5;
	public static final byte DEC=6;
	public static final byte MOD=7; //mod num2 por num1
	
	private byte operacao;
	private Palavra num1, num2,resultado;
	private String codigo;
	
	public ULA(String codigo){
		this.codigo = codigo;
		num1 = new Palavra();
		num2 = new Palavra();
		resultado = new Palavra();
	}
	
	public String getCodigo(){
		return codigo;
	}
	
	public void setOperacao(byte b) throws Exception{
		if(b>=0 && b<=7){
			operacao = b;
			calc();
		}
	}

	private void calc() throws Exception{
		int a = Integer.parseInt(num1.bitString());
		int b = Integer.parseInt(num2.bitString());
		int resp=0;
		switch(operacao){
		case ADD: resp = a+b; break;
		case SUB: resp = b-a; break;
		case DIV: 
			if(a!=0)resp = b/a;
			else throw new Exception("Divisao por 0");
			break;
		case MUL: resp = a*b; break;
		case INC: resp = a+1; break;
		case DEC: resp = a-1; break;
		case MOD: 
			if(a!=0)resp = b%a;
			else throw new Exception("Divisao por 0");
			break;
		case NDA:
		default:break;
		}
		resultado = new Palavra(resp);
	}

	@Override
	public void setPalavra(Palavra palavra, int idPorta) { //utilizada pela porta
		if(idPorta==15)num2 = palavra;
		else num1 = palavra;
	}

	@Override
	public Palavra getPalavra() {
		return resultado;
	}
}
