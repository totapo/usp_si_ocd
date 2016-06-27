package componentes;

import java.util.ArrayList;
import java.util.List;

import model.Componente;
import model.Palavra;
import model.Porta;

public class Registrador extends Componente{
	private String nome;
	private Palavra mem;
	private List<Porta> portas;
	
	public Registrador(String nome){
		this.nome = nome;
		portas = new ArrayList<Porta>();
	}
	
	public void setMem(Palavra mem) {
		this.mem = mem;
	}
	
	public void addPorta(Porta p){
		this.portas.add(p);
	}

	public String getNome() {
		return nome;
	}
	public Palavra getMem() {
		return mem;
	}
	
	
}
