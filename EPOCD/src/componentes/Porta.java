package componentes;

import java.util.LinkedList;
import java.util.List;

import halp.Observer;
import halp.Subject;
import model.Componente;


public class Porta implements Observer, Subject{ 
	//Subject eh pra caso a gnt queira mudar algo na tela qnd abre e fecha a porta
	protected boolean aberta;
	protected boolean in;
	protected Barramento barramento;
	protected Componente c;
	protected int id;
	
	public Porta(boolean in, int id, Barramento b, Componente c, UnidadeControle uc){
		this.c = c;
		this.in = in;
		this.id=id;
		this.barramento = b;
		uc.addObserver(this);
		barramento.addObserver(this);
		this.obs = new LinkedList<Observer>();
	}
	

	@Override
	public void notify(Subject s) {
		if(s instanceof UnidadeControle){
			this.aberta = ((UnidadeControle) s).getStatus(id);
			if(aberta && ((UnidadeControle) s).podeAtualizar()){
				//if(in) c.setPalavra(barramento.getPalavra(),id);
				if(!in) barramento.setPalavra(c.getPalavra());
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
