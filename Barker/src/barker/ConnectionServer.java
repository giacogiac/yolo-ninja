package barker;

import java.rmi.Remote;
import java.rmi.RemoteException;

import javax.security.auth.login.LoginException;

public interface ConnectionServer extends Remote {
	public BarkerServer logon(String username, String password) throws RemoteException, LoginException;
}
