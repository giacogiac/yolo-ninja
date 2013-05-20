package server;

import java.rmi.RemoteException;
import java.rmi.server.RMIClientSocketFactory;
import java.rmi.server.RMIServerSocketFactory;
import java.rmi.server.UnicastRemoteObject;

import javax.security.auth.login.LoginContext;
import javax.security.auth.login.LoginException;

import security.module.RemoteCallbackHandler;

import barker.BarkerServerAnon;
import barker.BarkerServerAuth;
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
	public BarkerServerAuth logon(String username, String passwd) throws RemoteException, LoginException {
		LoginContext lc = new LoginContext("BarkerServer", new RemoteCallbackHandler(username, passwd));
		lc.login();
		return new BarkerServerAuthImpl(lc, port, clientFactory, serverFactory);
	}
	
	@Override
	public BarkerServerAnon anon() throws RemoteException {
		return new BarkerServerAnonImpl(port, clientFactory, serverFactory);
	}
	
	@Override
	public void addUser(String username, String password) throws LoginException, RemoteException {
	    BarkerServer.addUser(username, password);
    }

}
