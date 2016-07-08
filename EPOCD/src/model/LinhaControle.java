package model;

import java.util.Arrays;

public class LinhaControle {
	private static final int tamLinha = 39;
	private String funcao;
	//private static final int fimPortas = 29;
	//private static final int fimJump = 31;
	//private static final int fimProx = 32;
	//private static final int fimULA = 35;
	//private static final int fimRWAV = 38;
	//private static final int fimDECODE = 40;
	
	private byte[] linha;
	
	public LinhaControle(byte[] l, String funcao){
		if(l.length == tamLinha) linha =l;
		else linha = new byte[tamLinha];
		this.funcao = funcao;
	}
	
	public byte[] getLinha(){
		return linha;
	}
	
	public byte[] getPortas(){
		return Arrays.copyOfRange(linha, 0, 32);
	}
	
	public byte getJmpCond(){
		return linha[32];
	}
	
	public byte getProx(){
		return linha[33];
	}
	
	public byte getULA(){
		return linha[34];
	}
	
	public byte[] getRWAV(){
		return Arrays.copyOfRange(linha, 35, 38);
	}
	
	public byte getDecode(){
		return linha[38];
	}
	
}
