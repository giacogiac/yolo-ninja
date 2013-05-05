package client;

import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;

import javax.swing.JWindow;
import javax.swing.SwingUtilities;

import barker.Doggy;
import barker.ServerInterface;


public class ClientMain {

	public static void main(String[] args) {
		SwingUtilities.invokeLater(new Runnable(){
			public void run(){
				MainWindow window = new MainWindow("Barker");
				window.setVisible(true);//On la rend visible
			}
		});
		
		
		//connection au serveur Barker
		try {
			String serveurURl = "192.168.1.87";
			System.out.println("Debut... " + Thread.currentThread().getName());
			Registry reg = LocateRegistry.getRegistry(serveurURl,2001);
			ServerInterface serv = (ServerInterface) reg.lookup("BARKER");
			// connection ou inscription
		} catch (RemoteException e) {
			e.printStackTrace();
		} catch (NotBoundException e) {
			e.printStackTrace();
		}
		
	}

}
