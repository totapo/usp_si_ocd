package componentes;
import model.Componente;
import model.Palavra;

public class Registrador implements Componente{
	private String nome;
	private String codigo;
	private Palavra mem;
	
	public Registrador(String nome, String codigo){
		this.nome = nome;
		this.codigo = codigo;
	}

	public String getNome() {
		return nome;
	}
	
	public String getCodigo(){
		return codigo;
	}
	
	@Override
	public String toString() {
		return nome;
	}

	@Override
	public void setPalavra(Palavra palavra,int idPorta) {
		this.mem = palavra;
	}

	@Override
	public Palavra getPalavra() {
		return mem;
	}
	
	
}
