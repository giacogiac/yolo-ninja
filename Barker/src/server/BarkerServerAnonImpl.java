package server;

import java.rmi.RemoteException;
import java.rmi.server.RMIClientSocketFactory;
import java.rmi.server.RMIServerSocketFactory;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.ListIterator;

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
        if (nb < 0)
            throw new IllegalArgumentException ("nb < 0");
        int end = barks.size();
        int start = end - nb;
        if (start < 0)
            start = 0;
        List<Bark> retlist = barks.subList(start, end);
        Collections.reverse(retlist);
        return new ArrayList(retlist);
    }

    @Override
    public List<Bark> lastBarksFrom(int nb, String username)
            throws RemoteException {
        if (nb < 0)
            throw new IllegalArgumentException ("nb < 0");
        List<Bark> retlist = new ArrayList<Bark>();
        if (nb == 0)
            return retlist;
        for (ListIterator<Bark> i = barks.listIterator(barks.size()); i.hasPrevious(); )
        {
            Bark b = i.previous();
            if (b.getUsername().toLowerCase().equals(username.toLowerCase()))
            {
                retlist.add(b);
                if (retlist.size() == nb)
                    break;
            }
        }
        return retlist;
    }

    @Override
    public List<Bark> lastBarksAbout(int nb, String topic)
            throws RemoteException {
        if (nb < 0)
            throw new IllegalArgumentException ("nb < 0");
        List<Bark> retlist = new ArrayList<Bark>();
        if (nb == 0)
            return retlist;
        for (ListIterator<Bark> i = barks.listIterator(barks.size()); i.hasPrevious(); )
        {
            Bark b = i.previous();
            if (b.getTopics().contains(topic.toLowerCase()))
            {
                retlist.add(b);
                if (retlist.size() == nb)
                    break;
            }
        }
        return retlist;
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
