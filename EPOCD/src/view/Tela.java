package view;

import java.awt.BorderLayout;
import java.awt.Color;

import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JTextArea;

public class Tela extends JFrame {

	private static final long serialVersionUID = 1L;
	
	private JButton btnTraduzir;
	private JButton btnClearCodigo;
	
	private JTextArea codigo;
	
	public Tela(){
		initComponents();
		this.setVisible(true);
	}
	
	private void initComponents(){
		this.getContentPane().setLayout(new BorderLayout());
		this.setDefaultCloseOperation(DISPOSE_ON_CLOSE);
		this.setSize(800, 500);
		JPanel painelEsq = new JPanel();
		painelEsq.setBackground(Color.BLUE);
		
		painelEsq.setLayout(new BorderLayout());
		
		
		JPanel painelBtns = new JPanel(); 
		
		painelEsq.add(painelBtns,BorderLayout.SOUTH);
		
		btnTraduzir = new JButton("Traduzir");
		btnTraduzir.setActionCommand("Traduzir");
		
		btnClearCodigo = new JButton("Clear");
		btnClearCodigo.setActionCommand("ClearCod");
		
		painelBtns.add(btnTraduzir);
		painelBtns.add(btnClearCodigo);
		
		painelBtns.setLayout(new BoxLayout(painelBtns,BoxLayout.X_AXIS));
		
		JPanel painelCod = new JPanel();
		
		codigo = new JTextArea();
		//codigo.
		
		JPanel painelDir = new JPanel();
		painelDir.setBackground(Color.BLACK);
		//painelEsq.setSize(WIDTH, HEIGHT);
		
		this.getContentPane().add(painelDir, BorderLayout.CENTER);
		
	}
	
}
