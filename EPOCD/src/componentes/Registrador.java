package componentes;
import java.util.LinkedList;
import java.util.List;

import halp.Observer;
import halp.Subject;
import model.Componente;
import model.Palavra;

public class Registrador extends Componente implements Subject{
	private String nome;
	private String codigo;
	private Palavra mem;
	
	private List<Observer> observers;
	
	public Registrador(String nome, String codigo){
		this.nome = nome;
		this.codigo = codigo;
		observers = new LinkedList<Observer>();
	}

	public String getNome() {
		return nome;
	}
	
	public String getCodigo(){
		return codigo;
	}
	
	@Override
	public String toString() {
		return nome;
	}

	@Override
	public void setPalavra(Palavra palavra,int idPorta) {
		this.mem = palavra;
		notifyObservers();
	}

	@Override
	public Palavra getPalavra() {
		return mem;
	}

	@Override
	public void addObserver(Observer o) {
		observers.add(o);
	}

	@Override
	public void notifyObservers() {
		for(Observer o : observers)
			o.notify(this);
	}
	
	
}
