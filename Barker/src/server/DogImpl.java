package server;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.ArrayList;
import java.util.List;

import barker.Bark;
import barker.Dog;

public class DogImpl extends UnicastRemoteObject implements Dog {
	private static final long serialVersionUID = -5065478789716075630L;
	
	private List<String> sniffed;
	private String login;
	private BarkerServerImpl barkerServer;
	
	protected DogImpl(String login, BarkerServerImpl server) throws RemoteException {
		super();
		this.login = login;
		this.barkerServer = server;
		
	}
	
	@Override
	public void sendBark(Bark b) throws RemoteException {
		

	}

	public void setLogin(String login) {
		this.login = login;
	}

	public String getLogin() {
		return login;
	}

	@Override
	public List<Bark> getBarks() throws RemoteException {
		List<Bark> barks = new ArrayList<Bark>();
		
		return barks;
	}

	@Override
	public List<String> getSniffed() throws RemoteException {
		return sniffed;
	}

	@Override
	public void sniff(String doggy) throws RemoteException {
		sniffed.add(doggy);
		
	}

}
