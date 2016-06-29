package componentes;

import java.util.Arrays;

public class RegistradorUtilizavel extends Registrador {
	private byte[] id;
	public RegistradorUtilizavel(String nome, String codigo,byte[] id) {
		super(nome, codigo);
		this.id = id;
	}
	
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + Arrays.hashCode(id);
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
		if (!Arrays.equals(id, other.id))
			return false;
		return true;
	}
	
	

}
