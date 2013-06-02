package client;

import java.awt.TrayIcon;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;

import javax.imageio.ImageIO;
import javax.swing.JOptionPane;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;

import security.module.RMISSLClientSocketFactory;

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
		
		try {
			UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
		} catch (UnsupportedLookAndFeelException e) {
			System.out.println("Unable to load native look and feel");
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (InstantiationException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
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
		
	}

}
