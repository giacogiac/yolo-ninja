package server;

import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;

import javax.security.auth.login.LoginException;

import classserver.ClassFileServer;

import security.module.RMISSLClientSocketFactory;
import security.module.RMISSLServerSocketFactory;

import barker.ConnectionServer;

public class ServerMain {
	public static final String HOST = "127.0.0.1";

	static public void main(String[] args) {
	    System.out.println("Lancement ClassServer...");
	    
	    int port = 2002;
	    String classpath = null;
	    try {
	        new ClassFileServer(port, classpath);
	    } catch (java.io.IOException e) {
	        System.out.println("Unable to start ClassServer: " + e.getMessage());
	        e.printStackTrace();
	    }
	    
		System.out.println("Lancement Serveur Barker...");
		
		// sécurité
		System.setProperty("javax.net.ssl.keyStore", "barker.ks");
		System.setProperty("javax.net.ssl.keyStorePassword", "azerty");
		System.setProperty("java.security.policy", "server.policy");
		System.setProperty("java.security.auth.login.config", "login.conf");
		if (System.getSecurityManager() == null) { 
			System.setSecurityManager(new java.rmi.RMISecurityManager()); 
		}
		
		//initialisation
		try {
			BarkerServerAuthImpl.addUser("BabouChot", "passw");
			BarkerServerAuthImpl.addUser("giaco", "gpassw");
		} catch (LoginException e) {
			e.printStackTrace();
		}
		
		// lancement
		try {
			RMISSLServerSocketFactory sslServer = new RMISSLServerSocketFactory();
			RMISSLClientSocketFactory sslClient = new RMISSLClientSocketFactory();
			Registry reg= LocateRegistry.createRegistry(2001, sslClient, sslServer);
			ConnectionServer conserver = new ConnectionServerImpl(0, sslClient, sslServer);
			reg.rebind("Server", conserver);
			System.out.println("Serveur en cours ...");
		} catch (RemoteException e) {
			e.printStackTrace();
		}
	}

}
