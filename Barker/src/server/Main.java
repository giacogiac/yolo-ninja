package server;

import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;

public class Main {

	static public void main(String[] args) {
		//String address = "url:port";
		//System.setProperty("java.rmi.server.codebase",address);
		try {
			Registry reg = LocateRegistry.createRegistry(2001);
			Server barkerServer = new Server();
			reg.rebind("BARKER", barkerServer);
			System.out.println("Lancement Serveur Barker...");
		} catch (RemoteException e) {
			e.printStackTrace();
		}
	}

}
