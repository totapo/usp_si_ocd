package view;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

import javax.security.auth.login.AccountException;
import javax.swing.*;

import controller.Controller;

public class TelaPrincipal extends JFrame {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private JPanel comandos, registradores, memoria, linhasControle;
	private JButton btnTraduzir;
	private JButton btnClearCodigo;
	private JButton btnExecutaInstrucao;
	private JTextArea codigo;
	private JTextField ax, bx, cx, dx, ds, ir, pc, mar, mbr, p1, p2;
	private ArrayList<CelulaMemoria> celulas;
	private JTable tabelaMemoria;
	private JTable tabelaControle;
	private Controller ctrl;

	public TelaPrincipal() {
		iniciarComponentes();
		this.setVisible(true);
	}

	public void iniciarComponentes() {
		// Criando a janela
		this.setTitle("Assembly Compiler Simulator");
		this.getContentPane().setLayout(null);
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setSize(1000, 680);
		
		// Criando os paineis
		comandos = new JPanel(new FlowLayout(FlowLayout.CENTER, 10, 10));
		comandos.setBorder(BorderFactory.createTitledBorder("Comandos Assembly"));
		comandos.setBounds(10, 10, 580, 300);
		comandos.setVisible(true);
		this.getContentPane().add(comandos);
		
		registradores = new JPanel(null);
		registradores.setBorder(BorderFactory.createTitledBorder("Registradores"));
		registradores.setBounds(600, 10, 380, 300);
		registradores.setVisible(true);
		this.getContentPane().add(registradores);
		
		memoria = new JPanel(null);
		memoria.setBorder(BorderFactory.createTitledBorder("Memória"));
		memoria.setBounds(600, 320, 380, 300);
		memoria.setVisible(true);
		this.getContentPane().add(memoria);
		
		linhasControle = new JPanel(new BorderLayout());
		linhasControle.setBorder(BorderFactory.createTitledBorder("Linhas de Controle"));
		linhasControle.setBounds(10, 320, 580, 300);
		linhasControle.setVisible(true);
		this.getContentPane().add(linhasControle);
		
		//Criando Compoenentes para os comandos
		codigo = new JTextArea();
		codigo.setPreferredSize(new Dimension(560, 200));
		
		btnTraduzir = new JButton("Traduzir");
		//TODO adiciona listeners
		btnTraduzir.setSize(50, 20);
		
		btnExecutaInstrucao = new JButton("Executa Instrução");
		//TODO adiciona listeners
		btnExecutaInstrucao.setSize(50, 20);
		
		btnClearCodigo = new JButton("Limpar Código");
		//TODO adiciona listeners
		btnClearCodigo.setSize(50, 20);
		
		comandos.add(codigo);
		comandos.add(btnTraduzir);
		comandos.add(btnExecutaInstrucao);
		comandos.add(btnClearCodigo);
		
		//Criando componentes para as linhas de controle
		String[][] valores = new String[20][6];
		valores[0][0] = "00000000000000000000000000000000";
		String[] colunas = new String[]{"Portas", "Jump", "Prox.", "ULA", "RWAV", "Decode"};
		
		tabelaControle = new JTable(valores, colunas);
		tabelaControle.getColumnModel().getColumn(0).setPreferredWidth(290);
		tabelaControle.setEnabled(false);
        JScrollPane barraRolagem = new JScrollPane(tabelaControle);
        linhasControle.add(barraRolagem); 
	}

}
