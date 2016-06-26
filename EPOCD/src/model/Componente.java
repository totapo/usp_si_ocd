package model;

public abstract class Componente {
	
	public Palavra p;
	
	public void setPalavra(Palavra palavra) {
		p = palavra;
	}

	public Palavra getPalavra() {
		return p;
	}

}
