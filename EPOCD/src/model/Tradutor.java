package model;

import java.util.HashMap;
import java.util.Map;

public class Tradutor {
	/*
		CodeCfgs.put(new OpCode(new byte[]{0,0,0,0,0}),new byte[]{11,3,0}); //add reg,reg
		CodeCfgs.put(new OpCode(new byte[]{0,0,0,0,1}),new byte[]{15,3,0}); //sub reg,reg
		CodeCfgs.put(new OpCode(new byte[]{0,0,0,1,0}),new byte[]{19,3,0}); //mov reg,reg
		CodeCfgs.put(new OpCode(new byte[]{0,0,0,1,1}),new byte[]{20,1,0}); //mul reg
		CodeCfgs.put(new OpCode(new byte[]{0,0,1,0,0}),new byte[]{24,1,0}); //div reg
		CodeCfgs.put(new OpCode(new byte[]{0,0,1,0,1}),new byte[]{31,1,0}); //inc reg
		CodeCfgs.put(new OpCode(new byte[]{0,0,1,1,0}),new byte[]{34,1,0}); //dec reg
		CodeCfgs.put(new OpCode(new byte[]{0,0,1,1,1}),new byte[]{37,1,0}); //add reg,num
		CodeCfgs.put(new OpCode(new byte[]{0,1,0,0,0}),new byte[]{41,1,0}); //sub reg.num
		CodeCfgs.put(new OpCode(new byte[]{0,1,0,0,1}),new byte[]{45,1,0}); //mov reg,num
		CodeCfgs.put(new OpCode(new byte[]{0,1,0,1,0}),new byte[]{51,1,1}); //mov reg,[num]
		CodeCfgs.put(new OpCode(new byte[]{0,1,0,1,1}),new byte[]{51,3,2}); //mov reg,[reg]
		CodeCfgs.put(new OpCode(new byte[]{0,1,1,0,0}),new byte[]{46,4,0}); //mov [num],num
		CodeCfgs.put(new OpCode(new byte[]{0,1,1,0,1}),new byte[]{49,2,0}); //mov [num],reg  - na memoria fica opcode-reg-num, isso facilita as coisas
		CodeCfgs.put(new OpCode(new byte[]{0,1,1,1,0}),new byte[]{49,3,3}); //mov [reg].reg 
		CodeCfgs.put(new OpCode(new byte[]{0,1,1,1,1}),new byte[]{46,1,3}); //mov [reg],num
		CodeCfgs.put(new OpCode(new byte[]{1,0,0,0,0}),new byte[]{50,0,0}); //jmp num
		CodeCfgs.put(new OpCode(new byte[]{1,0,0,0,1}),new byte[]{50,0,4, 0}); //jl
		CodeCfgs.put(new OpCode(new byte[]{1,0,0,1,0}),new byte[]{50,0,4, 1}); //jle
		CodeCfgs.put(new OpCode(new byte[]{1,0,0,1,1}),new byte[]{50,0,4, 2}); //jg
		CodeCfgs.put(new OpCode(new byte[]{1,0,1,0,0}),new byte[]{50,0,4, 3}); //jge
		CodeCfgs.put(new OpCode(new byte[]{1,0,1,0,1}),new byte[]{50,0,4, 4}); //jz
		CodeCfgs.put(new OpCode(new byte[]{1,0,1,1,0}),new byte[]{50,0,4, 5}); //jnz
		CodeCfgs.put(new OpCode(new byte[]{1,0,1,1,1}),new byte[]{55,4,0}); //cmp num,num
		CodeCfgs.put(new OpCode(new byte[]{1,1,0,0,0}),new byte[]{57,1,0}); //cmp reg,num
		CodeCfgs.put(new OpCode(new byte[]{1,1,0,0,1}),new byte[]{59,2,0}); //cmp num,reg
		CodeCfgs.put(new OpCode(new byte[]{1,1,0,0,1}),new byte[]{61,3,0}); //cmp reg,reg 
	*/
	
	private Map<String,RegCode> regs;
	
	private static Tradutor t;
	
	public static Tradutor instanceOf(){
		if(t==null) t = new Tradutor();
		return t;
	}
	
	private Tradutor(){
		regs =  new HashMap<String,RegCode>();
		
		regs.put("AX", new RegCode(new byte[]{0,0,0}));
		regs.put("BX", new RegCode(new byte[]{0,0,1}));
		regs.put("CX", new RegCode(new byte[]{0,1,0}));
		regs.put("DX", new RegCode(new byte[]{0,1,1}));
		regs.put("DS", new RegCode(new byte[]{1,0,0}));
	}
	
	public Palavra traduzir(String assemblyLine) throws Exception{
		Palavra resp = null;
		String[] linha = assemblyLine.split(" ");
		String comando = linha[0];
		String[] params = linha[1].split(",");
		
		
		
		byte[] p = new byte[32];
		
		return resp;
	}
	
}
