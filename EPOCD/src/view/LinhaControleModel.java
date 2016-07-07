package view;

import javax.swing.table.AbstractTableModel;

import componentes.Firmware;
import model.LinhaControle;

public class LinhaControleModel extends AbstractTableModel{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private LinhaControle[] linhasControle;
	private String[] columnNames;
	public LinhaControleModel(){
		linhasControle = Firmware.instrucoes;
		columnNames = new String[]{"Portas", "Jump", "Próx.", "ULA", "RWAV", "Decode", "Linha"};
	}
	
	@Override
	public int getRowCount() {
		return linhasControle.length;
	}
	
	@Override
	public String getColumnName(int col) {
        return columnNames[col];
    }

	@Override
	public int getColumnCount() {
		return columnNames.length;
	}

	@Override
	public Object getValueAt(int rowIndex, int columnIndex) {
		if(rowIndex>linhasControle.length || rowIndex<0) columnIndex=-1;
		switch(columnIndex){
		case 0:return stringfy(linhasControle[rowIndex].getPortas());
		case 1:return linhasControle[rowIndex].getJmpCond();
		case 2:return linhasControle[rowIndex].getProx();
		case 3:return linhasControle[rowIndex].getULA();
		case 4:return stringfy(linhasControle[rowIndex].getRWAV());
		case 5:return linhasControle[rowIndex].getDecode();
		case 6:return rowIndex;
		default: return null;
		}
	}

	private String stringfy(byte[] a) {
		String resp = "";
		for(byte b:a) resp+=b;
		return resp;
	}

}
