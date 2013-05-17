package server;

import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;

import javax.security.auth.login.LoginException;

import security.module.BarkerServerLoginModule;
import security.module.RMISSLClientSocketFactory;
import security.module.RMISSLServerSocketFactory;

import barker.ConnectionServer;

public class ServerMain {
	public static final String HOST = "157.169.103.27";

	static public void main(String[] args) {
		System.out.println("Lancement Serveur Barker...");
		
		System.setProperty("javax.net.ssl.keyStore", "barker.ks");
		System.setProperty("javax.net.ssl.keyStorePassword", "azerty");
		System.setProperty("java.security.policy", "serverpolicy.conf");
		System.setProperty("java.security.auth.login.config", "login.conf");
		if (System.getSecurityManager() == null) { 
			System.setSecurityManager(new java.rmi.RMISecurityManager()); 
		}
		
		try {
			BarkerServerLoginModule.addUser("babouchot", "passw");
			BarkerServerLoginModule.addUser("giaco", "gpassw");
		} catch (LoginException e) {
			e.printStackTrace();
		}
		
		RMISSLServerSocketFactory sslServer = new RMISSLServerSocketFactory();
		RMISSLClientSocketFactory sslClient = new RMISSLClientSocketFactory();
		
		try {
			Registry reg= LocateRegistry.createRegistry(2001, sslClient, sslServer);
			ConnectionServer conserver = new ConnectionServerImpl(0, sslClient, sslServer);
			reg.rebind("Server", conserver);
			System.out.println("Serveur en cours ...");
		} catch (RemoteException e) {
			e.printStackTrace();
		}
	}

}
