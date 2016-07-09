package componentes;

import java.util.Map;
import java.util.TreeMap;

import model.Componente;
import model.Palavra;

public class Memoria implements Componente{
	//classe que representa a memória principal do computador
	boolean av,w,r,endOk; //flags
	Map<Long,Palavra>palavras; //memória em si
	
	private long address; 
	private Palavra valor;
	private String cod;
	
	public Memoria(String cod){
		this.palavras = new TreeMap<Long,Palavra>();
		av = r = w = endOk = false;
		this.cod = cod;
	}
	
	public String getCodigo(){
		return cod;
	}
	
	public Map<Long,Palavra> getMap(){
		return palavras;
	}
	
	//utilizado pela UC para indicar o que está ou não habilitado
	public void setFlags(byte[] flags){
		if(flags.length==3){
			r  = flags[0]==1;
			w  = flags[1]==1;
			av = flags[2]==1;
		}
	}

	@Override
	public void setPalavra(Palavra palavra, int idPorta) {
		if(av && !endOk){ //se enviaram alguma coisa e o endereço ainda não foi setado, seta o endereço
			//int a = palavra.getIntValue();
			//if(a<0) address = Long.parseLong(palavra.bitString(palavra.getBits()),2);
			address=palavra.getIntValue();;
			endOk = true; //pra habilitar o write e o read
		} else if(w && av){ //se enviaram alguma coisa e endOk, insere a palavra na memoria
			valor = palavra;
			if(address>=0) //não aceita address negativo
				palavras.put(address,valor);
			endOk=false;
		}
	}

	@Override
	public Palavra getPalavra() {
		if(av && endOk && r){ //se pediram alguma coisa e já enviaram algum endereço, procura o valor dele na memória
			valor = palavras.get(address);
		}
		endOk=false; //seta endOk pra false
		if(valor==null && address >=0) return new Palavra(); //não aceita address negativo
		return valor;
	}
	
	//utilizado pela Controller (traduzir as instrucoes do textArea)
	public void insere(long posicao, Palavra val){
		palavras.put(posicao,val);
	}

	@Override
	public void reset() {
		av = r = w = false;
		this.palavras.clear();
	}
	
}
