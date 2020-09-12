package main;

import data.ClackData;

/**
 * The Client version of the Clack program
 * @author Jonathan Nordby
 *
 */

public class ClackClient {
	
	private String userName, hostName;
	private int port;
	private boolean closeConnection;
	private ClackData dataToSendToServer, dataToReceiveFromServer;
	private final static int DEFAULT_PORT = 7000;
	
	public ClackClient(String userName, String hostName) {
		this(userName, hostName, DEFAULT_PORT);
	}

	public ClackClient(String userName) {
		this(userName, "localhost", DEFAULT_PORT);
	}
	
	public ClackClient() {
		this("anonymous", "localhost", DEFAULT_PORT);
	}
	
	public ClackClient(String userName, String hostName, int port) {
		
		this.userName = userName;
		this.hostName = hostName;
		this.port = port;
	}
	
	public void start() {
		//TODO Implement Later
	}
	
	public void readClientData() {
		//TODO Implement Later
	}
	
	public void sendData() {
		//TODO Implement Later
	}
	
	public void receiveData() {
		//TODO Implement Later
	}
	
	public void printData() {
		//TODO Implement Later
	}
	
	public String getUserName() {
		return userName;
	}
	
	public String getHostName() {
		return hostName;
	}
	
	public int getPort() {
		return port;
	}
	
	@Override
	public final int hashCode() {
		int result = 17;
		result = 37 * result + port;
		result = 37 * result + Boolean.hashCode(closeConnection);
		result = 37 * result + userName.hashCode();
		result = 37 * result + hostName.hashCode();
		result = 37 * result + (dataToSendToServer == null ? 0 : dataToSendToServer.hashCode());
		result = 37 * result + (dataToReceiveFromServer == null ? 0 : dataToReceiveFromServer.hashCode());
		return result;
	}
	
	@Override
	public boolean equals(Object other) {
		if (!(other instanceof ClackClient)) {
			return false;
		}
		ClackClient otherClient = (ClackClient) other;
		
		return userName == otherClient.userName &&
			   hostName == otherClient.hostName &&
			   port == otherClient.port &&
			   closeConnection == otherClient.closeConnection &&
			   dataToSendToServer.equals(otherClient.dataToSendToServer) && 
			   dataToReceiveFromServer.equals(otherClient.dataToReceiveFromServer);
	}
	
	@Override
	public String toString() {
		return "Client: " +  + port + " " + userName + " " + hostName + " " + closeConnection + " " + dataToReceiveFromServer.toString() + " " + dataToSendToServer.toString();
	}
}
