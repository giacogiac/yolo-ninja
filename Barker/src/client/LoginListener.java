package client;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.rmi.RemoteException;

import javax.security.auth.login.LoginException;
import javax.swing.JOptionPane;

import barker.BarkerServerAuth;

public class LoginListener implements ActionListener {

	private LoginWindow loginWindow;

	public LoginListener (LoginWindow loginWindow) {
		this.loginWindow = loginWindow;
	}
	
	@Override
	public void actionPerformed(ActionEvent arg0) {
		BarkerServerAuth server = null;
		try {
			server = loginWindow.getServer().logon(loginWindow.getLoginField().getText(),
					new String(loginWindow.getMdpField().getPassword()));
			System.out.println("On a recu une ref vers serveur distant, et on a été authentifié ");
			loginWindow.dispose();
			MainWindow mainWin = new MainWindow(loginWindow.getServer(), server, loginWindow.getServer().anon(), 
					loginWindow.getTrayIcon(), loginWindow.getLoginField().getText());
			mainWin.setVisible(true);
		} catch (RemoteException | LoginException e) {
			JOptionPane.showConfirmDialog(loginWindow, "Identifiants incorrects: " + e.getMessage(),
					MainWindow.APPNAME, JOptionPane.CLOSED_OPTION, JOptionPane.ERROR_MESSAGE);
			System.out.println("Connexion impossible: " + e.getMessage());
			e.printStackTrace();
		}
	}

}
