package server;

import barker.Bark;
import barker.BarkerServer;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.security.PrivilegedActionException;
import java.security.PrivilegedExceptionAction;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import javax.security.auth.Subject;

import barker.Doggy;

public class BarkerServerImpl extends UnicastRemoteObject implements BarkerServer {
	private static final long serialVersionUID = -7666130486088047448L;
	
	private static HashMap<String, String> loginMdp = new HashMap<String, String>();
	private static HashMap<String, Doggy> loginDoggy = new HashMap<String, Doggy>();
	private static HashMap<String, List<String>> sniffers = new HashMap<String, List<String>>();
	private static List<Bark> barks = new ArrayList<Bark>();
	
	private Subject subject;

	

	protected BarkerServerImpl(Subject subject) throws RemoteException {
		super();
		this.subject = subject;
	}
	
	public void register(String login, String mdp) throws RemoteException {
		loginMdp.put(login, mdp);
		loginDoggy.put(login, new Dog(login, this));
	}
	
	public Doggy connect(String login, String mdp) throws RemoteException {
		if (mdp.equals(loginMdp.get(login))) {
			return loginDoggy.get(login);
		}
		return null;
	}

	public List<Bark> getBarks() {
		return barks;
	}
	
	public List<String> getSniffers(String login) {
		return	sniffers.get(login);
	}
	
	public HashMap<String, List<String>> getSniffers() {
		return sniffers;
	}
	
	public synchronized int setVal(final int v)
			throws RemoteException {
		int val = 0;
		try {
			System.out.println("Nous dit qui est le sujet : " + subject + " ");
			val = (Integer) Subject.doAsPrivileged(subject,
					new PrivilegedExceptionAction<Object>() {
						public void ecrireFichier() {
							FileOutputStream fo;
							try {
								fo = new FileOutputStream(
										new File("foo.txt"));
								fo.write(new byte[] { 'c', 'o', 'u', 'c', 'o',
										'u' });
								// si on n a pas de permission de write fichier,
								// ceci ne fonctionne pas
								// dès lors que nous avons un securityManager
								// activé, sauf si on grant explicitement la
								// permission File.io.Permission "essaiResultat"
								// , "write";
								fo.close();
							} catch (IOException e1) {
								e1.printStackTrace();
							}
						}

						public Object run() throws Exception {
							Thread.sleep(500);
							System.out.println("Nouvelle " + v);
							this.ecrireFichier();
							return 40 * v;
						}
					}, null);
		} catch (PrivilegedActionException pae) {
			if (pae.getException() instanceof RemoteException)
				throw (RemoteException) pae.getException();
		}
		return val;
	}

}
