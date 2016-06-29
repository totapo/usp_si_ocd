package model;

import java.util.LinkedList;
import java.util.List;

import componentes.IR;
import componentes.Memoria;
import componentes.Registrador;
import componentes.RegistradorUtilizavel;
import componentes.ULA;

public class Main {
	
	private List<Componente> comp;
	private List<Porta> portas;
	private List<Barramento> barramentos;
	
	public static void main(String[] args){
		new Main();
	}
	
	public Main(){
		initComponentes();
	}

	private void initComponentes() {
		comp = new LinkedList<Componente>();
		portas = new LinkedList<Porta>();
		barramentos = new LinkedList<Barramento>();
		//componentes
		Componente MAR= new Registrador("MAR","000001");
		Componente MBR= new Registrador("MBR","802903");
		Componente IR = new IR("IR","004005");
		Componente P1 = new Registrador("P1","006007");
		Componente P2 = new Registrador("P2","010011");
		Componente PC = new Registrador("PC","012013");
		Componente X  = new Registrador("X","014015");
		Componente ULA= new ULA("016017");
		Componente AC = new Registrador("AC","018019");
		Componente AX = new RegistradorUtilizavel("AX","020021",new byte[]{0,0,0});
		Componente BX = new RegistradorUtilizavel("BX","022023",new byte[]{0,0,1});
		Componente CX = new RegistradorUtilizavel("CX","024025",new byte[]{0,1,0});
		Componente DX = new RegistradorUtilizavel("DX","026027",new byte[]{0,1,1});
		Componente Me = new Memoria("028029");
		Componente DS = new RegistradorUtilizavel("DS","030031",new byte[]{1,0,0});
		UnidadeControle UC = new UnidadeControle((IR)IR);
		
		Barramento bUlaAC, bUlaX, bRegs, bExterno; 
		bUlaAC = new Barramento();
		bUlaX = new Barramento();
		bRegs = new Barramento();
		bExterno = new Barramento();
		
		//portas ula ac
		//Porta outUla = new Porta(false,17,bUlaAc,);
	}
}
