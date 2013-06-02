package server;

import java.util.HashSet;
import java.util.Set;

public class Dog {
	
	private String username;
	private Set<String> sniffed;
	
	protected Dog(String username) {
		super();
		this.username = username;
		this.sniffed = new HashSet<String>();
	}

	public String getUsername() {
		return username;
	}
	
    public Set<String> getSniffed() {
        return sniffed;
    }
	
	public void sniff(String username) {
	    sniffed.add(username.toLowerCase());
	}

}
