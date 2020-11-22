package src.main;

import java.io.EOFException;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;

import src.data.ClackData;
import src.data.MessageClackData;

/**
 * an I/O Module for interacting with a client and a server
 * @author Jonathan Nordby <br> Stephen Miner
 *
 */
public class ServerSideClientIO implements Runnable {

	private boolean closeConnection;
	private ClackData dataToReceiveFromClient;
	private ClackData dataToSendToClient;
	private ObjectInputStream inFromClient;
	private ObjectOutputStream outToClient;
	private ClackServer server;
	private Socket clientSocket;

	/**
	 * Creates a ServerSideClientIO
	 * @param server the server to bind to
	 * @param clientSocket the socket where the client to be bound to is connected
	 */
	public ServerSideClientIO(ClackServer server, Socket clientSocket) {
		this.server = server;
		this.clientSocket = clientSocket;
		closeConnection = false;
		dataToReceiveFromClient = null;
		dataToSendToClient = null;
		inFromClient = null;
		outToClient = null;
	}

	@Override
	public void run() {

		try {
			System.out.println("A wild host has appeared: " + clientSocket.getInetAddress());
			outToClient = new ObjectOutputStream(clientSocket.getOutputStream());
			inFromClient = new ObjectInputStream(clientSocket.getInputStream());

			while (!closeConnection) {
				receiveData();
				setDataToSendToClient(dataToReceiveFromClient);
				server.broadcast(dataToSendToClient);
			}

			inFromClient.close();
			outToClient.close();
		} catch (IOException e) {
			System.err.println("I/O Error while connecting");
			e.printStackTrace();
		}


	}

	/**
	 * Writes data to the OutputStream
	 */
	public void sendData() {
		try {
			outToClient.writeObject(dataToSendToClient);
		} catch (IOException e) {
			System.err.println("Error in I/O");
			e.printStackTrace();
		}
	}

	/**
	 * Reads data from the InputStream
	 */
	public void receiveData() {
		try {
			dataToReceiveFromClient = (ClackData) inFromClient.readObject();
			if(dataToReceiveFromClient != null) {
				if (dataToReceiveFromClient.getType() == ClackData.CONSTANT_LISTUSERS) {
					setUpUserList();
				} else if (dataToReceiveFromClient.getType() == ClackData.CONSTANT_LOGOUT) {
					closeConnection = true;
					server.remove(this, dataToReceiveFromClient.getUserName());
					clientSocket.close();
					outToClient.close();
					inFromClient.close();

				} else if (dataToReceiveFromClient.getType() == ClackData.CONSTANT_NEWUSER) {
					String userName = dataToReceiveFromClient.getUserName();
					server.userNameList.add(userName);
				}
				System.out.println(dataToReceiveFromClient);
			}
		} catch (EOFException e) {
			closeConnection = true;
		} catch (ClassNotFoundException e) {
			System.err.println("ClackData cannot be found. -THIS SHOULD NEVER HAPPEN");
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	/**
	 * Sets up the user list to send when SENDUSERS is called
	 */
	public void setUpUserList() {
		String message = "Online Users:\n";
		for(String userName : server.userNameList) {
			message += userName + "\n";
		}
		dataToReceiveFromClient = new MessageClackData("", message , ClackData.CONSTANT_LISTUSERS);
	}

	/**
	 * Sets the data in the output buffer to data
	 * @param data
	 * @return data
	 */
	public ClackData setDataToSendToClient(ClackData data) {

		dataToSendToClient = data;
		return data;
	}
}