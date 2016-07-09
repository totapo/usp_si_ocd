package componentes;

import model.Componente;
import model.Palavra;

public class ULA implements Componente{
	//classe que representa a ULA
	
	//OPERAÇÕES
	public static final byte NDA=0;
	public static final byte ADD=1;
	public static final byte SUB=2; //sub num2 por num1
	public static final byte DIV=3; //divide num2 por num1
	public static final byte MUL=4; 
	public static final byte INC=5;
	public static final byte DEC=6;
	public static final byte MOD=7; //mod num2 por num1
	
	private byte[] flags; //so tem duas 0 e sinal
	
	private byte operacao;
	private Palavra num1, num2,resultado;
	private String codigo;
	
	public ULA(String codigo){
		this.codigo = codigo;
		num1 = new Palavra();
		num2 = new Palavra();
		resultado = new Palavra();
		flags = new byte[2];
	}
	
	public String getCodigo(){
		return codigo;
	}
	private boolean updateFlags; //utilizado para indicar se as flags devem ser alteradas ao termino do calculo ou não
	public void setOperacao(byte b, boolean updateFlags) throws Exception{
		if(b>=1 && b<=7){
			this.updateFlags = updateFlags;
			operacao = b;
			calc();
		}
	}
	
	public boolean flagZero(){ //true deu 0 false caso contrario 
		return flags[0]==1;
	}
	
	public boolean flagSignal(){ //true (positivo), false negativo
		return flags[1]==0;
	}
	
	public void setFlags(byte[] f){
		if(f.length==2){
			flags = f;
		}
	}

	//realiza o cálculo necessário e seta as flags
	private void calc() throws Exception{
		int a = num1.getIntValue();
		int b = num2.getIntValue();
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
		if(this.updateFlags){
			flags[0]=(byte)((resp==0)?1:0); //flag 0
			flags[1]=(byte)((resp>=0)?0:1); //flag sinal
		}
		resultado = new Palavra(resp);
	}

	@Override
	public void setPalavra(Palavra palavra, int idPorta) {
		if(idPorta==15)num2 = palavra; //se a porta foi a de id 15 quer dizer que o número vem do registrador X, caso contrário veio do barramento de registradores
		else num1 = palavra;
	}

	@Override
	public Palavra getPalavra() {
		return resultado;
	}
	
	public Palavra getNum1(){
		return num1;
	}
	
	public Palavra getNum2(){
		return num2;
	}

	@Override
	public void reset() {
		flags[0]=0;
		flags[1]=1;
		num1 = new Palavra();
		num2 = new Palavra();
		operacao=0;
		resultado = new Palavra();
	}
}
