/**
 * 
 */
package main;

import data.ClackData;

/**
 * @author Jonathan Nordby
 *
 */
public class ClackServer {

	private int port;
	private boolean closeConnection;
	ClackData dataToReceiveFromClient;
	ClackData dataToSendToClient;
	private final static int DEFAULT_PORT = 7000;
	
	/**
	 * Creates an instance of the Clack Server with the specified port
	 * @param port the number of 
	 */
	ClackServer(int port) {
		this.port = port;
		dataToReceiveFromClient = null;
		dataToSendToClient = null;
	}
	/**
	 * 
	 */
	ClackServer() {
		this(DEFAULT_PORT);
	}
	
	void start() {
		//TODO Implement
	}
	
	void receiveData() {
		//TODO Implement Function
	}
	
	int getPort() {
		return port;
	}
	
	@Override
	public final int hashCode() {
		int result = 17;
		result = 37 * result + Integer.hashCode(port);
		result = 37 * result + Boolean.hashCode(closeConnection);
		result = 37 * result + dataToReceiveFromClient.hashCode();
		result = 37 * result + dataToSendToClient.hashCode();
		return result;
	}
	
	@Override
	public boolean equals(Object other) {
		if (!(other instanceof ClackServer)) {
			return false;
		}
		return hashCode() == other.hashCode();
	}
	
	@Override
	public String toString() {
		return "Server: " + port + " " + closeConnection + " " + dataToReceiveFromClient.toString() + " " + dataToSendToClient.toString();
	}
}
