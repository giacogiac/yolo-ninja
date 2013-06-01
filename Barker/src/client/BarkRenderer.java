package client;

import java.awt.Component;

import javax.swing.JList;
import javax.swing.JMenuItem;
import javax.swing.JPopupMenu;
import javax.swing.ListCellRenderer;

public class BarkRenderer implements ListCellRenderer<BarkPanel> {

	@Override
	public Component getListCellRendererComponent(
			JList<? extends BarkPanel> list	, BarkPanel value, int index,
			boolean isSelected, boolean cellHasFocus) {
		
		//Get the selected index. (The index param isn't
        //always valid, so just use the value.)
		
        if (isSelected) {
            list.setBackground(list.getSelectionBackground());
            list.setForeground(list.getSelectionForeground());
        } else {
            list.setBackground(list.getBackground());
            list.setForeground(list.getForeground());
        }
        	
        value.setEnabled(true);
        
		return value;
	}

}
