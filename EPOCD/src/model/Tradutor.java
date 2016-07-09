package model;

import java.util.HashMap;
import java.util.Map;

public class Tradutor {
	//classe que faz a tradução do código em assembly pra uma palavra que pode ser colocada na memória
	
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
		assemblyLine = assemblyLine.trim();
		String[] linha = assemblyLine.split(" ");
		
		//se não contém dois parâmetros (0=comando, 1=argumentos) joga exceção
		if(linha.length<2) throw new Exception("Comando inválido: "+assemblyLine);
		String comando = linha[0];
		String[] params = linha[1].split(",");
		
		comando = comando.toLowerCase() + " ";
		
		//tratamento do primeiro parâmetro
		if(params[0].contains("[")){ //se contem colchetes ativa a flag f1
			comando+="[";
			params[0] = params[0].replace("[", "");
			params[0] = params[0].replace("]", "");
			f1=true;
		}
		//verifica se ele usa um registrador ou um número
		switch(params[0].toLowerCase()){
		case "ax": 
		case "bx":
		case "cx":
		case "dx":
		case "ds":
			a = this.regs.get(params[0].toLowerCase());
			comando+="reg";
			break;
		default: //se não for um registrador deve ser um número
			aux1 = Integer.parseInt(params[0]);
			comando+="num";
		}
		//se a flag f1 estiver ativa adiciona o colchete que fecha no comando
		if(f1){
			comando+="]";
			f1=false;
		}
		
		//se houverem dois parâmetros na instrucao, faz o mesmo tratamento para o segundo
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
		
		//ao termino da construção da String que representa o comando, busca o opcode na baseado no comando
		byte[] code = this.opcodes.get(comando);
		byte[] p = new byte[32];
		int fim;
		//se for nulo, joga uma exceção
		if(code==null) throw new Exception("Comando inválido "+assemblyLine+ " - "+comando);
		int i;
		
		//primeiros 5 bits são opcode sempre
		for(i=0; i<5; i++){
			p[i] = code[i];
		}
		int cont;
		//se o registrador a nao for nulo
		if(a!=null){
			cont=0;
			//os bits 5 a 7 representam a
			for(;i<8; i++){
				p[i] = a.getCode()[cont++];
			}
		}
		if(a!=null && b!=null){ //se a não for nulo e b não for nulo
			cont=0;
			//os próximos 3 (8 a 10) bits representam b
			for(;i<11; i++){
				p[i] = b.getCode()[cont++];
			}
		}
		else if(b!=null){ //caso somente b não seja nulo
			cont=0;
			//os bits 5 a 7 representam b
			for(;i<8; i++)
				p[i] = b.getCode()[cont++];
		}
		int lastI = i;
		//se a e b forem nulos temos duas opções
		if(a==null && b==null){
			if(f2){ //a flag f2 indica que existem dois parâmetros na instrução que, nesse caso são dois números
				//assim sendo ele divide o restante da palavra entre os dois (32-5)/2+4 = 13+4 = 17 
				//[ 5 - 17] = p1 (13 bits)
				//[18 - 31] = p2 (14 bits)
				palavra = Integer.toBinaryString(aux1);
				fim = ((Palavra.qtdBitsPalavra-5)/2)+4;
				cont= palavra.length()-1;
				for(i=fim; i>=lastI && cont >= 0; i--){
					p[i]=Byte.parseByte(palavra.charAt(cont--)+"");
				}
				lastI=fim+1;
				palavra = Integer.toBinaryString(aux2);
				cont= palavra.length()-1;
				for(i=31; i>=lastI && cont >= 0; i--){
					p[i]=Byte.parseByte(palavra.charAt(cont--)+"");
				}
			} else {
				palavra = Integer.toBinaryString(aux1);
				cont= palavra.length()-1;
				for(i=31; i>=lastI && cont >= 0; i--){
					p[i]=Byte.parseByte(palavra.charAt(cont--)+"");
				}
			}
			
		} else if(a==null){ // se o a for nulo quer dizer que há um parâmetro numérico em aux1 que ocupa o resto da palavra [8-31]
			palavra = Integer.toBinaryString(aux1);
			cont= palavra.length()-1;
			for(i=31; i>=lastI && cont >= 0; i--){
				p[i]=Byte.parseByte(palavra.charAt(cont--)+"");
			}
		} else if(b==null){ // se o b for nulo quer dizer que há um parâmetro numérico em aux2 que ocupa o resto da palavra [8-31]
			palavra = Integer.toBinaryString(aux2);
			cont=palavra.length()-1;
			for(i=31; i>=lastI && cont >= 0; i--){
				p[i]=Byte.parseByte(palavra.charAt(cont--)+"");
			}
		}
		//checa consistência em caso de mov
		if(comando.contains("mov") && a!=null && a.equals(b)) throw new Exception ("mov não pode ser chamado com registradores iguais!\n"+a+" "+b);
		
		//cria a palavra com base no array de bytes construído
		resp = new Palavra(p);
		
		return resp;
	}
	
}
