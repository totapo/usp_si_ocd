package model;

import java.util.Arrays;

public class OpCode {
	private byte[] code;
	
	public OpCode(Palavra mem){
		this.code = Arrays.copyOfRange(mem.getBits(),0,5);
	}
	
	public OpCode(byte[] b){
		if(b.length==5) this.code = b;
	}
	
	public byte[] getCode(){
		return code;
	}
	
	@Override
	public String toString(){
		return Arrays.toString(code);
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
		OpCode other = (OpCode) obj;
		if (!Arrays.equals(code, other.code))
			return false;
		return true;
	}
	
	
}
