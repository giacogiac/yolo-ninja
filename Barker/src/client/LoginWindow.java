package client;

import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.LayoutManager;
import java.awt.TextArea;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;

public class LoginWindow extends JFrame {
	
	public LoginWindow() {
		super();
		this.setDefaultCloseOperation(EXIT_ON_CLOSE);
		this.setLayout(new GridLayout(0, 1));
		this.setTitle(MainWindow.APPNAME);
		this.setBackground(Color.blue);
		
		JLabel login = new JLabel("login : ");
		JTextField loginField = new JTextField();
		JPanel loginPanel = new JPanel(new GridLayout(1, 0));
		loginPanel.add(login);
		loginPanel.add(loginField);
		this.add(loginPanel);
		
		JLabel mdp = new JLabel("mot de passe : ");
		JPasswordField mdpField = new JPasswordField();
		JPanel mdpPanel = new JPanel(new GridLayout(1, 0));
		mdpPanel.add(mdp);
		mdpPanel.add(mdpField);
		this.add(mdpPanel);
		
		JButton submit = new JButton("Login");
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

}
