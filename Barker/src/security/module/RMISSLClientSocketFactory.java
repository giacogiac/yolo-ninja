package security.module;

import java.io.IOException;
import java.io.Serializable;
import java.net.Socket;
import java.rmi.server.RMIClientSocketFactory;

import javax.rmi.ssl.SslRMIClientSocketFactory;

public class RMISSLClientSocketFactory implements RMIClientSocketFactory,
		Serializable {
	private static final long serialVersionUID = -4430804928569691409L;

	@Override
	public Socket createSocket(String host, int port) throws IOException {
		SslRMIClientSocketFactory factory = new SslRMIClientSocketFactory();
		Socket socket = factory.createSocket(host,port);
		return socket;
	}

}
