package view;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.Rectangle;
import java.util.ArrayList;

import javax.swing.*;
import javax.swing.table.DefaultTableCellRenderer;

import componentes.Memoria;
import controller.Controller;

public class TelaPrincipal extends JFrame {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private JTextField[] txtRegistradores;
	public final int ax = 0, bx = 1, cx = 2, dx = 3, ir = 4, p1 = 5, p2 = 6, pc = 7,  ds = 8, mar = 9, mbr = 10, x = 11, ac = 12
			, ula1 = 13, ula2=14, zero=15, sinal=16;
	private JPanel pnlComandos, pnlRegistradores, pnlMemoria, pnlLinhasControle;
	private JButton btnTraduzir;
	private JButton btnClearCodigo;
	private JButton btnExecutaInstrucao;
	//private JButton btnJmpMemoria;
	private JTextArea codigo;
	//private JTextField txtJmpMemoria;
	private JTextField txtDescOperacao;
	private ArrayList<CelulaMemoria> celulas;
	private JTable tabelaMemoria;
	private JTable tabelaControle;
	private Controller ctrl;

	public TelaPrincipal(Controller ctrl) {
		this.ctrl = ctrl;
		iniciarComponentes();
		this.setVisible(true);
	}

	public void iniciarComponentes() {
		// Criando a janela
		this.setTitle("Assembly Compiler Simulator");
		this.getContentPane().setLayout(new BorderLayout());
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setSize(1100, 680);
		
		// Criando os paineis
		JPanel leftPanel = new JPanel();
		leftPanel.setLayout(new BoxLayout(leftPanel,BoxLayout.Y_AXIS));
		leftPanel.setPreferredSize(new Dimension(580,600));
		
		JPanel rightPanel = new JPanel();
		rightPanel.setLayout(new BoxLayout(rightPanel,BoxLayout.Y_AXIS));
		rightPanel.setPreferredSize(new Dimension(380,600));
		
		pnlComandos = new JPanel(new FlowLayout(FlowLayout.CENTER, 10, 10));
		pnlComandos.setBorder(BorderFactory.createTitledBorder("Comandos Assembly"));
		pnlComandos.setPreferredSize(new Dimension( 580, 300));
		pnlComandos.setVisible(true);
		
		
		pnlLinhasControle = new JPanel();
		pnlLinhasControle.setLayout(new BoxLayout(pnlLinhasControle, BoxLayout.Y_AXIS));
		pnlLinhasControle.setBorder(BorderFactory.createTitledBorder("Linhas de Controle"));
		pnlLinhasControle.setPreferredSize(new Dimension( 580, 300));
		pnlLinhasControle.setVisible(true);
		//this.getContentPane().add(pnlLinhasControle);
		
		leftPanel.add(pnlComandos, BorderLayout.LINE_START);
		leftPanel.add(pnlLinhasControle, BorderLayout.CENTER);
		this.getContentPane().add(leftPanel, BorderLayout.LINE_START);
		
		pnlRegistradores = new JPanel(new FlowLayout());
		pnlRegistradores.setBorder(BorderFactory.createTitledBorder("Registradores"));
		pnlRegistradores.setPreferredSize(new Dimension( 380, 300));
		pnlRegistradores.setVisible(true);
		GridLayout g = new GridLayout(0,4);
		g.setVgap(10);
		JPanel regs = new JPanel(g);
		regs.setPreferredSize(new Dimension( 500, 240));
		pnlRegistradores.add(regs);
		((FlowLayout)pnlRegistradores.getLayout()).setAlignment(FlowLayout.LEFT);
		//this.getContentPane().add(pnlRegistradores, BorderLayout.CENTER);
		
		pnlMemoria = new JPanel(new BorderLayout());
		pnlMemoria.setBorder(BorderFactory.createTitledBorder("Memória"));
		pnlMemoria.setPreferredSize(new Dimension(380, 300));
		pnlMemoria.setVisible(true);
		//this.getContentPane().add(pnlMemoria);
		
		rightPanel.add(pnlRegistradores, BorderLayout.LINE_START);
		rightPanel.add(pnlMemoria, BorderLayout.CENTER);
		this.getContentPane().add(rightPanel, BorderLayout.CENTER);
		
		//Criando Compoenentes para os comandos
		JPanel p = new JPanel(new BorderLayout());
		p.setPreferredSize(new Dimension(545,200));
		codigo = new JTextArea("");
		codigo.setLineWrap(true);
		codigo.setWrapStyleWord(true);
		JScrollPane scrollCodigo = new JScrollPane(codigo);
		scrollCodigo.setPreferredSize(new Dimension(545, 200));
		scrollCodigo.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
		p.add(scrollCodigo,BorderLayout.PAGE_START);
		
		btnTraduzir = new JButton("Traduzir");
		btnTraduzir.setActionCommand("Traduzir");
		btnTraduzir.addActionListener(ctrl);
		btnTraduzir.setSize(50, 20);
		
		btnExecutaInstrucao = new JButton("Executa Instrução");
		btnExecutaInstrucao.setActionCommand("Executar");
		btnExecutaInstrucao.addActionListener(ctrl);
		btnExecutaInstrucao.setSize(50, 20);
		
		btnClearCodigo = new JButton("Limpar Memória");
		btnClearCodigo.setActionCommand("Limpar");
		btnClearCodigo.addActionListener(ctrl);
		btnClearCodigo.setSize(50, 20);
		
		pnlComandos.add(p);
		pnlComandos.add(btnTraduzir);
		pnlComandos.add(btnExecutaInstrucao);
		pnlComandos.add(btnClearCodigo);
		
		//Criando componentes para as linhas de controle
		JPanel descLinhaControle = new JPanel(new BorderLayout());
		descLinhaControle.setPreferredSize(new Dimension(545,30));
		this.txtDescOperacao = new JTextField();
		txtDescOperacao.setEditable(false);
		descLinhaControle.add(txtDescOperacao);
		tabelaControle = new JTable(new LinhaControleModel());
		tabelaControle.getColumnModel().getColumn(0).setPreferredWidth(460);
		DefaultTableCellRenderer rightRenderer = new DefaultTableCellRenderer();
		rightRenderer.setHorizontalAlignment(JLabel.RIGHT);
		tabelaControle.getColumnModel().getColumn(1).setCellRenderer(rightRenderer);
		tabelaControle.getColumnModel().getColumn(2).setCellRenderer(rightRenderer);
		tabelaControle.getColumnModel().getColumn(3).setCellRenderer(rightRenderer);
		tabelaControle.getColumnModel().getColumn(5).setCellRenderer(rightRenderer);
		tabelaControle.getColumnModel().getColumn(6).setCellRenderer(rightRenderer);
		tabelaControle.setEnabled(false);
        JScrollPane barraRolagem = new JScrollPane(tabelaControle);
        
        pnlLinhasControle.add(descLinhaControle); 
        pnlLinhasControle.add(barraRolagem); 
		
		tabelaMemoria = new JTable();
		tabelaMemoria.setEnabled(false);
        JScrollPane barraRolagemMemoria = new JScrollPane(tabelaMemoria);
        
        JPanel jmpMemPanel = new JPanel(new FlowLayout());
        pnlMemoria.add(jmpMemPanel, BorderLayout.NORTH);
        pnlMemoria.add(barraRolagemMemoria, BorderLayout.CENTER);
        
        //Criando Componentes para os registradores
        txtRegistradores = new JTextField[17];
        JLabel[] lblRegs = new JLabel[17];
        String[] nomesRegs = new String[]{"ax", "bx", "cx", "dx", "ir", "p1", "p2", "pc",  "ds", "mar", "mbr", "x","ac","UL1", "UL2", "Zero", "Sinal"};
        JPanel aux;
        for(int i = 0; i <= 16; i++){
        	aux = new JPanel();
        	aux.setLayout(new BoxLayout(aux,BoxLayout.X_AXIS));
        	txtRegistradores[i] = new JTextField();
        	txtRegistradores[i].setPreferredSize(new Dimension(55,30));
        	txtRegistradores[i].setEditable(false);
        	lblRegs[i] = new JLabel(nomesRegs[i].toUpperCase());
        	lblRegs[i].setPreferredSize(new Dimension(30,30));
        	if(i>14)
        		lblRegs[i].setPreferredSize(new Dimension(40,30));
        	aux.add(lblRegs[i]);
        	aux.add(txtRegistradores[i]);
        	regs.add(aux);
        	if(i == 6){
        		regs.add(new JPanel()); //pra encher o espaço xD
        	} else if(i==8 || i==10 || i==12){
        		regs.add(new JPanel()); //pra encher o espaço xD
        		regs.add(new JPanel()); //pra encher o espaço xD
        	}
        }
        
	}
	
	public void atualizaSelecaoLinhaControle(int ponteiro) {
		tabelaControle.setRowSelectionInterval(ponteiro, ponteiro);
		tabelaControle.scrollRectToVisible(new Rectangle(tabelaControle.getCellRect(ponteiro, 0, true))); //não testei e pode dar bosta
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}

	public JTextField[] getTxtRegistradores() {
		return txtRegistradores;
	}

	public JPanel getPnlComandos() {
		return pnlComandos;
	}

	public JPanel getPnlRegistradores() {
		return pnlRegistradores;
	}

	public JPanel getPnlMemoria() {
		return pnlMemoria;
	}

	public JPanel getPnlLinhasControle() {
		return pnlLinhasControle;
	}

	public JButton getBtnTraduzir() {
		return btnTraduzir;
	}

	public JButton getBtnClearCodigo() {
		return btnClearCodigo;
	}

	public JButton getBtnExecutaInstrucao() {
		return btnExecutaInstrucao;
	}

	public JTextArea getCodigo() {
		return codigo;
	}

	public ArrayList<CelulaMemoria> getCelulas() {
		return celulas;
	}

	public JTable getTabelaMemoria() {
		return tabelaMemoria;
	}

	public JTable getTabelaControle() {
		return tabelaControle;
	}

	public void setMemoryModel(Memoria memoria) {
		this.tabelaMemoria.setModel(new MemoriaModel(memoria));
		tabelaMemoria.getColumnModel().getColumn(0).setPreferredWidth(70);
		DefaultTableCellRenderer rightRenderer = new DefaultTableCellRenderer();
		rightRenderer.setHorizontalAlignment(JLabel.RIGHT);
		tabelaMemoria.getColumnModel().getColumn(0).setCellRenderer(rightRenderer);
		tabelaMemoria.getColumnModel().getColumn(1).setCellRenderer(rightRenderer);
	}

	public void atualizaMem() {
		((MemoriaModel)this.tabelaMemoria.getModel()).update();
		tabelaMemoria.repaint();
		tabelaMemoria.revalidate();
	}

	public JTextField getTxtDescOperacao() {
		return txtDescOperacao;
	}
	
}
