package server;

import java.rmi.RemoteException;
import java.rmi.server.RMIClientSocketFactory;
import java.rmi.server.RMIServerSocketFactory;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.ListIterator;

import javax.security.auth.login.LoginContext;
import javax.security.auth.login.LoginException;

import barker.Bark;
import barker.BarkerServerAuth;
import barker.Dog;

public class BarkerServerAuthImpl extends BarkerServerAnonImpl implements BarkerServerAuth {
	private static final long serialVersionUID = -7666130486088047448L;
	
	private LoginContext lcontext;
	private Dog user;
	
	protected BarkerServerAuthImpl(LoginContext lcontext, int port, RMIClientSocketFactory clientFactory, RMIServerSocketFactory serverFactory) throws RemoteException {
		super(port,clientFactory,serverFactory);
		if(lcontext != null) {
		    this.lcontext = lcontext;
		    user = users.get(lcontext.getSubject().getPrincipals().iterator().next().getName());
		    System.out.println("on a recu : "+ user.getUsername());
		} else {
		    this.lcontext = null;
		    user = null;
		    System.out.println("Connexion anonyme");
		}
	}
	
	@Override
    public List<Bark> myLastBarks(int nb) throws RemoteException {
	    if (nb < 0)
            throw new IllegalArgumentException ("nb < 0");
        List<Bark> retlist = new ArrayList<Bark>();
        if (nb == 0)
            return retlist;
        for (ListIterator<Bark> i = barks.listIterator(barks.size()); i.hasPrevious(); )
        {
            Bark b = i.previous();
            if (b.getRelated().contains(user.getUsername().toLowerCase()) || !Collections.disjoint(b.getTopics(), user.getSniffed()))
            {
                retlist.add(b);
                if (retlist.size() == nb)
                    break;
            }
        }
        return retlist;
    }

    @Override
    public void bark(String message) throws RemoteException {
        if (loggedout)
            throw new RemoteException("User Logged out");
        if (lcontext == null)
            throw new RemoteException("Anonymous user");
        sendBark(new SimpleBark(user.getUsername(), new Date(), message));
    }
    
    @Override
    public void logout() throws RemoteException {
        if (loggedout)
            throw new RemoteException("User already Logged out");
        if(lcontext != null) {
            try {
                lcontext.logout();
            } catch (LoginException e) {
            }
        }
        loggedout = true;
    }

    @Override
    public void rebark(Bark bark) throws RemoteException {
        sendBark(new ReBark(user.getUsername(), new Date(), bark));
    }

    @Override
    public void sniff(String username) throws RemoteException {
        if (loggedout)
            throw new RemoteException("User Logged out");
        if (lcontext == null)
            throw new RemoteException("Anonymous user");
        user.sniff(username);
    }

}
