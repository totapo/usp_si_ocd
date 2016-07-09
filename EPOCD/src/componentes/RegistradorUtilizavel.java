package componentes;


import model.RegCode;

public class RegistradorUtilizavel extends Registrador {
	//classe que representa um registrador utilizavel pelo programador
	//AX, BX, CX, DX e DS
	private RegCode id;
	public RegistradorUtilizavel(String nome, String codigo,RegCode id) {
		super(nome, codigo);
		this.id = id;
	}
	
	public RegCode getID(){
		return id;
	}
	
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + id.hashCode();
		return result;
	}
	
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		RegistradorUtilizavel other = (RegistradorUtilizavel) obj;
		if (!this.id.equals(other.id))
			return false;
		return true;
	}

}
