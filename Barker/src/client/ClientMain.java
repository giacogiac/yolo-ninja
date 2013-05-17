package client;

import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;

import javax.security.auth.login.LoginException;

import security.module.RMISSLClientSocketFactory;

import barker.BarkerServer;
import barker.ConnectionServer;

public class ClientMain {
	public static final String SERVER = "127.0.0.1";

	public static void main(String[] args) {
		System.out.println("Lancement Client...");

		System.setProperty("javax.net.ssl.trustStore", "barker.ks");
		System.setProperty("java.security.policy", "policy.conf");
		if (System.getSecurityManager() == null) { 
			System.setSecurityManager(new java.rmi.RMISecurityManager()); 
		}
		
		ConnectionServer conserver = null;
		try {
			RMISSLClientSocketFactory clientFactory = new RMISSLClientSocketFactory();
			Registry reg= LocateRegistry.getRegistry(SERVER, 2001, clientFactory);
			conserver = (ConnectionServer) reg.lookup("Server");
		} catch (RemoteException | NotBoundException e) {
			e.printStackTrace();
			return;
		}
		
		BarkerServer bserver = null;
		try {
			bserver = conserver.logon("Babouchot", "passw");
			System.out.println("On a recu une ref vers serveur distant, et on a été authentifié ");
		} catch (RemoteException | LoginException e) {
			System.out.println("Connexion impossible: " + e.getMessage());
		} 
		
		System.out.println("Fin Client");
	}

}
