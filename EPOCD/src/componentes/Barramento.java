package componentes;

import halp.Observer;
import halp.Subject;
import model.Palavra;

import java.util.LinkedList;
import java.util.List;

public class Barramento implements Subject{
	//classe que representa um barramento, pelo qual passa somente uma palavra
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

	//a ideia é que, quando alguém colocar algo no barramento, ele notifique todos os seus
	//observadores (portas)
	public void setPalavra(Palavra p) {
		palavraAtual = p;
		notifyObservers();
	}
}
