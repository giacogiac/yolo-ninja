package security.module;

import java.util.*;

import javax.security.auth.*;
import javax.security.auth.callback.*;
import javax.security.auth.login.*;
import javax.security.auth.spi.*;

public class BarkerServerLoginModule implements LoginModule {
	private static HashMap<String, String> loginPw = new HashMap<String, String>();
	
    // initial state
    private Subject subject;
    private CallbackHandler callbackHandler;
    private Map sharedState;
    private Map options;

    // configurable option
    private boolean debug = false;

    // the authentication status
    private boolean succeeded = false;
    private boolean commitSucceeded = false;

    // username and password
    private String username;
    private String password;

    // testUser's SamplePrincipal
    //To Improve
    private BarkerPrincipal userPrincipal;

    @Override
	public void initialize(Subject subject, CallbackHandler callbackHandler, Map sharedState, Map options) {
		this.subject = subject;
		this.callbackHandler = callbackHandler;
		this.sharedState = sharedState;
		this.options = options;

		// initialize any configured options
		debug = "true".equalsIgnoreCase((String) options.get("debug"));
	}
    
    @Override
	public boolean login() throws LoginException {
		System.out.println("Le LoginModule est en train de vérifier un Login");
		
		// prompt for a user name and password
		if (callbackHandler == null)
			throw new LoginException("Error: no CallbackHandler available "
					+ "to garner authentication information from the user");

		Callback[] callbacks = new Callback[2];
		callbacks[0] = new NameCallback("user name: ");
		callbacks[1] = new PasswordCallback("password: ", false);
		
		try {
			callbackHandler.handle(callbacks);
			username = ((NameCallback) callbacks[0]).getName().toLowerCase();
			char[] tmpPassword = ((PasswordCallback) callbacks[1]).getPassword();
			if (tmpPassword == null) {
				// treat a NULL password as an empty password
				tmpPassword = new char[0];
			}
			password = new String(tmpPassword);
			((PasswordCallback) callbacks[1]).clearPassword();
		} catch (java.io.IOException ioe) {
			throw new LoginException(ioe.toString());
		} catch (UnsupportedCallbackException uce) {
			throw new LoginException("Error: " + uce.getCallback().toString()
					+ " not available to garner authentication information "
					+ "from the user");
		}

		// print debugging information
		if (debug) {
			System.out.println("\t\t[SampleLoginModule] "
					+ "user entered user name: " + username);
			System.out.print("\t\t[SampleLoginModule] "
					+ "user entered password: " + password);
			System.out.println();
		}

		// verify the username/password
		boolean usernameCorrect = false;
		boolean passwordCorrect = false;
		if (loginPw.containsKey(username))
			usernameCorrect = true;
		if (usernameCorrect && password.equals(loginPw.get(username))) {
			// authentication succeeded!!!
			passwordCorrect = true;
			if (debug)
				System.out.println("\t\t[SimpleMonServeurLoginModule] "
						+ "authentication succeeded");
			succeeded = true;
			return true;
		} else {
			// authentication failed -- clean out state
			if (debug)
				System.out.println("\t\t[SimpleMonServeurLoginModule] "
						+ "authentication failed");
			succeeded = false;
			username = null;
			password = null;
			if (!usernameCorrect) {
				throw new FailedLoginException("User Name Incorrect");
			} else {
				throw new FailedLoginException("Password Incorrect");
			}
		}
	}
    
    @Override
	public boolean commit() throws LoginException {
		if (succeeded == false) {
			return false;
		} else {
			// add a Principal (authenticated identity)
			// to the Subject

			// assume the user we authenticated is the SamplePrincipal
			userPrincipal = new BarkerPrincipal(username);
			if (!subject.getPrincipals().contains(userPrincipal))
				subject.getPrincipals().add(userPrincipal);

			if (debug) {
				System.out.println("\t\t[SampleLoginModule] "
						+ "added SamplePrincipal to Subject with user "
						+ userPrincipal.getName());
			}

			// in any case, clean out state
			username = null;
			password = null;

			commitSucceeded = true;
			return true;
		}
	}
	
	@Override
	public boolean abort() throws LoginException {
		if (succeeded == false) {
			return false;
		} else if (succeeded == true && commitSucceeded == false) {
			// login succeeded but overall authentication failed
			succeeded = false;
			username = null;
			if (password != null) {
				password = null;
			}
			userPrincipal = null;
		} else {
			// overall authentication succeeded and commit succeeded,
			// but someone else's commit failed
			logout();
		}
		return true;
	}
	
	@Override
	public boolean logout() throws LoginException {
		subject.getPrincipals().remove(userPrincipal);
		succeeded = false;
		succeeded = commitSucceeded;
		username = null;
		if (password != null) {
			password = null;
		}
		userPrincipal = null;
		return true;
	}
	
	public static void addUser(String username, String password) throws LoginException {
		if (loginPw.containsKey(username.toLowerCase())) {
			throw new AccountException("Username already exist");
		}
		loginPw.put(username.toLowerCase(), password);
	}
	
	public static void modifyUser(String username, String password) throws LoginException {
		if (!loginPw.containsKey(username.toLowerCase())) {
			throw new AccountException("Username doesn't exist");
		}
		loginPw.put(username.toLowerCase(), password);
	}
	
	public static void deleteUser(String username) throws LoginException {
		if (!loginPw.containsKey(username.toLowerCase())) {
			throw new AccountException("Username doesn't exist");
		}
		loginPw.remove(username.toLowerCase());
	}
}
