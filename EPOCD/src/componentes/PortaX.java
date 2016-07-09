package componentes;

import halp.Subject;
import model.Componente;

public class PortaX extends Porta{
	//porta "especial" entre o registrador X e a ULA (eh tanto de entrada como de saída
	private ULA ula;
	
	public PortaX(int id, Barramento b, Componente c,ULA ula,
			UnidadeControle uc) {
		super(false, id, b, c, uc);
		this.ula = ula;
	}

	//por ser diferente das outras portas dá override no notify
	@Override
	public void notify(Subject s) {
		if(s instanceof UnidadeControle){ //se a uc enviou um notify 
			this.aberta = ((UnidadeControle) s).getStatus(id); //abre ou fecha de acordo com o necessário
			if(aberta && ((UnidadeControle) s).podeAtualizar()){ //se estiver aberta e a uc deu o ok
				ula.setPalavra(c.getPalavra(),id); //envia a palavra do X para a ULA
			}
			notifyObservers();
		} 
	}
	
}
