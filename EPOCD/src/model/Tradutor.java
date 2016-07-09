package model;

import java.util.HashMap;
import java.util.Map;

public class Tradutor {
	
	
	private Map<String,RegCode> regs;
	private Map<String,byte[]> opcodes;
	
	private static Tradutor t;
	
	public static Tradutor instanceOf(){
		if(t==null) t = new Tradutor();
		return t;
	}
	
	private Tradutor(){
		regs =  new HashMap<String,RegCode>();
		opcodes = new HashMap<String,byte[]>();
		
		regs.put("ax", new RegCode(new byte[]{0,0,0}));
		regs.put("bx", new RegCode(new byte[]{0,0,1}));
		regs.put("cx", new RegCode(new byte[]{0,1,0}));
		regs.put("dx", new RegCode(new byte[]{0,1,1}));
		regs.put("ds", new RegCode(new byte[]{1,0,0}));
		
		opcodes.put("add reg,reg", new byte[]{0,0,0,0,0});
		opcodes.put("sub reg,reg", new byte[]{0,0,0,0,1});
		opcodes.put("mov reg,reg", new byte[]{0,0,0,1,0});
		opcodes.put("mul reg"    , new byte[]{0,0,0,1,1});
		opcodes.put("div reg"    , new byte[]{0,0,1,0,0});
		opcodes.put("inc reg"    , new byte[]{0,0,1,0,1});
		opcodes.put("dec reg"    , new byte[]{0,0,1,1,0});
		opcodes.put("add reg,num", new byte[]{0,0,1,1,1});
		opcodes.put("sub reg,num", new byte[]{0,1,0,0,0});
		opcodes.put("mov reg,num", new byte[]{0,1,0,0,1});
		opcodes.put("mov reg,[num]", new byte[]{0,1,0,1,0});
		opcodes.put("mov reg,[reg]", new byte[]{0,1,0,1,1});
		opcodes.put("mov [num],num", new byte[]{0,1,1,0,0});
		opcodes.put("mov [num],reg", new byte[]{0,1,1,0,1});
		opcodes.put("mov [reg],reg", new byte[]{0,1,1,1,0});
		opcodes.put("mov [reg],num", new byte[]{0,1,1,1,1});
		opcodes.put("jmp num"      , new byte[]{1,0,0,0,0});
		opcodes.put("jl num"       , new byte[]{1,0,0,0,1});
		opcodes.put("jle num"      , new byte[]{1,0,0,1,0});
		opcodes.put("jg num"       , new byte[]{1,0,0,1,1});
		opcodes.put("jge num"      , new byte[]{1,0,1,0,0});
		opcodes.put("jz num"       , new byte[]{1,0,1,0,1});
		opcodes.put("jnz num"      , new byte[]{1,0,1,1,0});
		opcodes.put("cmp num,num"  , new byte[]{1,0,1,1,1});
		opcodes.put("cmp reg,num"  , new byte[]{1,1,0,0,0});
		opcodes.put("cmp num,reg"  , new byte[]{1,1,0,0,1});
		opcodes.put("cmp reg,reg"  , new byte[]{1,1,0,1,0});
		opcodes.put("mul num"  , new byte[]{1,1,0,1,1});
		opcodes.put("div num"      , new byte[]{1,1,1,0,0});
	}
	
	public Palavra traduzir(String assemblyLine) throws Exception{
		Palavra resp = null;
		boolean f1,f2;
		RegCode a,b;
		a = b = null;
		int aux1,aux2;
		aux1=aux2=0;
		String palavra=null;
		f1 =f2=false;
		String[] linha = assemblyLine.split(" ");
		if(linha.length<2) throw new Exception("Comando inválido: "+assemblyLine);
		String comando = linha[0];
		String[] params = linha[1].split(",");
		if(linha.length==0) throw new Exception("Comando inválido: "+assemblyLine);
		comando = comando.toLowerCase() + " ";
		
		if(params[0].contains("[")){
			comando+="[";
			params[0] = params[0].replace("[", "");
			params[0] = params[0].replace("]", "");
			f1=true;
		}
		
		switch(params[0].toLowerCase()){
		case "ax": 
		case "bx":
		case "cx":
		case "dx":
		case "ds":
			a = this.regs.get(params[0].toLowerCase());
			comando+="reg";
			break;
		default:
			aux1 = Integer.parseInt(params[0]);
			comando+="num";
		}
		if(f1){
			comando+="]";
			f1=false;
		}
		
		if(params.length==2){
			f2=true;
			comando+= ",";
			if(params[1].contains("[")){
				comando+="[";
				params[1] = params[1].replace("[", "");
				params[1] = params[1].replace("]", "");
				f1=true;
			}
			
			switch(params[1].toLowerCase()){
			case "ax": 
			case "bx":
			case "cx":
			case "dx":
			case "ds":
				b = this.regs.get(params[1].toLowerCase());
				comando+="reg";
				break;
			default:
				aux2 = Integer.parseInt(params[1]);
				comando+="num";
			}
			if(f1){
				comando+="]";
				f1=false;
			}
		}
		System.out.println(comando);
		byte[] code = this.opcodes.get(comando);
		byte[] p = new byte[32];
		int fim;
		if(code==null) throw new Exception("Comando invalido "+assemblyLine+ " - "+comando);
		int i;
		for(i=0; i<5; i++){
			p[i] = code[i];
		}
		int cont;
		if(a!=null){
			cont=0;
			for(;i<8; i++){
				p[i] = a.getCode()[cont++];
			}
		}
		if(a!=null && b!=null){
			cont=0;
			for(;i<11; i++){
				p[i] = b.getCode()[cont++];
			}
		}
		else if(b!=null){
			cont=0;
			for(;i<8; i++)
				p[i] = b.getCode()[cont++];
		}
		int lastI = i;
		//TODO
		if(a==null && b==null){
			if(f2){
				palavra = Integer.toBinaryString(aux1);
				fim = ((Palavra.qtdBitsPalavra-5)/2)-1;
				cont= palavra.length()-1;
				//System.out.println("here "+fim+" "+palavra);
				for(i=fim; i>=lastI && cont >= 0; i--){
					p[i]=Byte.parseByte(palavra.charAt(cont--)+"");
				}
				lastI=fim;
				palavra = Integer.toBinaryString(aux2);
				cont= palavra.length()-1;
				for(i=31; i>=lastI && cont >= 0; i--){
					p[i]=Byte.parseByte(palavra.charAt(cont--)+"");
				}
			} else {
				palavra = Integer.toBinaryString(aux1);
				cont= palavra.length()-1;
				//System.out.println("here "+fim+" "+palavra);
				for(i=31; i>=lastI && cont >= 0; i--){
					p[i]=Byte.parseByte(palavra.charAt(cont--)+"");
				}
			}
			
		} else if(a==null){
			palavra = Integer.toBinaryString(aux1);
			cont= palavra.length()-1;
			for(i=31; i>=lastI && cont >= 0; i--){
				p[i]=Byte.parseByte(palavra.charAt(cont--)+"");
			}
		} else if(b==null){
			palavra = Integer.toBinaryString(aux2);
			cont=palavra.length()-1;
			for(i=31; i>=lastI && cont >= 0; i--){
				p[i]=Byte.parseByte(palavra.charAt(cont--)+"");
			}
		}
		//System.out.println(palavra);
		resp = new Palavra(p);
		
		return resp;
	}
	
}
