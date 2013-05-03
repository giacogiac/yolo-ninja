package client;

import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.HeadlessException;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;


public class MainWindow extends JFrame {
	
	public MainWindow(String title) throws HeadlessException {
		super(title);
		
		build();
	}

	private void build(){
		setSize(800,600); //On donne une taille à notre fenêtre
		setLocationRelativeTo(null); //On centre la fenêtre sur l'écran
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); //On dit à l'application de se fermer lors du clic sur la croix
		setContentPane(buildContentPane());
	}
	
	private JPanel buildContentPane(){
		JPanel panel = new JPanel();
		panel.setBackground(new Color(0, 183, 255));
		panel.setLayout(new FlowLayout());
	 
		RoundedButton bouton = new RoundedButton("Cliquez ici !", 20);
		panel.add(bouton);
 
		JButton bouton2 = new JButton("Ou là !");
		//bouton2.setBorder(new RoundedBorder(10));
		panel.add(bouton2);
 
		return panel;
	}

}
