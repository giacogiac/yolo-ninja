package client;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.rmi.RemoteException;

import javax.security.auth.login.LoginException;
import javax.swing.JDialog;
import javax.swing.JOptionPane;

import barker.ConnectionServer;

public class InscriptionListener implements ActionListener {

	private SubscribeWindow window;
	
	public InscriptionListener(SubscribeWindow window) {
		this.window = window;
	}
	
	@Override
	public void actionPerformed(ActionEvent e) {
		String login = window.getLogin();
		String mdp = window.getMdp();
		String confirm = window.getConfirm();
		ConnectionServer conserver = window.getServer();
		if (login.equals(null) || login.equals(""))
			JOptionPane.showConfirmDialog(window, "Inscription impossible : \nveuillez renseigner un login",
					MainWindow.APPNAME, JOptionPane.CLOSED_OPTION, JOptionPane.ERROR_MESSAGE);
		else if (mdp.equals(null) || mdp.equals(""))
			JOptionPane.showConfirmDialog(window, "Inscription impossible : \nveuillez renseigner un mot de passe", 
					MainWindow.APPNAME, JOptionPane.CLOSED_OPTION, JOptionPane.ERROR_MESSAGE);
		else if (! mdp.equals(confirm)) 
			JOptionPane.showConfirmDialog(window, "Inscription impossible : \nla confirmation du mot de passe ne correspond pas", 
					MainWindow.APPNAME, JOptionPane.CLOSED_OPTION, JOptionPane.ERROR_MESSAGE);
		else {
			try {
				conserver.addUser(login, new String(mdp));
				JOptionPane.showConfirmDialog(window, "Bienvenue sur Barker "+login+" !\nVotre inscription est terminée.", 
						MainWindow.APPNAME, JOptionPane.CLOSED_OPTION, JOptionPane.INFORMATION_MESSAGE);
				window.dispose();
			} catch (LoginException e1) {
				JOptionPane.showConfirmDialog(window, "Inscription impossible : "+e1.getMessage(), 
						MainWindow.APPNAME, JOptionPane.CLOSED_OPTION, JOptionPane.ERROR_MESSAGE);
				e1.printStackTrace();
			} catch (RemoteException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
		}

	}

}
