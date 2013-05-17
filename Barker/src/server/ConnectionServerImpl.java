package server;

import java.rmi.RemoteException;
import java.rmi.server.RMIClientSocketFactory;
import java.rmi.server.RMIServerSocketFactory;
import java.rmi.server.UnicastRemoteObject;

import javax.security.auth.login.LoginContext;
import javax.security.auth.login.LoginException;

import barker.ConnectionServer;

public class ConnectionServerImpl extends UnicastRemoteObject implements ConnectionServer {
	private static final long serialVersionUID = -3634531286982118660L;
	
	private RMIClientSocketFactory clientFactory;
	private RMIServerSocketFactory serverFactory;
	private int port;
	
	protected ConnectionServerImpl(int port, RMIClientSocketFactory clientFactory, RMIServerSocketFactory serverFactory) throws RemoteException {
		super (port, clientFactory, serverFactory );
		this.clientFactory = clientFactory;
		this.serverFactory = serverFactory;
		this.port= port;
	}
	
	@Override
	public BarkerServerImpl logon(String username, String passwd) throws RemoteException, LoginException {
		LoginContext lc = new LoginContext("BarkerServer", new security.module.RemoteCallbackHandler(username, passwd));
		try{
			lc.login();
		}
		catch (LoginException e){
			System.out.println("Recu "+ username + " et " + passwd + " mais, après vérif, ils sont incorrects");
			throw e;
		}
		return new BarkerServerImpl(lc.getSubject(), port, clientFactory, serverFactory);
	}

}
