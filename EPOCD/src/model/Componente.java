package model;

public interface Componente{
	//interface que representa um componente
	public void setPalavra(Palavra palavra, int idPorta);

	public Palavra getPalavra();
	
	public void reset();
}
