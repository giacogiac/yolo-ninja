package barker;

import java.rmi.Remote;
import java.rmi.RemoteException;

import javax.security.auth.login.LoginException;

public interface ConnectionServer extends Remote {
	public BarkerServerAuth logon(String username, String password) throws RemoteException, LoginException;
	
	public BarkerServerAnon anon() throws RemoteException;

    public void addUser(String username, String password) throws LoginException, RemoteException;
}
