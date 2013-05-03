package client;

import javax.swing.JWindow;
import javax.swing.SwingUtilities;


public class Main {

	public static void main(String[] args) {
		SwingUtilities.invokeLater(new Runnable(){
			public void run(){
				MainWindow window = new MainWindow("Barker");
				window.setVisible(true);//On la rend visible
			}
		});
		
	}

}
