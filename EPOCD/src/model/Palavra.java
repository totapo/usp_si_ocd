package model;

import java.util.Arrays;

public class Palavra {
	//classe que representa uma palavra
	public static final int qtdBitsPalavra = 32;
	
	private byte[] bits;
	
	public Palavra(){
		this.bits = new byte[qtdBitsPalavra];
	}
	
	public Palavra(long resp) {
		this.bits = new byte[qtdBitsPalavra];
		String s = Long.toBinaryString(resp);
		int cont=31;
		for(int i=s.length()-1; i>=0 && cont>=0; i--){
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

	//usado para testar o limite de enderecamento com código
	/*
	public Palavra(boolean b) {
		bits = new byte[qtdBitsPalavra];
		for(int i=0; i<qtdBitsPalavra; i++) bits[i]=1;
	}
	 */
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
	
	//retorna uma String de bits baseada no array recebido
	public String bitString(byte[] bits){
		String r = "";
		for(byte b:bits)
			r+=b+"";
		return r;
	}
	
	public String toString(){
		return Arrays.toString(bits);
	}
	
	public int getIntValue(){
		long l= Long.parseLong(this.bitString(bits),2);
		return (int)l;
	}
}
