package model;

import java.util.LinkedList;
import java.util.List;

import halp.Observer;
import halp.Subject;

public class UnidadeControle implements Subject{

	public UnidadeControle(){
		
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

}
