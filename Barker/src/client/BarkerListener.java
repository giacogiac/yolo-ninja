package client;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.rmi.RemoteException;

import javax.swing.JOptionPane;

import barker.BarkerServerAuth;

public class BarkerListener implements ActionListener {
	
	private MainWindow mainWin;

	public BarkerListener(MainWindow window) {
		this.mainWin = window;
	}

	@Override
	public void actionPerformed(ActionEvent arg0) {
		BarkerServerAuth server = mainWin.getAuth();
		try {
			if (!mainWin.getBarkArea().getText().equals("")) {
				server.bark(mainWin.getBarkArea().getText());
				mainWin.getBarkArea().setText("");
				mainWin.refreshLists();
			}
		} catch (RemoteException e) {
			JOptionPane.showConfirmDialog(mainWin, "Erreur lors de l'envoi du bark : \n"+e.getMessage(), 
					MainWindow.APPNAME, JOptionPane.CLOSED_OPTION, JOptionPane.ERROR_MESSAGE);
			e.printStackTrace();
		}
	}

}
