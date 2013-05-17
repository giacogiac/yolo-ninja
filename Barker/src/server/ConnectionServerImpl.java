package server;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;

import javax.security.auth.login.LoginContext;
import javax.security.auth.login.LoginException;

import barker.ConnectionServer;

public class ConnectionServerImpl extends UnicastRemoteObject implements ConnectionServer {
	private static final long serialVersionUID = -3634531286982118660L;
	
	protected ConnectionServerImpl() throws RemoteException {
		super();
	}
	
	@Override
	public BarkerServerImpl logon(String username, String passwd) throws RemoteException, LoginException {
		// Verifier si l'utilisateur a bien donné un login et passwd egaux à testUser et testPasswd
		// Si non, renvoyer une instance de LoginException 
		
		LoginContext lc = new LoginContext("BarkerServer", new security.module.RemoteCallbackHandler(username, passwd));
		try{
			lc.login();
			System.out.println("Authentifié le sujet "+ lc.getSubject());
		}
		catch (LoginException e){
			System.out.println("Recu "+ username + " et " + passwd + " mais, après vérif, ils sont incorrects");
			throw e;
		}
		return new BarkerServerImpl(lc.getSubject());
	}

}
