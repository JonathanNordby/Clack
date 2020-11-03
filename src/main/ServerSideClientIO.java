package main;

import java.io.EOFException;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;

import data.ClackData;

public class ServerSideClientIO implements Runnable {

	private boolean closeConnection;
	private ClackData dataToReceiveFromClient;
	private ClackData dataToSendToClient;
	private ObjectInputStream inFromClient;
	private ObjectOutputStream outToClient;
	private ClackServer server;
	private Socket clientSocket;

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

	public void sendData() {
		try {
			outToClient.writeObject(dataToSendToClient);
		} catch (IOException e) {
			System.err.println("Error in I/O");
			e.printStackTrace();
		}
	}

	public void receiveData() {
		try {
			dataToReceiveFromClient = (ClackData) inFromClient.readObject();
			if (dataToReceiveFromClient.getType() == ClackData.CONSTANT_LOGOUT) {
				closeConnection = true;
				server.remove(this);
				clientSocket.close();
				outToClient.close();
				inFromClient.close();

			} else if (dataToReceiveFromClient.getType() == ClackData.CONSTANT_LISTUSERS) {
				
			}
			System.out.println(dataToReceiveFromClient);
		} catch (EOFException e) {
			closeConnection = true;
		} catch (ClassNotFoundException e) {
			System.err.println("ClackData cannot be found. -THIS SHOULD NEVER HAPPEN");
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public ClackData setDataToSendToClient(ClackData data) {

		dataToSendToClient = data;
		return data;
	}
}