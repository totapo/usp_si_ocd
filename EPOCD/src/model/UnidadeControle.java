package model;

import java.util.LinkedList;
import java.util.List;

import halp.Observer;
import halp.Subject;

public class UnidadeControle implements Subject{
	private Firmware firm;
	
	public UnidadeControle(){
		firm = new Firmware();
		this.observers = new LinkedList<Observer>();
	}
	
	private void executeMicroInstruction(){
		
	}
	
	static int convertToInt(boolean[] a){
		int x = 0;
		for(int i=a.length-1; i>=0; i--){
			x+=Math.pow(2, i);
		}
		return x;
	}
	
	static boolean[] convertToBit(int a){
		//boolean[] r = 
		return null;
	}
	
	public void advanceClock(){
		executeMicroInstruction();
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
