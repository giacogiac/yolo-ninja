package client;

import java.awt.Font;
import java.awt.GridLayout;
import javax.swing.JLabel;
import javax.swing.JPanel;

import barker.Bark;

public class BarkPanel extends JPanel {
    private static final long serialVersionUID = 1L;
    
    private JLabel userLabel;
	private JLabel messageLabel;
	private Bark bark;

	public BarkPanel(Bark bark) {
		this.bark = bark;
		this.setLayout(new GridLayout(0, 1));
		userLabel = new JLabel(bark.getUsername() + " a envoyé le "+bark.getSendtime()+" : ");
		userLabel.setFont(new Font("sender", Font.ITALIC, 10));
		messageLabel = new JLabel(bark.getMessage());
		this.add(userLabel);
		this.add(messageLabel);
		
	}
	
	public String toString() {
		return "<html>"+userLabel.getText()+"<br>"+messageLabel.getText()+"</html>";
	}
	
	public Bark getBark() {
		return bark;
	}
	
}
