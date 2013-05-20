package barker;

import java.rmi.Remote;
import java.rmi.RemoteException;

public interface BarkerServerAuth extends BarkerServerAnon {
	public void bark(String message) throws RemoteException;
	
	public void rebark() throws RemoteException;
	
	public void sniff(String username) throws RemoteException;
}
