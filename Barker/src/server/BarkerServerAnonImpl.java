package server;

import java.rmi.RemoteException;
import java.rmi.server.RMIClientSocketFactory;
import java.rmi.server.RMIServerSocketFactory;
import java.util.List;

import barker.Bark;
import barker.BarkerServerAnon;

public class BarkerServerAnonImpl extends BarkerServer implements BarkerServerAnon {
    private static final long serialVersionUID = 1L;
    
    protected boolean loggedout = false;

    protected BarkerServerAnonImpl(int port, RMIClientSocketFactory csf,
            RMIServerSocketFactory ssf) throws RemoteException {
        super(port, csf, ssf);
    }

    @Override
    public List<Bark> lastBarks(int nb) throws RemoteException {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public List<Bark> lastBarksFrom(int nb, String username)
            throws RemoteException {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public List<Bark> lastBarksAbout(int nb, String topic)
            throws RemoteException {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public List<String> trendingTopics() throws RemoteException {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public void logout() throws RemoteException {
        if (loggedout)
            throw new RemoteException("User already Logged out");
        loggedout = true;
    }

}
