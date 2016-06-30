package componentes;

import java.util.LinkedList;
import java.util.List;

import halp.Observer;
import halp.Subject;
import model.Componente;


public class Porta implements Observer, Subject{ 
	//Subject eh pra caso a gnt queira mudar algo na tela qnd abre e fecha a porta
	private boolean aberta;
	private boolean in;
	private Barramento barramento;
	private Componente c;
	private int id;
	
	public Porta(boolean in, int id, Barramento b, Componente c){
		this.c = c;
		this.in = in;
		this.id=id;
		this.barramento = b;
		this.obs = new LinkedList<Observer>();
	}
	

	@Override
	public void notify(Subject s) {
		if(s instanceof UnidadeControle){
			this.aberta = ((UnidadeControle) s).getStatus(id);
			if(aberta){
				if(in) c.setPalavra(barramento.getPalavra(),id);
				else barramento.setPalavra(c.getPalavra());
			}
			notifyObservers();
		} else if(s instanceof Barramento){
			if(aberta && in){
				c.setPalavra(barramento.getPalavra(),id);
			}
		}
	}

	private List<Observer> obs;
	@Override
	public void addObserver(Observer o) {
		obs.add(o);
	}

	@Override
	public void notifyObservers() {
		for(Observer o : obs){
			o.notify(this);
		}
	}

}
