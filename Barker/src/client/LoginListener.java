package client;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.rmi.RemoteException;

import javax.security.auth.login.LoginException;
import javax.swing.JButton;
import javax.swing.JOptionPane;

import barker.BarkerServerAuth;
import barker.ConnectionServer;

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
			MainWindow mainWin = new MainWindow(server, loginWindow.getTrayIcon());
			mainWin.setVisible(true);
		} catch (RemoteException | LoginException e) {
			JOptionPane.showConfirmDialog(loginWindow, "Identifiants incorrects: " + e.getMessage(),
					MainWindow.APPNAME, JOptionPane.CLOSED_OPTION, JOptionPane.ERROR_MESSAGE);
			System.out.println("Connexion impossible: " + e.getMessage());
			e.printStackTrace();
		}
	}

}
