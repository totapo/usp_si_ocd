package model;

public class Palavra {
	//TODO
	//SETAR A QTD BITS
	public static final int qtdBitsPalavra = 32;
	
	private byte[] bits;
	
	public Palavra(byte[] b){
		this.bits = b;
	}
	
	public byte[] getBits(){
		return bits;
	}
	
	public String bitString(){
		String r = "";
		for(byte b:bits)
			r+=b+"";
		return r;
	}
}
