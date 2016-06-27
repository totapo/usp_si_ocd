package model;

import java.util.Arrays;

public class LinhaControle {
	private static final int tamLinha = 37;
	//private static final int fimPortas = 29;
	//private static final int fimJump = 31;
	//private static final int fimProx = 32;
	//private static final int fimULA = 35;
	//private static final int fimRWAV = 38;
	//private static final int fimDECODE = 40;
	
	private byte[] linha;
	
	public LinhaControle(byte[] l){
		if(l.length == tamLinha) linha =l;
		else linha = new byte[tamLinha];
	}
	
	public byte[] getLinha(){
		return linha;
	}
	
	public byte[] getPortas(){
		return Arrays.copyOfRange(linha, 0, 30);
	}
	
	public byte getJmpCond(){
		return linha[30];
	}
	
	public byte getProx(){
		return linha[31];
	}
	
	public byte getULA(){
		return linha[32];
	}
	
	public byte[] getRWAV(){
		return Arrays.copyOfRange(linha, 33, 36);
	}
	
	public byte getDecode(){
		return linha[36];
	}
	
}
