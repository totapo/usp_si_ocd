package componentes;

import halp.Subject;
import model.Componente;

public class PortaX extends Porta{
	private ULA ula;
	
	public PortaX(int id, Barramento b, Componente c,ULA ula,
			UnidadeControle uc) {
		super(false, id, b, c, uc);
		this.ula = ula;
	}

	@Override
	public void notify(Subject s) {
		if(s instanceof UnidadeControle){
			this.aberta = ((UnidadeControle) s).getStatus(id);
			if(aberta && ((UnidadeControle) s).podeAtualizar()){
				ula.setPalavra(c.getPalavra(),id);
			}
			notifyObservers();
		} 
	}
	
}
