package componentes;

import model.Componente;
import model.Palavra;
import model.Porta;

public class Registrador extends Componente{
	private String nome;
	private Palavra mem;
	private Porta in,out;
	
	public Registrador(String nome){
		
	}
	
	
	
	public void setMem(Palavra mem) {
		this.mem = mem;
	}
	public void setIn(Porta in) {
		this.in = in;
	}

	public void setOut(Porta out) {
		this.out = out;
	}

	public String getNome() {
		return nome;
	}
	public Palavra getMem() {
		return mem;
	}
	
	
}
