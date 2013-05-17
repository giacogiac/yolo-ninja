package barker;

import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.List;

public interface Dog extends Remote {
	
	void sendBark (Bark b) throws RemoteException;
	
	List<Bark> getBarks () throws RemoteException;
	
	List<String> getSniffed () throws RemoteException;
	
	void sniff (String doggy) throws RemoteException;
}
