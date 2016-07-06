package view;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.util.Map;
import java.util.TreeMap;

import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;

import controller.Controller;

public class Tela extends JFrame {

	private static final long serialVersionUID = 1L;
	
	private JButton btnTraduzir;
	private JButton btnClearCodigo;
	private JButton executeMicroInstruction;
	
	private JTextArea codigo;
	
	private JTextField ax,bx,cx,dx,ds,ir,pc,mar,mbr,p1,p2;
	
	private JPanel memoria;
	
	private Map<Integer,MemCell> memCells;
	
	private Controller ctrl;
	
	public Tela(Controller ctrl){
		initComponents();
		this.ctrl = ctrl;
		this.setVisible(true);
	}
	
	private void initComponents(){
		memCells = new TreeMap<Integer,MemCell>();
		this.setTitle("Assembly Simulator");
		
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
		
		btnClearCodigo = new JButton("Limpar Memória");
		btnClearCodigo.setActionCommand("Clear");
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
		painelEsq.setBorder(BorderFactory.createTitledBorder("Código"));
		
		JPanel painelDir = new JPanel();
		//painelDir.setBackground(Color.BLACK);
		//painelEsq.setSize(WIDTH, HEIGHT);
		
		GridLayout g = new GridLayout(4,1);
		
		painelDir.setLayout(new BoxLayout(painelDir,BoxLayout.Y_AXIS));
		
		
		memoria = new JPanel();
		memoria.setLayout(new FlowLayout());
		
		JScrollPane scroll = new JScrollPane(memoria,JScrollPane.VERTICAL_SCROLLBAR_NEVER, JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
		memoria.setBorder(BorderFactory.createTitledBorder("Memória"));
		
		JPanel regs = new JPanel();
		regs.setLayout(new GridBagLayout());
		GridBagConstraints c = new GridBagConstraints();
		
		JPanel ax = new JPanel();
		
		painelDir.add(scroll);
		painelDir.setBorder(BorderFactory.createTitledBorder("Status"));
		
		this.getContentPane().add(painelDir, BorderLayout.CENTER);
		this.getContentPane().add(painelEsq, BorderLayout.LINE_START);
	}
	
	public void setMemCell(int id, int val){
		MemCell m = this.memCells.get(id);
		if(m!=null){
			m.setVal(val);
		} else {
			m = new MemCell(id,val);
			memCells.put(id, new MemCell(id,val));
			atualizarPainelMem();
		}
		memoria.repaint();
	}

	private void atualizarPainelMem() {
		memoria.removeAll();
		int width=0;
		for(MemCell a : memCells.values()){
			memoria.add(a);
			width++;
		}
		memoria.setPreferredSize(new Dimension(60,70*width));
	}
}
