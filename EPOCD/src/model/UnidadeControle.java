package model;

import java.util.LinkedList;
import java.util.List;

import halp.Observer;
import halp.Subject;

public class UnidadeControle implements Subject{
	private Firmware firm;
	private LinhaControle atual;
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
		String b = Integer.toBinaryString(a);
		boolean[] resp = new boolean[b.length()];
		for(int i=0; i<resp.length; i++){
			resp[i]=b.charAt(i)=='1';
		}
		return resp;
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

	public boolean getStatus(int id) {
		return atual.getLinha()[id]==1;
	}

}
