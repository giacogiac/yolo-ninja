package client;

import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;

import barker.ConnectionServer;

public class SubscribeWindow extends JFrame {
    private static final long serialVersionUID = 1L;
    
    private JTextField loginField;
	private JPasswordField mdpField;
	private JPasswordField confirmField;
	private LoginWindow logWin;
	
	public SubscribeWindow(final LoginWindow window) {
		this.logWin = window;
		this.setDefaultCloseOperation(DISPOSE_ON_CLOSE);
		this.setLayout(new GridLayout(0, 1));
		
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
		
		JLabel confirm = new JLabel("  Confirmer mot de passe : ");
		confirmField = new JPasswordField();
		JPanel confirmPanel = new JPanel(new GridLayout(1, 0));
		confirmPanel.add(confirm);
		confirmPanel.add(confirmField);
		this.add(confirmPanel);
		
		JPanel buttonsPanel = new JPanel();
		JButton cancel = new JButton("Annuler");
		JButton ok = new JButton("Valider");
		buttonsPanel.add(cancel);
		buttonsPanel.add(ok);
		
		cancel.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent arg0) {
				dispose();
			}
		});
		
		ok.addActionListener(new InscriptionListener(this));
		
		this.add(buttonsPanel);
		this.pack();
		this.setResizable(false);

	}

	public String getLogin() {
		return loginField.getText();
	}

	public String getMdp() {
		return new String(mdpField.getPassword());
	}

	public String getConfirm() {
		return new String(confirmField.getPassword());
	}
	
	public ConnectionServer getServer() {
		return logWin.getServer();
	}
	
}
