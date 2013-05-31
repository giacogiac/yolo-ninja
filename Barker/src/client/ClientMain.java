package client;

import java.awt.AWTException;
import java.awt.SystemTray;
import java.awt.TrayIcon;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;

import javax.imageio.ImageIO;
import javax.security.auth.login.LoginException;
import javax.swing.Icon;
import javax.swing.JOptionPane;

import security.module.RMISSLClientSocketFactory;

import barker.BarkerServerAuth;
import barker.ConnectionServer;

public class ClientMain {
	public static final String SERVER = "127.0.0.1";

	public static void main(String[] args) {
		
		BufferedImage picture = null;
		TrayIcon trayIcon = null;
		try {
			picture = ImageIO.read(new File("rsrc/barker.jpg"));
			trayIcon = new TrayIcon(picture, MainWindow.APPNAME);
		} catch (IOException e1) {
			e1.printStackTrace();
		}
		
		
		
		LoginWindow window = new LoginWindow(trayIcon);
		
		System.out.println("Lancement Client...");

		System.setProperty("javax.net.ssl.trustStore", "barker.ks");
		System.setProperty("java.security.policy", "client.policy");
		if (System.getSecurityManager() == null) { 
			System.setSecurityManager(new java.rmi.RMISecurityManager()); 
		}
		
		ConnectionServer conserver = null;
		try {
			RMISSLClientSocketFactory clientFactory = new RMISSLClientSocketFactory();
			Registry reg= LocateRegistry.getRegistry(SERVER, 2001, clientFactory);
			conserver = (ConnectionServer) reg.lookup("Server");
		} catch (RemoteException | NotBoundException e) {
			JOptionPane.showConfirmDialog(window, "Connexion au serveur impossible : "+e.getMessage(), 
					MainWindow.APPNAME, JOptionPane.CLOSED_OPTION, JOptionPane.ERROR_MESSAGE);
			e.printStackTrace();
			System.exit(0);
		}
		
		window.setServer(conserver);
		window.setVisible(true);
		
		
		System.out.println("Fin Client");
	}

}
