package barker;

import java.rmi.Remote;
import java.rmi.RemoteException;

public interface BarkerServer extends Remote {
	public int setVal(final int v) throws RemoteException;
}
