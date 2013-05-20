package client;

import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;

import javax.security.auth.login.LoginException;

import barker.BarkerServer;
import barker.ConnectionServer;

public class ClientMain {
	static final String SERVER = "127.0.0.1";

	public static void main(String[] args) {
		
		LoginWindow window = new LoginWindow();
		window.setVisible(true);
		MainWindow mainWin = new MainWindow();
		mainWin.setVisible(true);
		
		System.out.println("Lancement Client...");
		
		System.setProperty("java.rmi.server.hostname", SERVER);
		if (System.getSecurityManager() == null) { 
			System.setSecurityManager(new java.rmi.RMISecurityManager()); 
		}
		
		ConnectionServer conserver = null;
		try {
			Registry reg = LocateRegistry.getRegistry(SERVER,2001);
			conserver = (ConnectionServer) reg.lookup("Server");
		} catch (RemoteException | NotBoundException e) {
			e.printStackTrace();
			return;
		}
		
		BarkerServer bserver = null;
		try {
			bserver=conserver.logon("test", "test");
			System.out.println("On a recu une ref vers serveur distant, et on a �t� authentifi� ");
		} catch (RemoteException | LoginException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} 
		
		System.out.println("Fin Client");
	}

}
