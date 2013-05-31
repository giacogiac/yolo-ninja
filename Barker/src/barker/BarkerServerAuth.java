package barker;

import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.List;

public interface BarkerServerAuth extends BarkerServerAnon {
	public void bark(String message) throws RemoteException;
	
	public void rebark() throws RemoteException;
	
	public void sniff(String username) throws RemoteException;

    public List<Bark> myLastBarks(int nb) throws RemoteException;
}
