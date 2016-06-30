package model;

import java.util.Arrays;

public class Palavra {
	//TODO
	//SETAR A QTD BITS
	public static final int qtdBitsPalavra = 32;
	
	private byte[] bits;
	
	public Palavra(){
		this.bits = new byte[qtdBitsPalavra];
	}
	
	public Palavra(int resp) {
		this.bits = new byte[qtdBitsPalavra];
		String s = Integer.toBinaryString(resp);
		System.out.println(resp+"\n"+s);
		int cont=31;
		for(int i=s.length()-1; i>=0; i--){
			bits[cont--] = (byte) ((s.charAt(i)=='1')?1:0);
		}
	}
	
	public Palavra(Palavra a, int from, int to) { //from and to inclusive
		this.bits = new byte[qtdBitsPalavra];
		if(from >=0 && from < 32 && from <=to && to>=0 && to<=32){
			int b=31;
			for(int i=to; i>=from; i--){
				bits[b] = a.bits[i];
				b--;
			}
		}
	}

	public Palavra(byte[] p) {
		if(p.length==32) bits=p;
	}

	public void setValue(byte[] b){
		if(b.length>=32)
			for(int i=0; i<32; i++){
				bits[i]=b[i];
			}
	}
	
	public byte[] getBits(){
		return bits;
	}
	
	public byte[] getBits(int from,int to){ //both inclusive
		return Arrays.copyOfRange(bits, from, to+1);
	}
	
	private String bitString(){
		String r = "";
		for(byte b:bits)
			r+=b+"";
		return r;
	}
	
	public String toString(){
		return Arrays.toString(bits);
	}
	
	public int getIntValue(){
		return Integer.parseInt(this.bitString(),2);
	}
}
