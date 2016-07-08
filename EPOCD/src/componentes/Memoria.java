package componentes;

import java.util.Map;
import java.util.TreeMap;

import model.Componente;
import model.Palavra;

public class Memoria extends Componente{
	boolean av,w,r,endOk;
	Map<Long,Palavra>palavras;
	
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
	
	public void setFlags(byte[] flags){
		if(flags.length==3){
			r  = flags[0]==1;
			w  = flags[1]==1;
			av = flags[2]==1;
			//System.out.println("R "+r+" W "+w+" AV "+av);
		}
	}

	@Override
	public void setPalavra(Palavra palavra, int idPorta) {
		if(av && !endOk){
			address = palavra.getIntValue();
			endOk = true; //pra habilitar o write e o read
		} else if(w && av){
			valor = palavra;
			palavras.put(address,valor);
			endOk=false;
		}
	}

	@Override
	public Palavra getPalavra() {
		if(av && endOk && r){
			valor = palavras.get(address);
		}
		endOk=false;
		if(valor==null) return new Palavra();
		return valor;
	}
	
	public void insere(long posicao, Palavra val){
		palavras.put(posicao,val);
	}
	
}
