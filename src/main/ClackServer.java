package main;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.ServerSocket;
import java.net.Socket;

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
	ObjectInputStream inFromClient;
	ObjectOutputStream outToClient;
	private final static int DEFAULT_PORT = 7000;
	
	/**
	 * Creates an instance of the Clack Server with the specified port
	 * @param port the number of the port
	 */
	public ClackServer(int port) {
		this.port = port;
		dataToReceiveFromClient = null;
		dataToSendToClient = null;
		inFromClient = null;
		outToClient = null;
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
		try {
			ServerSocket server = new ServerSocket(port);
			Socket client = server.accept();
			outToClient = new ObjectOutputStream(client.getOutputStream());
			inFromClient = new ObjectInputStream(client.getInputStream());
			closeConnection = false;
			do {
				receiveData();
				sendData();
			}while(!closeConnection);
			
			client.close();
			server.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public void sendData() {
		try {
			outToClient.writeObject(dataToSendToClient);
		} catch (IOException e) {
			System.err.println("Error in I/O");
			e.printStackTrace();
		}
	}
	
	/**
	 * TODO
	 */
	public void receiveData() {
		try {
			dataToReceiveFromClient = (ClackData) inFromClient.readObject();
		} catch (ClassNotFoundException e) {
			System.err.println("ClackData cannot be found. -THIS SHOULD NEVER HAPPEN");
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
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
		if (dataToSendToClient == null && otherClackServer.dataToSendToClient == null) {
			if (dataToReceiveFromClient == null && otherClackServer.dataToReceiveFromClient == null) {
				return port == otherClackServer.port &&
					   closeConnection == otherClackServer.closeConnection;
			} else {
				return port == otherClackServer.port &&
					   closeConnection == otherClackServer.closeConnection &&
					   dataToReceiveFromClient.equals(otherClackServer.dataToReceiveFromClient);				
			}
		} else if (dataToSendToClient != null && otherClackServer.dataToSendToClient != null) {
			if (dataToReceiveFromClient != null && otherClackServer.dataToReceiveFromClient != null) {
				return port == otherClackServer.port &&
					   closeConnection == otherClackServer.closeConnection &&
					   dataToSendToClient.equals(otherClackServer.dataToSendToClient) && 
					   dataToReceiveFromClient.equals(otherClackServer.dataToReceiveFromClient);
			} else {
				if (dataToReceiveFromClient == null && otherClackServer.dataToReceiveFromClient == null) {
					return port == otherClackServer.port &&
						   closeConnection == otherClackServer.closeConnection &&
					       dataToSendToClient.equals(otherClackServer.dataToSendToClient);
				}
				return false;
			}
		} else {
			return false;
		}	
	}
	
	@Override
	public String toString() {
		return "Server: " + port + " " + closeConnection + " " + (dataToReceiveFromClient == null ? "null" : dataToReceiveFromClient.toString()) + " " + (dataToSendToClient == null ? "null" : dataToSendToClient.toString());

	}
}
