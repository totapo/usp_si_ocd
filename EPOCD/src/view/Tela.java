package view;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;

import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JTextArea;

import controller.Controller;

public class Tela extends JFrame {

	private static final long serialVersionUID = 1L;
	
	private JButton btnTraduzir;
	private JButton btnClearCodigo;
	
	private JTextArea codigo;
	
	private Controller ctrl;
	
	public Tela(Controller ctrl){
		initComponents();
		this.ctrl = ctrl;
		this.setVisible(true);
	}
	
	private void initComponents(){
		this.getContentPane().setLayout(new BorderLayout());
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setSize(800, 500);
		JPanel painelEsq = new JPanel();
		//painelEsq.setBackground(Color.BLUE);
		
		painelEsq.setLayout(new BorderLayout());
		
		
		JPanel painelBtns = new JPanel(); 
		
		painelEsq.add(painelBtns,BorderLayout.SOUTH);
		
		btnTraduzir = new JButton("Traduzir");
		btnTraduzir.setActionCommand("Traduzir");
		btnTraduzir.addActionListener(ctrl);
		
		btnClearCodigo = new JButton("Clear");
		btnClearCodigo.setActionCommand("ClearCod");
		btnClearCodigo.addActionListener(ctrl);
		
		painelBtns.add(btnTraduzir);
		painelBtns.add(btnClearCodigo);
		
		painelBtns.setLayout(new BoxLayout(painelBtns,BoxLayout.X_AXIS));
		
		JPanel painelCod = new JPanel();
		
		codigo = new JTextArea();
		codigo.setPreferredSize(new Dimension(300, 750));
		painelCod.add(codigo);
		//codigo.get
		
		painelEsq.add(painelCod);
		
		JPanel painelDir = new JPanel();
		painelDir.setBackground(Color.BLACK);
		//painelEsq.setSize(WIDTH, HEIGHT);
		
		this.getContentPane().add(painelDir, BorderLayout.CENTER);
		this.getContentPane().add(painelEsq, BorderLayout.LINE_START);
	}
	
}
