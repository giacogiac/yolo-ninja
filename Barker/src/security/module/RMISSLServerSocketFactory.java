package security.module;

import java.io.IOException;
import java.io.Serializable;
import java.net.ServerSocket;
import java.rmi.server.RMIServerSocketFactory;

import javax.rmi.ssl.SslRMIServerSocketFactory;

public class RMISSLServerSocketFactory implements RMIServerSocketFactory,
		Serializable {
	private static final long serialVersionUID = 8334732573957400642L;

	@Override
	public ServerSocket createServerSocket(int port) throws IOException {
		SslRMIServerSocketFactory factory= new SslRMIServerSocketFactory();
		ServerSocket socket = factory.createServerSocket(port);
		return socket;
	}

}
