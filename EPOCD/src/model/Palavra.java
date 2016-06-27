package model;

public class Palavra {
	//TODO
	//SETAR A QTD BITS
	public static final int qtdBitsPalavra = 32;
	
	int[] bits;
	
	public Palavra(int[] b){
		this.bits = b;
	}
	
	public int[] getBits(){
		return bits;
	}
}
