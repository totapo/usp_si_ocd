package model;

public abstract class Componente{
	
	public abstract void setPalavra(Palavra palavra, int idPorta);

	public abstract Palavra getPalavra();
	
	public abstract void reset();
}
