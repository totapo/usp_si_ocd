package view;

import java.awt.*;
import java.util.ArrayList;

import javax.swing.*;

import controller.Controller;

public class TelaPrincipal extends JFrame {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private JTextField[] txtRegistradores;
	private final int ax = 0, bx = 1, cx = 2, dx = 3, ir = 4, p1 = 5, p2 = 6, pc = 7,  ds = 8, mar = 9, mbr = 10;
	private JPanel pnlComandos, pnlRegistradores, pnlMemoria, pnlLinhasControle;
	private JButton btnTraduzir;
	private JButton btnClearCodigo;
	private JButton btnExecutaInstrucao;
	private JButton btnJmpMemoria;
	private JTextArea codigo;
	private JTextField txtJmpMemoria;
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
		pnlComandos = new JPanel(new FlowLayout(FlowLayout.CENTER, 10, 10));
		pnlComandos.setBorder(BorderFactory.createTitledBorder("Comandos Assembly"));
		pnlComandos.setBounds(10, 10, 580, 300);
		pnlComandos.setVisible(true);
		this.getContentPane().add(pnlComandos);
		
		pnlRegistradores = new JPanel(new FlowLayout());
		pnlRegistradores.setBorder(BorderFactory.createTitledBorder("Registradores"));
		pnlRegistradores.setBounds(600, 10, 380, 300);
		pnlRegistradores.setVisible(true);
		((FlowLayout)pnlRegistradores.getLayout()).setAlignment(FlowLayout.LEFT);
		this.getContentPane().add(pnlRegistradores);
		
		pnlMemoria = new JPanel(new BorderLayout());
		pnlMemoria.setBorder(BorderFactory.createTitledBorder("Memória"));
		pnlMemoria.setBounds(600, 320, 380, 300);
		pnlMemoria.setVisible(true);
		this.getContentPane().add(pnlMemoria);
		
		pnlLinhasControle = new JPanel(new BorderLayout());
		pnlLinhasControle.setBorder(BorderFactory.createTitledBorder("Linhas de Controle"));
		pnlLinhasControle.setBounds(10, 320, 580, 300);
		pnlLinhasControle.setVisible(true);
		this.getContentPane().add(pnlLinhasControle);
		
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
		
		pnlComandos.add(codigo);
		pnlComandos.add(btnTraduzir);
		pnlComandos.add(btnExecutaInstrucao);
		pnlComandos.add(btnClearCodigo);
		
		//Criando componentes para as linhas de controle
		//TODO criar funcionalidade para a interface
		String[][] valores = new String[20][6];
		valores[0][0] = "00000000000000000000000000000000";
		String[] colunas = new String[]{"Portas", "Jump", "Prox.", "ULA", "RWAV", "Decode"};
		
		tabelaControle = new JTable(valores, colunas);
		tabelaControle.getColumnModel().getColumn(0).setPreferredWidth(290);
		tabelaControle.setEnabled(false);
        JScrollPane barraRolagem = new JScrollPane(tabelaControle);
        pnlLinhasControle.add(barraRolagem); 
        
        //Criando componentes para a memoria
        valores = new String[20][6];
		valores[0][0] = "10000";
		valores[0][1] = "34589820";
		colunas = new String[]{"Endereco","Valor"};
		
		tabelaMemoria = new JTable(valores, colunas);
		tabelaMemoria.getColumnModel().getColumn(0).setPreferredWidth(70);
		tabelaMemoria.setEnabled(false);
        JScrollPane barraRolagemMemoria = new JScrollPane(tabelaMemoria);
        
        JPanel jmpMemPanel = new JPanel(new FlowLayout());
        JLabel labelMemoria = new JLabel("Mover para: ");
        btnJmpMemoria = new JButton("Ir");
        btnJmpMemoria.setPreferredSize(new Dimension(60, 30));
        //TODO pular para espaco de memoria 
        txtJmpMemoria = new JTextField();
        txtJmpMemoria.setPreferredSize(new Dimension(80, 30));
        jmpMemPanel.add(labelMemoria);
        jmpMemPanel.add(txtJmpMemoria);
        jmpMemPanel.add(btnJmpMemoria);
        pnlMemoria.add(jmpMemPanel, BorderLayout.NORTH);
        pnlMemoria.add(barraRolagemMemoria, BorderLayout.CENTER);
        
        //Criando Componentes para os registradores
        txtRegistradores = new JTextField[11];
        JLabel[] lblRegs = new JLabel[11];
        String[] nomesRegs = new String[]{"ax", "bx", "cx", "dx", "ir", "p1", "p2", "pc",  "ds", "mar", "mbr"};
        JSeparator separator;
        for(int i = 0; i <= 10; i++){
        	if(i == 4 || i == 7 || i == 9){
        		separator = new JSeparator(SwingConstants.HORIZONTAL);
        		separator.setPreferredSize(new Dimension(pnlRegistradores.getWidth() - 25, 2));
        		pnlRegistradores.add(separator);
        	}
        	txtRegistradores[i] = new JTextField();
        	txtRegistradores[i].setPreferredSize(new Dimension(50,30));
        	txtRegistradores[i].setEnabled(false);
        	lblRegs[i] = new JLabel(nomesRegs[i].toUpperCase());
        	lblRegs[i].setPreferredSize(new Dimension(30,30));
        	pnlRegistradores.add(lblRegs[i]);
        	pnlRegistradores.add(txtRegistradores[i]);
        }
        
	}

}
