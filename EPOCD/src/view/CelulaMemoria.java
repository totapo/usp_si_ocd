package view;

public class CelulaMemoria {
	
	private int posicao;
	private int valor;
	
	public CelulaMemoria(int posicao, int valor){
		this.posicao = posicao;
		this.valor = valor;
	}
	
	public void setCelula(int posicao, int valor){
		this.posicao = posicao;
		this.valor = valor;
	}
	
	public int getPosicao(){
		return posicao;
	}
	
	public int getValor(){
		return valor;
	}

}
