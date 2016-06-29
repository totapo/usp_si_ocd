package model;

import componentes.ULA;

public class Firmware {
	static final LinhaControle [] instrucoes;
	private int pointer;
	
	static {
		instrucoes = new LinhaControle[55]; 
													//portas												          jump-prox-ula   rwav  decode
		instrucoes[ 0] = new LinhaControle(new byte[]{1,0,0,0,0,0,0,0,0,0,0,0,0,1,0,0,1,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0, 0,  1, ULA.INC, 0,0,0, 0}); //busca
		instrucoes[ 1] = new LinhaControle(new byte[]{0,1,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,1,1,0,0,0,0,0,0,0,0,0,1,0,0,0, 0,  2, ULA.NDA, 1,0,1, 0});
		instrucoes[ 2] = new LinhaControle(new byte[]{0,0,0,0,0,0,0,0,1,0,0,0,1,0,0,0,0,0,0,1,0,0,0,0,0,0,0,0,0,1,0,0, 0,  3, ULA.NDA, 0,0,0, 0});
		instrucoes[ 3] = new LinhaControle(new byte[]{0,0,0,1,1,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0, 0, -1, ULA.NDA, 0,0,0, 0}); //decisao de indirecao ou execucao
		instrucoes[ 4] = new LinhaControle(new byte[]{1,0,0,0,0,0,0,0,0,0,0,1,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0, 0,  5, ULA.NDA, 0,0,0, 0}); //indirecao tipo [num] no p2 do ir
		instrucoes[ 5] = new LinhaControle(new byte[]{0,1,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,1,0,0,0, 0,  6, ULA.NDA, 1,0,1, 0}); 
		instrucoes[ 6] = new LinhaControle(new byte[]{0,0,0,0,0,0,0,0,1,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,1,0,0, 0,  7, ULA.NDA, 0,0,0, 0}); 
		instrucoes[ 7] = new LinhaControle(new byte[]{0,0,0,1,0,0,0,0,0,0,1,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0, 0, 10, ULA.NDA, 0,0,0, 0}); 
		instrucoes[ 8] = new LinhaControle(new byte[]{0,0,0,0,0,0,0,0,0,0,1,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0, 0,  5, ULA.NDA, 0,0,0, 3}); //indirecao tipo [reg] no p2 do ir
		instrucoes[ 9] = new LinhaControle(new byte[]{0,0,0,0,0,0,1,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0, 0, 10, ULA.NDA, 0,0,0, 1}); //indirecao tipo [reg] no p1 do ir
		instrucoes[10] = new LinhaControle(new byte[]{0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0, 0, -2, ULA.NDA, 0,0,0, 0}); //decisao do pulo baseado no opcode
		instrucoes[11] = new LinhaControle(new byte[]{0,0,0,0,0,0,0,0,0,0,0,0,0,0,1,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0, 0, 12, ULA.NDA, 0,0,0, 1}); //add reg,reg
		instrucoes[12] = new LinhaControle(new byte[]{0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,1,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0, 0, 13, ULA.ADD, 0,0,0, 3});
		instrucoes[13] = new LinhaControle(new byte[]{0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,1,1,0,0,0,0,0,0,0,0,0,0,0,0,0,0, 0, 14, ULA.NDA, 0,0,0, 0});
		instrucoes[14] = new LinhaControle(new byte[]{0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,1,0,0,0,0,0,0,0,0,0,0,0,0,0, 0,  0, ULA.NDA, 0,0,0, 2});
		instrucoes[15] = new LinhaControle(new byte[]{0,0,0,0,0,0,0,0,0,0,0,0,0,0,1,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0, 0, 16, ULA.NDA, 0,0,0, 1}); //sub reg,reg
		instrucoes[16] = new LinhaControle(new byte[]{0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,1,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0, 0, 17, ULA.SUB, 0,0,0, 3});
		instrucoes[17] = new LinhaControle(new byte[]{0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,1,1,0,0,0,0,0,0,0,0,0,0,0,0,0,0, 0, 18, ULA.NDA, 0,0,0, 0});
		instrucoes[18] = new LinhaControle(new byte[]{0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,1,0,0,0,0,0,0,0,0,0,0,0,0,0, 0,  0, ULA.NDA, 0,0,0, 2});
		instrucoes[19] = new LinhaControle(new byte[]{0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0, 0,  0, ULA.NDA, 0,0,0, 5}); //mov reg,reg
		instrucoes[20] = new LinhaControle(new byte[]{0,0,0,0,0,0,0,0,0,0,0,0,0,1,0,0,0,0,0,0,1,0,0,0,0,0,0,0,0,0,0,0, 0, 21, ULA.NDA, 0,0,0, 0}); //mul reg
		instrucoes[21] = new LinhaControle(new byte[]{0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,1,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0, 0, 22, ULA.MUL, 0,0,0, 1});
		instrucoes[22] = new LinhaControle(new byte[]{0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,1,1,0,0,0,0,0,0,0,0,0,0,0,0,0,0, 0, 23, ULA.NDA, 0,0,0, 0});
		instrucoes[23] = new LinhaControle(new byte[]{0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,1,1,0,0,0,0,0,0,0,0,0,0,0,0, 0,  0, ULA.NDA, 0,0,0, 0});
		instrucoes[24] = new LinhaControle(new byte[]{0,0,0,0,0,0,0,0,0,0,0,0,0,0,1,0,0,0,0,0,0,1,0,0,0,0,0,0,0,0,0,0, 0, 25, ULA.NDA, 0,0,0, 0}); //div reg
		instrucoes[25] = new LinhaControle(new byte[]{0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,1,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0, 0, 26, ULA.DIV, 0,0,0, 1});
		instrucoes[26] = new LinhaControle(new byte[]{0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,1,1,0,0,0,0,0,0,0,0,0,0,0,0,0, 0, 27, ULA.NDA, 0,0,0, 0});
		instrucoes[27] = new LinhaControle(new byte[]{0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,1,1,0,0,0,0,0,0,0,0,0,0,0, 0, 28, ULA.NDA, 0,0,0, 0});
		instrucoes[28] = new LinhaControle(new byte[]{0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,1,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0, 0, 29, ULA.MOD, 0,0,0, 1});
		instrucoes[29] = new LinhaControle(new byte[]{0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,1,1,0,0,0,0,0,0,0,0,0,0,0,0,0, 0, 30, ULA.NDA, 0,0,0, 0});
		instrucoes[30] = new LinhaControle(new byte[]{0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,1,0,0,0,0,0,0,1,0,0,0,0,0, 0,  0, ULA.NDA, 0,0,0, 0});
		instrucoes[31] = new LinhaControle(new byte[]{0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,1,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0, 0, 32, ULA.INC, 0,0,0, 1}); //inc reg
		instrucoes[32] = new LinhaControle(new byte[]{0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,1,1,0,0,0,0,0,0,0,0,0,0,0,0,0, 0, 33, ULA.NDA, 0,0,0, 0});
		instrucoes[33] = new LinhaControle(new byte[]{0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,1,0,0,0,0,0,0,0,0,0,0,0,0, 0,  0, ULA.NDA, 0,0,0, 2});
		instrucoes[34] = new LinhaControle(new byte[]{0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,1,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0, 0, 35, ULA.DEC, 0,0,0, 1}); //dec reg
		instrucoes[35] = new LinhaControle(new byte[]{0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,1,1,0,0,0,0,0,0,0,0,0,0,0,0,0, 0, 36, ULA.NDA, 0,0,0, 0});
		instrucoes[36] = new LinhaControle(new byte[]{0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,1,0,0,0,0,0,0,0,0,0,0,0,0, 0,  0, ULA.NDA, 0,0,0, 2});
		instrucoes[37] = new LinhaControle(new byte[]{0,0,0,0,0,0,0,0,0,0,0,1,0,0,1,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0, 0, 38, ULA.NDA, 0,0,0, 0}); //add reg,num
		instrucoes[38] = new LinhaControle(new byte[]{0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,1,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0, 0, 39, ULA.ADD, 0,0,0, 1});
		instrucoes[39] = new LinhaControle(new byte[]{0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,1,1,0,0,0,0,0,0,0,0,0,0,0,0,0, 0, 40, ULA.NDA, 0,0,0, 0});
		instrucoes[40] = new LinhaControle(new byte[]{0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,1,0,0,0,0,0,0,0,0,0,0,0,0, 0,  0, ULA.NDA, 0,0,0, 2});
		instrucoes[41] = new LinhaControle(new byte[]{0,0,0,0,0,0,0,0,0,0,0,1,0,0,1,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0, 0, 42, ULA.NDA, 0,0,0, 0}); //sub reg,num
		instrucoes[42] = new LinhaControle(new byte[]{0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,1,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0, 0, 43, ULA.SUB, 0,0,0, 1});
		instrucoes[43] = new LinhaControle(new byte[]{0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,1,1,0,0,0,0,0,0,0,0,0,0,0,0,0, 0, 44, ULA.NDA, 0,0,0, 0});
		instrucoes[44] = new LinhaControle(new byte[]{0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,1,0,0,0,0,0,0,0,0,0,0,0,0, 0,  0, ULA.NDA, 0,0,0, 2});
		instrucoes[45] = new LinhaControle(new byte[]{0,0,0,0,0,0,0,0,0,0,0,1,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0, 0,  0, ULA.NDA, 0,0,0, 2}); //mov reg,num
		instrucoes[46] = new LinhaControle(new byte[]{1,0,0,0,0,0,0,0,0,0,0,1,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0, 0, 47, ULA.NDA, 0,0,0, 0}); //mov mem num
		instrucoes[47] = new LinhaControle(new byte[]{0,1,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,1,0,0,0, 0, 48, ULA.NDA, 0,1,1, 0});
		instrucoes[48] = new LinhaControle(new byte[]{0,0,0,0,0,0,0,0,0,1,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,1,0,0,0, 0,  0, ULA.NDA, 0,0,0, 0});
		instrucoes[49] = new LinhaControle(new byte[]{1,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0, 0, 47, ULA.NDA, 0,0,0, 1}); //mov mem reg
		instrucoes[50] = new LinhaControle(new byte[]{0,0,0,0,0,0,0,1,0,0,0,0,1,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0, 0,  0, ULA.NDA, 0,0,0, 0}); //jumps, todos
		instrucoes[51] = new LinhaControle(new byte[]{1,0,0,0,0,0,0,1,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0, 0, 52, ULA.NDA, 0,0,0, 0}); //mov reg,mem
		instrucoes[52] = new LinhaControle(new byte[]{0,1,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,1,0,0,0, 0, 53, ULA.NDA, 1,0,1, 0});
		instrucoes[53] = new LinhaControle(new byte[]{0,0,0,0,0,0,0,0,1,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,1,0,0, 0, 54, ULA.NDA, 0,0,0, 0});
		instrucoes[54] = new LinhaControle(new byte[]{0,0,0,1,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0, 0,  0, ULA.NDA, 0,0,0, 1});
		
	}

	public Firmware(){
		this.pointer = 0;
	}
	
	//a UC recebe a instrucao pra dar jump no poiter quando um ciclo acaba
	public void setPointer(int p){
		this.pointer = p;
	}
	
	public int getPointer(){
		return pointer;
	}
	
	public LinhaControle getInstruction(){
		return instrucoes[pointer];
	}

	
}
