package client;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class RestoreActionListener implements ActionListener {

	private MainWindow mainWindow;
	
	public RestoreActionListener(MainWindow window) {
		this.mainWindow = window;
	}
	
	@Override
	public void actionPerformed(ActionEvent e) {
		mainWindow.setVisible(true);
	}

}
