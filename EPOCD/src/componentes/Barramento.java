package componentes;

import halp.Observer;
import halp.Subject;
import model.Palavra;

import java.util.LinkedList;
import java.util.List;

//Podemos ter apenas duas instâncias de barramento, uma pra uc e registradores
//e outra pra acesso a memoria e tal
public class Barramento implements Subject{
	private Palavra palavraAtual;
	
	public Barramento(){	
		this.observers = new LinkedList<Observer>();
	}
	
	private List<Observer> observers;
	
	@Override
	public void addObserver(Observer o) {
		this.observers.add(o);
	}

	@Override
	public void notifyObservers() {
		for(Observer o : observers)
			o.notify(this);
	}

	public Palavra getPalavra() {
		return palavraAtual;
	}

	public void setPalavra(Palavra p) {
		palavraAtual = p;
		notifyObservers();
	}
}
