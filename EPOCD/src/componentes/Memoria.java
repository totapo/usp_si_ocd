package componentes;

import java.util.HashMap;
import java.util.Map;

import model.Componente;
import model.Palavra;

public class Memoria extends Componente{
	boolean av,w,r,endOk;
	Map<Integer,Palavra>palavras;
	
	private int address;
	private Palavra valor;
	private String cod;
	
	public Memoria(String cod){
		this.palavras = new HashMap<Integer,Palavra>();
		av = r = w = endOk = false;
		this.cod = cod;
	}
	
	public String getCodigo(){
		return cod;
	}
	
	public void setFlags(byte[] flags){
		if(flags.length==3){
			r  = flags[0]==1;
			w  = flags[1]==1;
			av = flags[2]==1;
		}
	}

	@Override
	public void setPalavra(Palavra palavra, int idPorta) {
		if(av && !endOk){
			address = palavra.getIntValue();
			endOk = true; //pra habilitar o write
		} else if(w){
			valor = palavra;
			palavras.put(address,valor);
			endOk=false;
		}
	}

	@Override
	public Palavra getPalavra() {
		if(av && endOk && r){
			valor = palavras.get(address);
			endOk=false;
		}
		if(valor==null) return new Palavra();
		return valor;
	}
	
	
	
}
