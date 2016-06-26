package model;

public class Palavra {
	//TODO
	//SETAR A QTD BITS
	public static final int qtdBitsPalavra = 0;
	
	boolean[] bits;
	
	public Palavra(boolean [] b){
		this.bits = b;
	}
	
	public boolean[] getBits(){
		return bits;
	}
}
