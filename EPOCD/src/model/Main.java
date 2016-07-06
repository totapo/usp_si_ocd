package model;

import java.util.LinkedList;
import java.util.List;

import view.Tela;
import view.TelaPrincipal;
import componentes.Barramento;
import componentes.IR;
import componentes.Memoria;
import componentes.Porta;
import componentes.Registrador;
import componentes.RegistradorUtilizavel;
import componentes.ULA;
import componentes.UnidadeControle;
import controller.Controller;

public class Main {
	
	private List<Componente> comp;
	private List<Porta> portas;
	private List<Barramento> barramentos;
	
	public static void main(String[] args){
		/*Tradutor t = Tradutor.instanceOf();
		try {
			System.out.println(t.traduzir("mov ax,bx"));
			System.out.println(t.traduzir("mov ax,1500"));
			System.out.println(t.traduzir("cmp 2,1500"));
			System.out.println(t.traduzir("mov ax,[ax]"));
		} catch (Exception e) {
			e.printStackTrace();
		}
		*/
		new Main();
		
	}
	
	public Main(){
		Tradutor t = Tradutor.instanceOf();
		initComponentes();
		TelaPrincipal a = new TelaPrincipal();
	}

	private void initComponentes() {
		comp = new LinkedList<Componente>();
		List<RegistradorUtilizavel>regsU = new LinkedList<RegistradorUtilizavel>();
		portas = new LinkedList<Porta>();
		barramentos = new LinkedList<Barramento>();
		//componentes
		Componente MAR= new Registrador("MAR","000001");
		Componente MBR= new Registrador("MBR","802903");
		Componente P1 = new Registrador("P1","006007");
		Componente P2 = new Registrador("P2","010011");
		Componente IR = new IR("IR","004005", (Registrador) P1, (Registrador) P2);
		Componente PC = new Registrador("PC","012013");
		Componente X  = new Registrador("X","014015");
		Componente ULA= new ULA("016017");
		Componente AC = new Registrador("AC","018019");
		Componente AX = new RegistradorUtilizavel("AX","020021",new RegCode(new byte[]{0,0,0}));
		Componente BX = new RegistradorUtilizavel("BX","022023",new RegCode(new byte[]{0,0,1}));
		Componente CX = new RegistradorUtilizavel("CX","024025",new RegCode(new byte[]{0,1,0}));
		Componente DX = new RegistradorUtilizavel("DX","026027",new RegCode(new byte[]{0,1,1}));
		Componente Me = new Memoria("028029");
		Componente DS = new RegistradorUtilizavel("DS","030031",new RegCode( new byte[]{1,0,0}));
		
		regsU.add((RegistradorUtilizavel)AX);
		regsU.add((RegistradorUtilizavel)BX);
		regsU.add((RegistradorUtilizavel)CX);
		regsU.add((RegistradorUtilizavel)DX);
		regsU.add((RegistradorUtilizavel)DS);
		
		UnidadeControle UC = new UnidadeControle((IR)IR, regsU);
		
		Barramento bUlaAC, bUlaX, bRegs, bExterno; 
		bUlaAC = new Barramento();
		bUlaX = new Barramento();
		bRegs = new Barramento();
		bExterno = new Barramento();
		
		//portas ula ac adicionar portas de saida como observadoras da UC primeiro (pra n cagar mov ax,ax) 
		new Porta(false,1 ,bExterno,MAR,UC);
		new Porta(false,3 ,bRegs,MBR,UC);
		new Porta(false,9 ,bExterno,MBR,UC);
		new Porta(false,5 ,bRegs,IR,UC);
		new Porta(false,7 ,bRegs,P1,UC);
		new Porta(false,11,bRegs,P2,UC);
		new Porta(false,13,bRegs,PC,UC);
		new Porta(false,15,bUlaX,X,UC);
		new Porta(false,17,bUlaAC,ULA,UC);
		new Porta(false,19,bRegs,AC,UC);
		new Porta(false,21,bRegs,AX,UC);
		new Porta(false,23,bRegs,BX,UC);
		new Porta(false,25,bRegs,CX,UC);
		new Porta(false,27,bRegs,DX,UC);
		new Porta(false,29,bExterno,Me,UC);
		new Porta(false,31,bRegs,DS,UC);
		
		new Porta(true,0 ,bExterno,MAR,UC);
		new Porta(true,2 ,bRegs,MBR,UC);
		new Porta(true,8 ,bExterno,MBR,UC);
		new Porta(true,4 ,bRegs,IR,UC);
		new Porta(true,6 ,bRegs,P1,UC);
		new Porta(true,10,bRegs,P2,UC);
		new Porta(true,12,bRegs,PC,UC);
		new Porta(true,14,bUlaX,X,UC);
		new Porta(true,16,bUlaAC,ULA,UC);
		new Porta(true,18,bRegs,AC,UC);
		new Porta(true,20,bRegs,AX,UC);
		new Porta(true,22,bRegs,BX,UC);
		new Porta(true,24,bRegs,CX,UC);
		new Porta(true,26,bRegs,DX,UC);
		new Porta(true,28,bExterno,Me,UC);
		new Porta(true,30,bRegs,DS,UC);
	}
}
