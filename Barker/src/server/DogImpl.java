package server;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;

import barker.Dog;

public class DogImpl implements Dog {
	private static final long serialVersionUID = -5065478789716075630L;
	
	private String username;
	
	protected DogImpl(String username) {
		super();
		this.username = username;
	}

	@Override
	public String getUsername() {
		return username;
	}

}
