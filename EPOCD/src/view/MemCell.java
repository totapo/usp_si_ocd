package view;

import java.awt.Color;
import java.awt.Dimension;

import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.JLabel;
import javax.swing.JPanel;


public class MemCell extends JPanel{
	private static final long serialVersionUID = 1L;
	private JLabel posicao,valorLbl;
	
	private int id,valor;
	
	public MemCell(int id,int valor){
		this.setLayout(new BoxLayout(this,BoxLayout.Y_AXIS));
		
		this.setBackground(Color.WHITE);
		this.setBorder(BorderFactory.createLineBorder(Color.black));
		
		this.id = id;
		this.valor = valor;
		
		this.setPreferredSize(new Dimension(60,50));
		
		this.posicao = new JLabel("Pos: "+this.id);
		this.valorLbl = new JLabel(""+valor);
		
		this.add(posicao);
		this.add(valorLbl);
	}
	
	public void setVal(int val){
		this.valor = val;
		valorLbl.setText(""+valor);
	}
	
	public int getId(){
		return id;
	}
	
}
