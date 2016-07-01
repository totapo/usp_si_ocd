package model;

import java.util.LinkedList;
import java.util.List;

import componentes.Barramento;
import componentes.IR;
import componentes.Memoria;
import componentes.Porta;
import componentes.Registrador;
import componentes.RegistradorUtilizavel;
import componentes.ULA;
import componentes.UnidadeControle;

public class Main {
	
	private List<Componente> comp;
	private List<Porta> portas;
	private List<Barramento> barramentos;
	
	public static void main(String[] args){
		Tradutor t = Tradutor.instanceOf();
		
		try {
			System.out.println(t.traduzir("mov ax,bx"));
			System.out.println(t.traduzir("mov ax,1500"));
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		new Main();
	}
	
	public Main(){
		//initComponentes();
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
		
		//portas ula ac adicionar portas de saida como observadoras da UC primeiro (pra n cagar mov ax,ax) TODO
		//Porta outUla = new Porta(false,17,bUlaAc,);
	}
}
