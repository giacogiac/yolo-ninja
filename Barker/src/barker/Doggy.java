package barker;

import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.List;

public interface Doggy extends Remote {
	
	void sendBark (Bark b) throws RemoteException;
	
	List<Bark> getBarks () throws RemoteException;
	
	List<String> getSniffers () throws RemoteException;
	
	List<String> getSniffeds () throws RemoteException;
	
	void sniff (String doggy) throws RemoteException;
}
