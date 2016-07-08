package componentes;

import model.LinhaControle;

public class Firmware {
	public static final LinhaControle [] instrucoes;
	private int pointer;
	
	static {
		instrucoes = new LinhaControle[71]; 
													//portas												          jump-prox-ula   rwav  decode
		instrucoes[ 0] = new LinhaControle(new byte[]{1,0,0,0,0,0,0,0,0,0,0,0,0,1,0,0,1,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0, 0,  1, ULA.INC, 0,0,0, 0}, "MAR <- PC | ULA <- PC (inc)"); //busca
		instrucoes[ 1] = new LinhaControle(new byte[]{0,1,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,1,1,0,0,0,0,0,0,0,0,0,1,0,0,0, 0,  2, ULA.NDA, 1,0,1, 0}, "Mem <- MAR | AC <- ULA");
		instrucoes[ 2] = new LinhaControle(new byte[]{0,0,0,0,0,0,0,0,1,0,0,0,1,0,0,0,0,0,0,1,0,0,0,0,0,0,0,0,0,1,0,0, 0,  3, ULA.NDA, 1,0,1, 0}, "MBR <- Mem | PC <- AC");
		instrucoes[ 3] = new LinhaControle(new byte[]{0,0,0,1,1,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0, 0, -1, ULA.NDA, 0,0,0, 0}, "IR <- MBR"); //decisao de indirecao ou execucao
		instrucoes[ 4] = new LinhaControle(new byte[]{1,0,0,0,0,0,0,0,0,0,0,1,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0, 0,  5, ULA.NDA, 0,0,0, 0}, "MAR <- P2"); //indirecao tipo [num] no p2 do ir
		instrucoes[ 5] = new LinhaControle(new byte[]{0,1,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,1,0,0,0, 0,  6, ULA.NDA, 1,0,1, 0}, "Mem <- MAR"); 
		instrucoes[ 6] = new LinhaControle(new byte[]{0,0,0,0,0,0,0,0,1,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,1,0,0, 0,  7, ULA.NDA, 1,0,1, 0}, "MBR <- Mem"); 
		instrucoes[ 7] = new LinhaControle(new byte[]{0,0,0,1,0,0,0,0,0,0,1,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0, 0, 10, ULA.NDA, 0,0,0, 0}, "P2 <- MBR"); 
		instrucoes[ 8] = new LinhaControle(new byte[]{0,0,0,0,0,0,0,0,0,0,1,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0, 0,  4, ULA.NDA, 0,0,0, 3}, "P2 <- reg"); //indirecao tipo [reg] no p2 do ir
		instrucoes[ 9] = new LinhaControle(new byte[]{0,0,0,0,0,0,1,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0, 0, 10, ULA.NDA, 0,0,0, 1}, "P1 <- reg"); //indirecao tipo [reg] no p1 do ir
		instrucoes[10] = new LinhaControle(new byte[]{0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0, 0, -2, ULA.NDA, 0,0,0, 0}, "Decisão pulo"); //decisao do pulo baseado no opcode
		instrucoes[11] = new LinhaControle(new byte[]{0,0,0,0,0,0,0,0,0,0,0,0,0,0,1,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0, 0, 12, ULA.NDA, 0,0,0, 1}, "X <- reg1"); //add reg,reg
		instrucoes[12] = new LinhaControle(new byte[]{0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,1,1,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0, 0, 13, ULA.ADD, 0,0,0, 3}, "ULA <- X | ULA <- reg2 (add)");
		instrucoes[13] = new LinhaControle(new byte[]{0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,1,1,0,0,0,0,0,0,0,0,0,0,0,0,0, 0, 14, ULA.NDA, 0,0,0, 0}, "AC <- ULA");
		instrucoes[14] = new LinhaControle(new byte[]{0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,1,0,0,0,0,0,0,0,0,0,0,0,0, 0,  0, ULA.NDA, 0,0,0, 2}, "reg1 <- AC");
		instrucoes[15] = new LinhaControle(new byte[]{0,0,0,0,0,0,0,0,0,0,0,0,0,0,1,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0, 0, 16, ULA.NDA, 0,0,0, 1}, "X <- reg1"); //sub reg,reg
		instrucoes[16] = new LinhaControle(new byte[]{0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,1,1,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0, 0, 17, ULA.SUB, 0,0,0, 3}, "ULA <- X | ULA <- reg2 (sub)");
		instrucoes[17] = new LinhaControle(new byte[]{0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,1,1,0,0,0,0,0,0,0,0,0,0,0,0,0, 0, 18, ULA.NDA, 0,0,0, 0}, "AC <- ULA");
		instrucoes[18] = new LinhaControle(new byte[]{0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,1,0,0,0,0,0,0,0,0,0,0,0,0, 0,  0, ULA.NDA, 0,0,0, 2}, "reg1 <- AC");
		instrucoes[19] = new LinhaControle(new byte[]{0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0, 0,  0, ULA.NDA, 0,0,0, 5}, "reg1 <- reg2"); //mov reg,reg
		instrucoes[20] = new LinhaControle(new byte[]{0,0,0,0,0,0,0,0,0,0,0,0,0,0,1,0,0,0,0,0,0,1,0,0,0,0,0,0,0,0,0,0, 0, 21, ULA.NDA, 0,0,0, 0}, "X <- AX"); //mul reg
		instrucoes[21] = new LinhaControle(new byte[]{0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,1,1,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0, 0, 22, ULA.MUL, 0,0,0, 1}, "ULA <- X | ULA <- reg (mul)");
		instrucoes[22] = new LinhaControle(new byte[]{0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,1,1,0,0,0,0,0,0,0,0,0,0,0,0,0, 0, 23, ULA.NDA, 0,0,0, 0}, "AC <- ULA");
		instrucoes[23] = new LinhaControle(new byte[]{0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,1,1,0,0,0,0,0,0,0,0,0,0,0, 0,  0, ULA.NDA, 0,0,0, 0}, "AX <- AC");
		instrucoes[24] = new LinhaControle(new byte[]{0,0,0,0,0,0,0,0,0,0,0,0,0,0,1,0,0,0,0,0,0,1,0,0,0,0,0,0,0,0,0,0, 0, 25, ULA.NDA, 0,0,0, 0}, "X <- AX"); //div reg
		instrucoes[25] = new LinhaControle(new byte[]{0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,1,1,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0, 0, 26, ULA.DIV, 0,0,0, 1}, "ULA <- X | ULA <- reg (div)");
		instrucoes[26] = new LinhaControle(new byte[]{0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,1,1,0,0,0,0,0,0,0,0,0,0,0,0,0, 0, 27, ULA.NDA, 0,0,0, 0}, "AC <- ULA");
		instrucoes[27] = new LinhaControle(new byte[]{0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,1,1,0,0,0,0,0,0,0,0,0,0,0, 0, 28, ULA.NDA, 0,0,0, 0}, "AX <- AC");
		instrucoes[28] = new LinhaControle(new byte[]{0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,1,1,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0, 0, 29, ULA.MOD, 0,0,0, 1}, "ULA <- X | ULA <- reg (mod)");
		instrucoes[29] = new LinhaControle(new byte[]{0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,1,1,0,0,0,0,0,0,0,0,0,0,0,0,0, 0, 30, ULA.NDA, 0,0,0, 0}, "AC <- ULA");
		instrucoes[30] = new LinhaControle(new byte[]{0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,1,0,0,0,0,0,0,1,0,0,0,0,0, 0,  0, ULA.NDA, 0,0,0, 0}, "DX <- AC");
		instrucoes[31] = new LinhaControle(new byte[]{0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,1,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0, 0, 32, ULA.INC, 0,0,0, 1}, "ULA <- reg (inc)"); //inc reg
		instrucoes[32] = new LinhaControle(new byte[]{0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,1,1,0,0,0,0,0,0,0,0,0,0,0,0,0, 0, 33, ULA.NDA, 0,0,0, 0}, "AC <- ULA");
		instrucoes[33] = new LinhaControle(new byte[]{0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,1,0,0,0,0,0,0,0,0,0,0,0,0, 0,  0, ULA.NDA, 0,0,0, 2}, "reg <- AC");
		instrucoes[34] = new LinhaControle(new byte[]{0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,1,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0, 0, 35, ULA.DEC, 0,0,0, 1}, "ULA <- reg (dec)"); //dec reg
		instrucoes[35] = new LinhaControle(new byte[]{0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,1,1,0,0,0,0,0,0,0,0,0,0,0,0,0, 0, 36, ULA.NDA, 0,0,0, 0}, "AC <- ULA");
		instrucoes[36] = new LinhaControle(new byte[]{0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,1,0,0,0,0,0,0,0,0,0,0,0,0, 0,  0, ULA.NDA, 0,0,0, 2}, "reg <- AC");
		instrucoes[37] = new LinhaControle(new byte[]{0,0,0,0,0,0,0,0,0,0,0,1,0,0,1,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0, 0, 38, ULA.NDA, 0,0,0, 0}, "X <- P2"); //add reg,num
		instrucoes[38] = new LinhaControle(new byte[]{0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,1,1,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0, 0, 39, ULA.ADD, 0,0,0, 1}, "ULA <- X | ULA <- reg (add)");
		instrucoes[39] = new LinhaControle(new byte[]{0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,1,1,0,0,0,0,0,0,0,0,0,0,0,0,0, 0, 40, ULA.NDA, 0,0,0, 0}, "AC <- ULA");
		instrucoes[40] = new LinhaControle(new byte[]{0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,1,0,0,0,0,0,0,0,0,0,0,0,0, 0,  0, ULA.NDA, 0,0,0, 2}, "reg <- AC");
		instrucoes[41] = new LinhaControle(new byte[]{0,0,0,0,0,0,0,0,0,0,0,1,0,0,1,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0, 0, 42, ULA.NDA, 0,0,0, 0}, "X <- P2"); //sub reg,num
		instrucoes[42] = new LinhaControle(new byte[]{0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,1,1,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0, 0, 43, ULA.SUB, 0,0,0, 1}, "ULA <- X | ULA <- reg (sub)");
		instrucoes[43] = new LinhaControle(new byte[]{0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,1,1,0,0,0,0,0,0,0,0,0,0,0,0,0, 0, 44, ULA.NDA, 0,0,0, 0}, "AC <- ULA");
		instrucoes[44] = new LinhaControle(new byte[]{0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,1,0,0,0,0,0,0,0,0,0,0,0,0, 0,  0, ULA.NDA, 0,0,0, 2}, "reg <- AC");
		instrucoes[45] = new LinhaControle(new byte[]{0,0,0,0,0,0,0,0,0,0,0,1,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0, 0,  0, ULA.NDA, 0,0,0, 2}, "reg <- P2"); //mov reg,num
		instrucoes[46] = new LinhaControle(new byte[]{1,0,0,0,0,0,0,1,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0, 0, 47, ULA.NDA, 0,0,0, 0}, "MAR <- P1"); //mov mem num
		instrucoes[47] = new LinhaControle(new byte[]{0,1,1,0,0,0,0,0,0,0,0,1,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,1,0,0,0, 0, 48, ULA.NDA, 0,1,1, 0}, "Mem <- MAR | MBR <- P2");
		instrucoes[48] = new LinhaControle(new byte[]{0,0,0,0,0,0,0,0,0,1,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,1,0,0,0, 0,  0, ULA.NDA, 0,1,1, 0}, "Mem <- MBR");
		instrucoes[49] = new LinhaControle(new byte[]{1,0,0,0,0,0,0,1,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0, 0, 70, ULA.NDA, 0,0,0, 0}, "MAR <- P1"); //mov mem reg
		instrucoes[50] = new LinhaControle(new byte[]{0,0,0,0,0,0,0,1,0,0,0,0,1,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0, 0,  0, ULA.NDA, 0,0,0, 0}, "PC <- P1"); //jumps, todos
		instrucoes[51] = new LinhaControle(new byte[]{1,0,0,0,0,0,0,1,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0, 0, 52, ULA.NDA, 0,0,0, 0}, "MAR <- P1"); //mov reg,mem TODO
		instrucoes[52] = new LinhaControle(new byte[]{0,1,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,1,0,0,0, 0, 53, ULA.NDA, 1,0,1, 0}, "Mem <- MAR");
		instrucoes[53] = new LinhaControle(new byte[]{0,0,0,0,0,0,0,0,1,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,1,0,0, 0, 54, ULA.NDA, 1,0,1, 0}, "MBR <- Mem");
		instrucoes[54] = new LinhaControle(new byte[]{0,0,0,1,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0, 0,  0, ULA.NDA, 0,0,0, 2}, "reg <- MBR");//TODO inutil
		instrucoes[55] = new LinhaControle(new byte[]{0,0,0,0,0,0,0,1,0,0,0,0,0,0,1,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0, 0, 56, ULA.NDA, 0,0,0, 0}, "X <- P1"); //cmp num,num
		instrucoes[56] = new LinhaControle(new byte[]{0,0,0,0,0,0,0,0,0,0,0,1,0,0,0,1,1,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0, 0,  0, ULA.SUB, 0,0,0, 0}, "ULA <- X | ULA <- P2 (sub)");
		instrucoes[57] = new LinhaControle(new byte[]{0,0,0,0,0,0,0,0,0,0,0,0,0,0,1,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0, 0, 58, ULA.NDA, 0,0,0, 1}, "X <- reg"); //cmp reg,num
		instrucoes[58] = new LinhaControle(new byte[]{0,0,0,0,0,0,0,0,0,0,0,1,0,0,0,1,1,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0, 0,  0, ULA.SUB, 0,0,0, 0}, "ULA <- X | ULA <- P2 (sub)");
		instrucoes[59] = new LinhaControle(new byte[]{0,0,0,0,0,0,0,1,0,0,0,0,0,0,1,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0, 0, 60, ULA.NDA, 0,0,0, 0}, "X <- P1"); //cmp num,reg
		instrucoes[60] = new LinhaControle(new byte[]{0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,1,1,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0, 0,  0, ULA.SUB, 0,0,0, 3}, "ULA <- X | ULA <- reg (sub)");
		instrucoes[61] = new LinhaControle(new byte[]{0,0,0,0,0,0,0,0,0,0,0,0,0,0,1,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0, 0, 62, ULA.NDA, 0,0,0, 1}, "X <- reg1"); //cmp reg,reg
		instrucoes[62] = new LinhaControle(new byte[]{0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,1,1,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0, 0,  0, ULA.SUB, 0,0,0, 3}, "ULA <- X | ULA <- reg2 (sub)");
		instrucoes[63] = new LinhaControle(new byte[]{0,0,0,0,0,0,0,0,0,0,0,0,0,1,0,0,0,0,0,0,1,0,0,0,0,0,0,0,0,0,0,0, 0, 64, ULA.NDA, 0,0,0, 0}, "X <- AX"); //mul num
		instrucoes[64] = new LinhaControle(new byte[]{0,0,0,0,0,0,0,1,0,0,0,0,0,0,0,1,1,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0, 0, 22, ULA.MUL, 0,0,0, 1}, "ULA <- X | ULA <- P1 (mul)");
		instrucoes[65] = new LinhaControle(new byte[]{0,0,0,0,0,0,0,0,0,0,0,0,0,0,1,0,0,0,0,0,0,1,0,0,0,0,0,0,0,0,0,0, 0, 66, ULA.NDA, 0,0,0, 0}, "X <- AX"); //div num
		instrucoes[66] = new LinhaControle(new byte[]{0,0,0,0,0,0,0,1,0,0,0,0,0,0,0,1,1,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0, 0, 67, ULA.DIV, 0,0,0, 0}, "ULA <- X | ULA <- P1 (div)");
		instrucoes[67] = new LinhaControle(new byte[]{0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,1,1,0,0,0,0,0,0,0,0,0,0,0,0,0, 0, 68, ULA.NDA, 0,0,0, 0}, "AC <- ULA");
		instrucoes[68] = new LinhaControle(new byte[]{0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,1,1,0,0,0,0,0,0,0,0,0,0,0, 0, 69, ULA.NDA, 0,0,0, 0}, "AX <- AC");
		instrucoes[69] = new LinhaControle(new byte[]{0,0,0,0,0,0,0,1,0,0,0,0,0,0,0,1,1,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0, 0, 29, ULA.MOD, 0,0,0, 0}, "ULA <- X | ULA <- P1 (mod)");
		instrucoes[70] = new LinhaControle(new byte[]{0,1,1,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,1,0,0,0, 0, 48, ULA.NDA, 0,1,1, 3}, "Mem <- MAR | MBR <- reg"); //faltou uma linha pra mov mem,reg
		
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
