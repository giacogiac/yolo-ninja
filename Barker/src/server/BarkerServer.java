package server;

import java.rmi.RemoteException;
import java.rmi.server.RMIClientSocketFactory;
import java.rmi.server.RMIServerSocketFactory;
import java.rmi.server.UnicastRemoteObject;
import java.util.AbstractMap.SimpleEntry;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.ListIterator;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import javax.security.auth.login.LoginException;

import barker.Bark;
import barker.Dog;

import security.module.BarkerServerLoginModule;

public class BarkerServer extends UnicastRemoteObject {
    private static final long serialVersionUID = 1L;
    
    private static final int interval = 3600000; // 1heure
    
    protected static Map<String, Dog> users = new HashMap<String, Dog>();
    protected static List<Bark> barks = new ArrayList<Bark>();
    protected static List<Entry<Date, Set<String>>> trendings = new ArrayList<Entry<Date, Set<String>>>();
    
    protected BarkerServer(int port, RMIClientSocketFactory csf,
            RMIServerSocketFactory ssf) throws RemoteException {
        super(port, csf, ssf);
    }

    protected static void addUser(String username, String password) throws LoginException {
        BarkerServerLoginModule.addUser(username, password);
        users.put(username.toLowerCase(), new DogImpl(username));
    }
    
    protected static void deleteUser(String username) throws LoginException {
        BarkerServerLoginModule.deleteUser(username);
        users.remove(username);
        barks.removeAll(allBarksFrom(username));
    }
    
    protected static void sendBark(Bark bark) {
        trendings.add(new SimpleEntry<Date, Set<String>>(new Date(), bark.getTopics()));
        barks.add(bark);
    }
    
    private static List<Bark> allBarksFrom(String username) {
        List<Bark> retlist = new ArrayList<Bark>();
        for (ListIterator<Bark> i = barks.listIterator(barks.size()); i.hasPrevious(); )
        {
            Bark b = i.previous();
            if (b.getUsername().toLowerCase().equals(username.toLowerCase()))
            {
                retlist.add(b);
            }
        }
        return retlist;
    }
    
    protected static void updateTT() {
        Date when = new Date(new Date().getTime() - interval);
        while(0 < trendings.size())
        {
            if (trendings.get(0).getKey().after(when))
                break;
            trendings.remove(0);
        }
    }
    
}
