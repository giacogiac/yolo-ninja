package barker;

import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.List;

public interface BarkerServerAnon extends Remote {
    public List<Bark> lastBarks(int nb) throws RemoteException;
    
    public List<Bark> lastBarksFrom(int nb, String username) throws RemoteException;
    
    public List<Bark> lastBarksAbout(int nb, String topic) throws RemoteException;
    
    public List<String> trendingTopics() throws RemoteException;
    
    void logout() throws RemoteException;
}
