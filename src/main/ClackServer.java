package main;

import data.ClackData;

/**
 * The Server version of Clack
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
	 * @param port the number of the port
	 */
	public ClackServer(int port) {
		this.port = port;
		dataToReceiveFromClient = null;
		dataToSendToClient = null;
	}
	/**
	 * creates a Clack Server with a default port of 7000
	 */
	public ClackServer() {
		this(DEFAULT_PORT);
	}
	
	/**
	 * TODO
	 */
	public void start() {
		//TODO Implement
	}
	
	/**
	 * TODO
	 */
	public void receiveData() {
		//TODO Implement Function
	}
	
	/**
	 * @return the port number as an int
	 */
	public int getPort() {
		return port;
	}
	
	/**
	 * @return a hashcode of the object that follows the general contract
	 */
	@Override
	public final int hashCode() {
		int result = 17;
		result = 37 * result + Integer.hashCode(port);
		result = 37 * result + Boolean.hashCode(closeConnection);
		result = 37 * result + (dataToReceiveFromClient == null ? 0 : dataToReceiveFromClient.hashCode());
		result = 37 * result + (dataToSendToClient == null ? 0 : dataToSendToClient.hashCode());
		return result;
	}
	
	/**
	 * @return true or false depending on if the objects are equal
	 */
	@Override
	public boolean equals(Object other) {
		if (!(other instanceof ClackServer)) {
			return false;
		}
		ClackServer otherClackServer = (ClackServer) other;
		return  port == otherClackServer.port && 
				closeConnection == otherClackServer.closeConnection &&
				dataToReceiveFromClient == otherClackServer.dataToReceiveFromClient &&
				dataToSendToClient == otherClackServer.dataToSendToClient;
	}
	
	@Override
	public String toString() {
		return "Server: " + port + " " + closeConnection + " " + dataToReceiveFromClient.toString() + " " + dataToSendToClient.toString();
	}
}
