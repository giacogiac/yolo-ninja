package server;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.HashSet;
import java.util.Set;

import barker.Dog;

public class DogImpl implements Dog {
	private static final long serialVersionUID = -5065478789716075630L;
	
	private String username;
	private Set<String> sniffed;
	
	protected DogImpl(String username) {
		super();
		this.username = username;
		this.sniffed = new HashSet<String>();
	}

	@Override
	public String getUsername() {
		return username;
	}
	
	@Override
    public Set<String> getSniffed() {
        return sniffed;
    }

}
