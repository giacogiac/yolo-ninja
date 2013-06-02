package client;

import java.awt.Color;
import java.awt.Component;

import javax.swing.JList;
import javax.swing.ListCellRenderer;

public class BarkRenderer implements ListCellRenderer<BarkPanel> {

	@Override
	public Component getListCellRendererComponent(
			JList<? extends BarkPanel> list	, BarkPanel value, int index,
			boolean isSelected, boolean cellHasFocus) {

		//Get the selected index. (The index param isn't
		//always valid, so just use the value.)

		if(isSelected)
	        value.setBackground(Color.CYAN);
		else 
			value.setBackground(Color.WHITE);
		
		return value;
	}

}
