package main;

public class ClackClient {
	
	private String userName, hostName;
	private int port;
	private boolean closeConnection;
	private ClackData dataToSendToServer, dataToReceiveFromServer;
	private final int DEFAULT_PORT = 7000;
	
	ClackClient(String userName, String hostName) {
		ClackClient(userName, hostName, DEFAULT_PORT);
	}

	ClackClient(String userName) {
		ClackClient(userName, "localhost", DEFAULT_PORT);
	}
	
	ClackClient() {
		ClackClient("anonymous", "localhost", DEFAULT_PORT);
	}
	
	public ClackClient(String userName, String hostName, int port) {
		
		this.userName = userName;
		this.hostName = hostName;
		this.port = port;
	}
	
	void start() {
		
	}
	
	void readClientData() {
		
	}
	
	void sendData() {
		
	}
	
	void receiveData() {
		
	}
	
	void printData() {
		
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
	
	public final int hashCode() {
		
	}
	
	public boolean equals(ClackServer other) {
		
	}
	
	public String toString() {
		
	}
}
