package main;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;

import data.ClackData;

/**
 * The Server version of Clack
 * @author Jonathan Nordby <br> Stephen Miner
 */
public class ClackServer {

	private int port;
	private boolean closeConnection;
	ClackData dataToReceiveFromClient;
	ClackData dataToSendToClient;
	ArrayList<ServerSideClientIO> serverSideIOList;
	ArrayList<String> userNameList;
	private final static int DEFAULT_PORT = 7000;

	/**
	 * Creates an instance of the Clack Server with the specified port
	 * @param port the number of the port
	 */
	public ClackServer(int port) {
		if (port < 1024) {
			throw new IllegalArgumentException();
		}
		this.port = port;
		dataToReceiveFromClient = null;
		dataToSendToClient = null;
		serverSideIOList = new ArrayList<ServerSideClientIO>();
		userNameList = new ArrayList<String>();
//		inFromClient = null;
//		outToClient = null;
	}
	/**
	 * creates a Clack Server with a default port of 7000
	 */
	public ClackServer() {
		this(DEFAULT_PORT);
	}

	/**
	 * starts up the server
	 */
	public void start() {
		try {
			ServerSocket server = new ServerSocket(port);
			closeConnection = false;
			while (!closeConnection) {
				Socket client = server.accept();
				ServerSideClientIO IOThing = new ServerSideClientIO(this, client);
				serverSideIOList.add(IOThing);
				Thread serverThread = new Thread(IOThing);
				serverThread.start();


			}
			server.close();
		} catch (IOException e) {
			System.err.println("Error opening a connection");
			e.printStackTrace();
		}
	}

	/**
	 * Broadcasts the specified data to all connected clients
	 * @param objectToBroadcastToClients the data to be broadcasted
	 */
	public synchronized void broadcast(ClackData objectToBroadcastToClients) {
		for (ServerSideClientIO client : serverSideIOList) {
			System.out.println("Sending to a client!");

			client.setDataToSendToClient(objectToBroadcastToClients);
			client.sendData();
		}
	}


	/**
	 * Removes a host from the connection.
	 * @param serverSideClientToRemove the the IO Module that corresponds to the client to remove
	 * @param userName the username that matches the client to be removed
	 */
	public synchronized void remove(ServerSideClientIO serverSideClientToRemove, String userName) {
		serverSideIOList.remove(serverSideClientToRemove);
		userNameList.remove(userName);
		System.out.println(userName + " Disconnected");
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

	/**
	 * main method for server, takes arguments from the command line and initializes the server accordingly.
	 * @param args
	 */
	public static void main(String args[])
	{
		try {
			ClackServer server;
			switch (args.length) {
				case 0:
					server = new ClackServer();
					server.start();
					break;
				case 1:
					server = new ClackServer(Integer.parseInt(args[0]));
					server.start();
					break;
				default:
					System.err.println("Improper arguments");
			}
		}catch (NumberFormatException nfe) {
			System.err.println("NumberFormatException invalid port number format");
		}
	}
}