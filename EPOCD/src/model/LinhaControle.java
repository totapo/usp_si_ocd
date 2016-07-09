package model;

import java.util.Arrays;

public class LinhaControle {
	//classe que representa uma linha de controle
	private static final int tamLinha = 39;
	private String funcao;
	
	private byte[] linha;
	
	public LinhaControle(byte[] l, String funcao){
		if(l.length == tamLinha) linha =l;
		else linha = new byte[tamLinha];
		this.funcao = funcao;
	}
	
	public String getDesc(){
		return funcao;
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
