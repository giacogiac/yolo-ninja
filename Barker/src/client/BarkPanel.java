package client;

import java.awt.Font;
import java.awt.GridLayout;
import javax.swing.JLabel;
import javax.swing.JPanel;

import barker.Bark;

public class BarkPanel extends JPanel {

	public BarkPanel(Bark bark) {
		
		this.setLayout(new GridLayout(0, 1));
		JLabel userLabel = new JLabel(bark.getUsername() + "a envoyé le "+bark.getSendtime());
		userLabel.setFont(new Font("sender", Font.ITALIC, 10));
		JLabel messageLabel = new JLabel(bark.getMessage());
		this.add(userLabel);
		this.add(messageLabel);
		
	}
	
}
