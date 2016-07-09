package componentes;

import java.util.LinkedList;
import java.util.List;

import halp.Observer;
import halp.Subject;
import model.Componente;


public class Porta implements Observer, Subject{ 
	//Classe que representa uma porta
	//Implementa Subject para que, caso a gnt queira mudar algo na tela qnd abre e fecha a porta, fique mais fácil de identificar o que mudar
	protected boolean aberta; //se está aberta ou fechada
	protected boolean in; //se é de entrada ou saída
	protected Barramento barramento; //barramento do qual faz parte
	protected Componente c; //componente associado a porta
	protected int id; //id da porta
	
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
		if(s instanceof UnidadeControle){ //se a UC enviou um notify
			this.aberta = ((UnidadeControle) s).getStatus(id); //abra ou feche de acordo com o que a UC pede
			if(aberta && ((UnidadeControle) s).podeAtualizar()){ //se estiver aberta e a UC deu o OK para mexermos no barramento
				if(!in) barramento.setPalavra(c.getPalavra()); //se for uma porta de saída envia a palavra do componente ao barramento
			}
			notifyObservers();
		} else if(s instanceof Barramento){ //se o barramento enviou um notify
			if(aberta && in){ //se a porta estiver aberta e for de entrada
				c.setPalavra(barramento.getPalavra(),id); //pega o que tá no barramento e envia ao componente
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
