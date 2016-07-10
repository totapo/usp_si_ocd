package controller;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.LinkedList;
import java.util.List;

import javax.swing.JOptionPane;

import view.TelaPrincipal;
import model.*;
import componentes.*;

public class Controller implements ActionListener{
	//classe que faz a conexão entre a interface e a lógica do programa
	private UnidadeControle UC;
	private Memoria memoria;
	private ULA ula;
	private Registrador mar,mbr,ir,p1,p2,x,ac,pc,ax,bx,cx,dx,ds;
	private Tradutor t;
	private TelaPrincipal tela;
	
	public Controller(){
		t = Tradutor.instanceOf();
		initComponents();
		tela = new TelaPrincipal(this);
		tela.setMemoryModel(memoria);
		tela.getBtnExecutaInstrucao().setEnabled(false);
		tela.getBtnClearCodigo().setEnabled(false);
		this.atualizarExibicao();
		
	}
	
	//atualiza os campos da tela com os valores dos componetes
	private void atualizarExibicao() {
		tela.getTxtRegistradores()[tela.mar].setText(mar.getPalavra().getIntValue()+"");
		tela.getTxtRegistradores()[tela.mbr].setText(mbr.getPalavra().getIntValue()+"");
		tela.getTxtRegistradores()[tela.ir].setText(ir.getPalavra().getIntValue()+"");
		tela.getTxtRegistradores()[tela.p1].setText(p1.getPalavra().getIntValue()+"");
		tela.getTxtRegistradores()[tela.p2].setText(p2.getPalavra().getIntValue()+"");
		tela.getTxtRegistradores()[tela.pc].setText(pc.getPalavra().getIntValue()+"");
		tela.getTxtRegistradores()[tela.ax].setText(ax.getPalavra().getIntValue()+"");
		tela.getTxtRegistradores()[tela.bx].setText(bx.getPalavra().getIntValue()+"");
		tela.getTxtRegistradores()[tela.cx].setText(cx.getPalavra().getIntValue()+"");
		tela.getTxtRegistradores()[tela.dx].setText(dx.getPalavra().getIntValue()+"");
		tela.getTxtRegistradores()[tela.ds].setText(ds.getPalavra().getIntValue()+"");
		
		tela.getTxtRegistradores()[tela.x].setText(x.getPalavra().getIntValue()+"");
		tela.getTxtRegistradores()[tela.ac].setText(ac.getPalavra().getIntValue()+"");
		
		tela.getTxtRegistradores()[tela.ula1].setText(ula.getNum1().getIntValue()+"");
		tela.getTxtRegistradores()[tela.ula2].setText(ula.getNum2().getIntValue()+"");
		
		tela.getTxtRegistradores()[tela.zero].setText(((ula.flagZero())?1:0)+"");
		tela.getTxtRegistradores()[tela.sinal].setText(((ula.flagSignal())?1:0)+"");
		
		tela.atualizaSelecaoLinhaControle(UC.getPointer());

		tela.getTxtDescOperacao().setText("Linha "+UC.getPointer()+": "+Firmware.instrucoes[UC.getPointer()].getDesc());
		
		tela.atualizaMem();
	}


	//inicializa todos os componentes do processador
	private void initComponents() {
		
		List<RegistradorUtilizavel> regsU;
		//componentes
		mar= new Registrador("MAR","000001");
		mbr= new Registrador("MBR","802903");
		p1 = new Registrador("P1","006007");
		p2 = new Registrador("P2","010011");
		ir = new IR("IR","004005", p1, p2);
		pc = new Registrador("PC","012013");
		x  = new Registrador("X","014015");
		ula= new ULA("016017");
		ac = new Registrador("AC","018019");
		ax = new RegistradorUtilizavel("AX","020021",new RegCode(new byte[]{0,0,0}));
		bx = new RegistradorUtilizavel("BX","022023",new RegCode(new byte[]{0,0,1}));
		cx = new RegistradorUtilizavel("CX","024025",new RegCode(new byte[]{0,1,0}));
		dx = new RegistradorUtilizavel("DX","026027",new RegCode(new byte[]{0,1,1}));
		memoria = new Memoria("028029");
		ds = new RegistradorUtilizavel("DS","030031",new RegCode(new byte[]{1,0,0}));
		
		regsU = new LinkedList<RegistradorUtilizavel>();
		regsU.add((RegistradorUtilizavel)ax);
		regsU.add((RegistradorUtilizavel)bx);
		regsU.add((RegistradorUtilizavel)cx);
		regsU.add((RegistradorUtilizavel)dx);
		regsU.add((RegistradorUtilizavel)ds);
		
		UC = new UnidadeControle((IR)ir, regsU,memoria,ula);
		
		//barramentos
		Barramento bUlaAC, bUlaX, bRegs, bExterno; 
		bUlaAC = new Barramento();
		bUlaX = new Barramento();
		bRegs = new Barramento();
		bExterno = new Barramento();
		
		//portas ula ac adicionar portas de saida como observadoras da UC primeiro 
		new Porta(false,1 ,bExterno,mar,UC);
		new Porta(false,3 ,bRegs,mbr,UC);
		new Porta(false,9 ,bExterno,mbr,UC);
		new Porta(false,5 ,bRegs,ir,UC);
		new Porta(false,7 ,bRegs,p1,UC);
		new Porta(false,11,bRegs,p2,UC);
		new Porta(false,13,bRegs,pc,UC);
		new PortaX(15,bUlaX,x,ula,UC); //porta especial
		new Porta(false,17,bUlaAC,ula,UC);
		new Porta(false,19,bRegs,ac,UC);
		new Porta(false,21,bRegs,ax,UC);
		new Porta(false,23,bRegs,bx,UC);
		new Porta(false,25,bRegs,cx,UC);
		new Porta(false,27,bRegs,dx,UC);
		new Porta(false,29,bExterno,memoria,UC);
		new Porta(false,31,bRegs,ds,UC);
		
		new Porta(true,0 ,bRegs,mar,UC);
		new Porta(true,2 ,bRegs,mbr,UC);
		new Porta(true,8 ,bExterno,mbr,UC);
		new Porta(true,4 ,bRegs,ir,UC);
		new Porta(true,6 ,bRegs,p1,UC);
		new Porta(true,10,bRegs,p2,UC);
		new Porta(true,12,bRegs,pc,UC);
		new Porta(true,14,bRegs,x,UC);
		new Porta(true,16,bRegs,ula,UC);
		new Porta(true,18,bUlaAC,ac,UC);
		new Porta(true,20,bRegs,ax,UC);
		new Porta(true,22,bRegs,bx,UC);
		new Porta(true,24,bRegs,cx,UC);
		new Porta(true,26,bRegs,dx,UC);
		new Porta(true,28,bExterno,memoria,UC);
		new Porta(true,30,bRegs,ds,UC);
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		String action = e.getActionCommand();
		switch(action){
		case "Traduzir": //botao traduzir
			try {
				if(tela.getCodigo().getText().trim().length()>0){
					this.traduzir(tela.getCodigo().getText());
					//só deixa os outros botões ativos se traduziu corretamente
					tela.getBtnExecutaInstrucao().setEnabled(true);
					tela.getBtnClearCodigo().setEnabled(true);
				}
			} catch (Exception ex) {
				JOptionPane.showMessageDialog(tela, ex.getClass().getName()+": "+ex.getMessage(), "Erro na tradução", JOptionPane.ERROR_MESSAGE);
				//ex.printStackTrace();
			}
			break;
		case "Executar": //botao executar
			try {
				UC.advanceClock();
			} catch (Exception ex) {
				JOptionPane.showMessageDialog(tela, ex.getClass().getName()+": "+ex.getMessage(), "Erro na execução", JOptionPane.ERROR_MESSAGE);
				//ex.printStackTrace();
			}
			break;
		case "Limpar": //botao limpar
			tela.getBtnExecutaInstrucao().setEnabled(false);
			tela.getBtnClearCodigo().setEnabled(false);
			resetAll();
			break;
		default:
		}
		atualizarExibicao();
	}

	//reseta o status de todos os componentes
	private void resetAll() {
		UC.reset();;
		memoria.reset();;
		ula.reset();
		mar.reset();
		mbr.reset();
		ir.reset();
		p1.reset();
		p2.reset();
		x.reset();
		ac.reset();
		pc.reset();
		ax.reset();
		bx.reset();
		cx.reset();
		dx.reset();
		ds.reset();
	}

	private void traduzir(String text) throws Exception {
		String[] linhas = text.split("\n");
		int contador=0;
		for(String s:linhas){
			s = s.trim();
			if(s.length()>0){
				memoria.insere(contador, t.traduzir(s));
				contador++;
			}
		}
		//usado para testar o limite de endereçamento da memória por código
		//memoria.insere(4500, new Palavra(true));
	}

}
