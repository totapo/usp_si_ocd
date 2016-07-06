package controller;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.util.LinkedList;
import java.util.List;

import view.TelaPrincipal;
import model.*;
import componentes.*;

public class Controller implements WindowListener,ActionListener,MouseListener{
	
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
		printarFirmware();
	}
	
	private void printarFirmware() {
		tela.get
	}

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
		ds = new RegistradorUtilizavel("DS","030031",new RegCode( new byte[]{1,0,0}));
		
		regsU = new LinkedList<RegistradorUtilizavel>();
		regsU.add((RegistradorUtilizavel)ax);
		regsU.add((RegistradorUtilizavel)bx);
		regsU.add((RegistradorUtilizavel)cx);
		regsU.add((RegistradorUtilizavel)dx);
		regsU.add((RegistradorUtilizavel)ds);
		
		UC = new UnidadeControle((IR)ir, regsU);
		
		Barramento bUlaAC, bUlaX, bRegs, bExterno; 
		bUlaAC = new Barramento();
		bUlaX = new Barramento();
		bRegs = new Barramento();
		bExterno = new Barramento();
		
		//portas ula ac adicionar portas de saida como observadoras da UC primeiro (pra n cagar mov ax,ax) 
		new Porta(false,1 ,bExterno,mar,UC);
		new Porta(false,3 ,bRegs,mbr,UC);
		new Porta(false,9 ,bExterno,mbr,UC);
		new Porta(false,5 ,bRegs,ir,UC);
		new Porta(false,7 ,bRegs,p1,UC);
		new Porta(false,11,bRegs,p2,UC);
		new Porta(false,13,bRegs,pc,UC);
		new Porta(false,15,bUlaX,x,UC);
		new Porta(false,17,bUlaAC,ula,UC);
		new Porta(false,19,bRegs,ac,UC);
		new Porta(false,21,bRegs,ax,UC);
		new Porta(false,23,bRegs,bx,UC);
		new Porta(false,25,bRegs,cx,UC);
		new Porta(false,27,bRegs,dx,UC);
		new Porta(false,29,bExterno,memoria,UC);
		new Porta(false,31,bRegs,ds,UC);
		
		new Porta(true,0 ,bExterno,mar,UC);
		new Porta(true,2 ,bRegs,mbr,UC);
		new Porta(true,8 ,bExterno,mbr,UC);
		new Porta(true,4 ,bRegs,ir,UC);
		new Porta(true,6 ,bRegs,p1,UC);
		new Porta(true,10,bRegs,p2,UC);
		new Porta(true,12,bRegs,pc,UC);
		new Porta(true,14,bUlaX,x,UC);
		new Porta(true,16,bUlaAC,ula,UC);
		new Porta(true,18,bRegs,ac,UC);
		new Porta(true,20,bRegs,ax,UC);
		new Porta(true,22,bRegs,bx,UC);
		new Porta(true,24,bRegs,cx,UC);
		new Porta(true,26,bRegs,dx,UC);
		new Porta(true,28,bExterno,memoria,UC);
		new Porta(true,30,bRegs,ds,UC);
	}
	
	@Override
	public void mouseClicked(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mousePressed(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mouseReleased(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mouseEntered(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mouseExited(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void windowOpened(WindowEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void windowClosing(WindowEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void windowClosed(WindowEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void windowIconified(WindowEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void windowDeiconified(WindowEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void windowActivated(WindowEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void windowDeactivated(WindowEvent e) {
		// TODO Auto-generated method stub
		
	}

}
