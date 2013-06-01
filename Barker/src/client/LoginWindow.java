package client;

import java.awt.Color;
import java.awt.GridLayout;
import java.awt.TrayIcon;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.rmi.RemoteException;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;

import barker.ConnectionServer;

public class LoginWindow extends JFrame {
	
	private ConnectionServer conserver;
	private TrayIcon trayIcon;
	private JTextField loginField;
	private JPasswordField mdpField;
	
	public LoginWindow(TrayIcon trayIcon) {
		super();
		this.trayIcon = trayIcon;
		this.setDefaultCloseOperation(EXIT_ON_CLOSE);
		this.setLayout(new GridLayout(0, 1));
		this.setTitle(MainWindow.APPNAME);
		this.setBackground(Color.blue);
		
		JLabel login = new JLabel("  login : ");
		loginField = new JTextField();
		JPanel loginPanel = new JPanel(new GridLayout(1, 0));
		loginPanel.add(login);
		loginPanel.add(loginField);
		this.add(loginPanel);
		
		JLabel mdp = new JLabel("  mot de passe : ");
		mdpField = new JPasswordField();
		JPanel mdpPanel = new JPanel(new GridLayout(1, 0));
		mdpPanel.add(mdp);
		mdpPanel.add(mdpField);
		this.add(mdpPanel);
		
		JButton submit = new JButton("Login");
		
		submit.addActionListener(new LoginListener(this));
		
		this.add(submit);
		
		JButton register = new JButton("S'inscrire");
		final LoginWindow caller = this;
		register.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent arg0) {
				//enabled
				new SubscribeWindow(caller).setVisible(true);
				
			}
		});
		
		JButton anon = new JButton("Connexion anonyme");
		JPanel buttonPanel = new JPanel();
		buttonPanel.add(register);
		buttonPanel.add(anon);
		
		final LoginWindow logWin = this;
		
		anon.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				try {
					logWin.dispose();
					new MainWindow(conserver, null, conserver.anon(), getTrayIcon(), loginField.getText()).setVisible(true);
				} catch (RemoteException e1) {
					JOptionPane.showConfirmDialog(logWin, "Erreur : "+e1.getMessage(), 
							MainWindow.APPNAME, JOptionPane.CLOSED_OPTION, JOptionPane.ERROR_MESSAGE);
					e1.printStackTrace();
				}
				
			}
		});
		
		this.add(buttonPanel);
		
		this.pack();
		this.setResizable(false);
	}

	public void setServer(ConnectionServer conserver) {
		this.conserver = conserver;
		
	}
	
	public ConnectionServer getServer() {
		return conserver;
	}
	
	public TrayIcon getTrayIcon() {
		return trayIcon;
	}

	public JTextField getLoginField() {
		return loginField;
	}

	public JPasswordField getMdpField() {
		return mdpField;
	}

}
