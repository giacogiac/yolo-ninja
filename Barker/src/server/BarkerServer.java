package server;

import java.rmi.RemoteException;
import java.rmi.server.RMIClientSocketFactory;
import java.rmi.server.RMIServerSocketFactory;
import java.rmi.server.UnicastRemoteObject;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.security.auth.login.LoginException;

import barker.Bark;
import barker.Dog;

import security.module.BarkerServerLoginModule;

public class BarkerServer extends UnicastRemoteObject {
    private static final long serialVersionUID = 1L;
    
    protected static Map<String, Dog> users = new HashMap<String, Dog>();
    protected static List<Bark> barks = new ArrayList<Bark>();
    
    protected BarkerServer(int port, RMIClientSocketFactory csf,
            RMIServerSocketFactory ssf) throws RemoteException {
        super(port, csf, ssf);
    }

    public static void addUser(String username, String password) throws LoginException {
        BarkerServerLoginModule.addUser(username, password);
        users.put(username.toLowerCase(), new DogImpl(username));
    }
    
}
