package view;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import javax.swing.table.AbstractTableModel;

import componentes.Memoria;
import model.Palavra;

public class MemoriaModel extends AbstractTableModel {

	private static final long serialVersionUID = 1L;
	private Map<Long,Palavra> mem;
	private List<Entry<Long,Palavra>> l;
	private String[] columnNames;
	public MemoriaModel(Memoria m){
		mem = m.getMap();
		columnNames = new String[]{"Posição","Valor Inteiro"};
		l = new ArrayList<Entry<Long,Palavra>>(mem.entrySet());
	}
	
	public void update(){
		l.clear();
		l.addAll(mem.entrySet());
	}
	
	@Override
	public int getRowCount() {
		return mem.size();
	}

	@Override
	public String getColumnName(int col) {
        return columnNames[col];
    }
	
	@Override
	public int getColumnCount() {
		return 2;
	}

	@Override
	public Object getValueAt(int rowIndex, int columnIndex) {
		if(rowIndex<mem.size() && rowIndex>=0){
			switch(columnIndex){
			case 0: return l.get(rowIndex).getKey();
			case 1: return l.get(rowIndex).getValue().getIntValue();
			default: return null;
			}
		}
		return null;
	}

}
