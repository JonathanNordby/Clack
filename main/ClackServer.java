/**
 * 
 */
package main;

/**
 * @author Jonathan Nordby
 *
 */
public class ClackServer {

	private int port;
	private boolean closeConnection;
	ClackData dataToRecieveFromClient;
	ClackData dataToSendToClient;
	private final int DEFAULT_PORT = 7000;
	
	/**
	 * 
	 * @param port
	 */
	ClackServer(int port) {
		this.port = port;
		dataToRecieveFromClient = null;
		dataToSendToClient = null;
	}
	/**
	 * 
	 */
	ClackServer() {
		ClackServer(DEFAULT_PORT);
	}
	
	void start() {
		//TODO Implement
	}
	
	void recieveData() {
		//TODO Implement Function
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
