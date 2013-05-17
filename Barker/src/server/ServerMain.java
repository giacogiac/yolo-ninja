package server;

import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;

import barker.ConnectionServer;

public class ServerMain {
	public static final String HOST = "157.169.103.27";

	static public void main(String[] args) {
		System.out.println("Lancement Serveur Barker...");
		
		System.setProperty("java.security.policy", "serverpolicy.conf");
		System.setProperty("java.security.auth.login.config", "login.conf");
		if (System.getSecurityManager() == null) { 
			System.setSecurityManager(new java.rmi.RMISecurityManager()); 
		}
		
		try {
			Registry reg = LocateRegistry.createRegistry(2001);
			ConnectionServer conserver = new ConnectionServerImpl();
			reg.rebind("Server", conserver);
			System.out.println("Serveur en cours ...");
		} catch (RemoteException e) {
			e.printStackTrace();
		}
	}

}
