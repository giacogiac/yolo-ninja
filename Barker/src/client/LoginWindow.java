package client;

import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.LayoutManager;
import java.awt.TextArea;
import java.awt.TrayIcon;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.rmi.RemoteException;

import javax.print.attribute.standard.Severity;
import javax.security.auth.login.LoginException;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;

import barker.BarkerServerAuth;
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
		
		JLabel login = new JLabel("login : ");
		loginField = new JTextField();
		JPanel loginPanel = new JPanel(new GridLayout(1, 0));
		loginPanel.add(login);
		loginPanel.add(loginField);
		this.add(loginPanel);
		
		JLabel mdp = new JLabel("mot de passe : ");
		mdpField = new JPasswordField();
		JPanel mdpPanel = new JPanel(new GridLayout(1, 0));
		mdpPanel.add(mdp);
		mdpPanel.add(mdpField);
		this.add(mdpPanel);
		
		JButton submit = new JButton("Login");
		
		submit.addActionListener(new LoginListener(this));
		
		this.add(submit);
		
		JButton register = new JButton("S'inscrire");
		JButton anon = new JButton("Connexion anonyme");
		JPanel buttonPanel = new JPanel(new GridLayout(1, 0));
		buttonPanel.add(register);
		buttonPanel.add(anon);
		
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
