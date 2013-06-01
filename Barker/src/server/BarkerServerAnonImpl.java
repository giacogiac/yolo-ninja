package server;

import java.rmi.RemoteException;
import java.rmi.server.RMIClientSocketFactory;
import java.rmi.server.RMIServerSocketFactory;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;
import java.util.Set;
import java.util.Map.Entry;

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
        return new ArrayList<Bark>(retlist);
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
        updateTT();
        HashMap<String, Integer> tt = new HashMap<String, Integer>();
        for (ListIterator<Entry<Date, Set<String>>> i = trendings.listIterator(); i.hasNext(); )
        {
            Entry<Date, Set<String>> e = i.next();
            for(Iterator<String> j = e.getValue().iterator(); j.hasNext(); ) {
                String s = j.next();
                if(tt.containsKey(s)) {
                    tt.put(s, new Integer(tt.get(s).intValue() + 1));
                } else {
                    tt.put(s, 1);
                }
            }
        }
        List<String> ttsorted = new ArrayList<String>(tt.keySet());
        Collections.sort(ttsorted, new TrendingsComparator(tt));
        return new ArrayList<String>(ttsorted.subList(0, 10));
        
    }

    @Override
    public void logout() throws RemoteException {
        if (loggedout)
            throw new RemoteException("User already Logged out");
        loggedout = true;
    }

}
