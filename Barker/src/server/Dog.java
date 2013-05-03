package server;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.ArrayList;
import java.util.List;

import barker.Bark;
import barker.Doggy;

public class Dog extends UnicastRemoteObject implements Doggy {

	private List<String> sniffed;
	private String login;
	private Server barkerServer;
	
	protected Dog(String login, Server server) throws RemoteException {
		super();
		this.login = login;
		this.barkerServer = server;
		barkerServer.getSniffers().put(login, new ArrayList<String>());
	}
	
	@Override
	public void sendBark(Bark b) throws RemoteException {
		barkerServer.getBarks().add(b);

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
		for (Bark b : barkerServer.getBarks()) {
			if (b.getSender().equals(this.getLogin())) {
				barks.add(b);
			}
		}
		return barks;
	}

	@Override
	public List<String> getSniffed() throws RemoteException {
		return sniffed;
	}

	@Override
	public void sniff(String doggy) throws RemoteException {
		sniffed.add(doggy);
		barkerServer.getSniffers(doggy).add(this.getLogin());
	}

}
