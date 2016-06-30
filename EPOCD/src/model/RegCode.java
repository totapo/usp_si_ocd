package model;

import java.util.Arrays;

public class RegCode {
private byte[] code;
	
	public RegCode(Palavra mem){
		this.code = Arrays.copyOfRange(mem.getBits(),0,3);
	}
	
	public RegCode(byte[] b){
		if(b.length==3) this.code = b;
	}
	
	public byte[] getCode(){
		return code;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + Arrays.hashCode(code);
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
		RegCode other = (RegCode) obj;
		if (!Arrays.equals(code, other.code))
			return false;
		return true;
	}
	
}
