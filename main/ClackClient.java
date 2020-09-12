package main;

import data.ClackData;

public class ClackClient {
	
	private String userName, hostName;
	private int port;
	private boolean closeConnection;
	private ClackData dataToSendToServer, dataToReceiveFromServer;
	private final static int DEFAULT_PORT = 7000;
	
	ClackClient(String userName, String hostName) {
		this(userName, hostName, DEFAULT_PORT);
	}

	ClackClient(String userName) {
		this(userName, "localhost", DEFAULT_PORT);
	}
	
	ClackClient() {
		this("anonymous", "localhost", DEFAULT_PORT);
	}
	
	ClackClient(String userName, String hostName, int port) {
		
		this.userName = userName;
		this.hostName = hostName;
		this.port = port;
	}
	
	void start() {
		//TODO Implement Later
	}
	
	void readClientData() {
		//TODO Implement Later
	}
	
	void sendData() {
		//TODO Implement Later
	}
	
	void receiveData() {
		//TODO Implement Later
	}
	
	void printData() {
		//TODO Implement Later
	}
	
	String getUserName() {
		return userName;
	}
	
	String getHostName() {
		return hostName;
	}
	
	int getPort() {
		return port;
	}
	
	@Override
	public final int hashCode() {
		int result = 17;
		result = 37 * result + port;
		result = 37 * result + Boolean.hashCode(closeConnection);
		result = 37 * result + userName.hashCode();
		result = 37 * result + hostName.hashCode();
		result = 37 * result + dataToSendToServer.hashCode();
		result = 37 * result + dataToReceiveFromServer.hashCode();
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
