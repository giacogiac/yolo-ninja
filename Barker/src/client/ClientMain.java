package client;

import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;

import javax.security.auth.login.LoginException;

import barker.BarkerServer;
import barker.ConnectionServer;

public class ClientMain {
	public static final String SERVER = "127.0.0.1";

	public static void main(String[] args) {
		System.out.println("Lancement Client...");

		System.setProperty("java.security.policy", "policy.conf");
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
			bserver = conserver.logon("adminUser", "testPassword");
			System.out.println("On a recu une ref vers serveur distant, et on a été authentifié ");
			System.out.println(bserver.setVal(5));
		} catch (RemoteException | LoginException e) {
			e.printStackTrace();
		} 
		
		System.out.println("Fin Client");
	}

}
