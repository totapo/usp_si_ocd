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
		new Controller();
	}

	
}
