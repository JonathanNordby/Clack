package main;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.Scanner;

import data.ClackData;
import data.FileClackData;
import data.MessageClackData;

/**
 * The Client version of the Clack program
 * 
 * @author Jonathan Nordby
 *
 */

public class ClackClient {

	private String userName, hostName;
	private int port;
	private boolean closeConnection;
	private ClackData dataToSendToServer, dataToReceiveFromServer;
	private final static int DEFAULT_PORT = 7000;
	private Scanner inFromStd;
	private final String KEY = "Armavirumquecano";
	private ObjectInputStream inFromServer;
	private ObjectOutputStream outToServer;

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
		if (port < 1024) {
			throw new IllegalArgumentException("Invalid port number!");
		}
		this.userName = userName;
		this.hostName = hostName;
		this.port = port;
		inFromServer = null;
		outToServer = null;
	}

	/**
	 * Initializes a Client
	 */
	public void start() {
		try {
			Socket connection = new Socket(hostName, port);
			outToServer = new ObjectOutputStream(connection.getOutputStream());
			inFromServer = new ObjectInputStream(connection.getInputStream());
			inFromStd = new Scanner(System.in);
			closeConnection = false;
			do {
				readClientData();
				sendData();
				receiveData();
				printData();
			} while (!closeConnection);
			inFromStd.close();
			connection.close();
		} catch (UnknownHostException e) {
			System.err.println("Cannot resolve IP Address");
			e.printStackTrace();
		} catch (IOException e) {
			System.err.println("I/O Error occured");
			e.printStackTrace();
		}

	}

	/**
	 * Reads in data provided in standard input
	 */
	public void readClientData() {
		// TODO Implement Later
		String input;
		if (inFromStd.hasNext()) {
			input = inFromStd.nextLine();
			if (input.startsWith("DONE")) {
				closeConnection = true;
				dataToSendToServer = new MessageClackData(userName, "DONE", KEY, ClackData.CONSTANT_SENDMESSAGE);
			} else if (input.startsWith("SENDFILE")) {
				dataToSendToServer = new FileClackData(userName, input.substring("SENDFILE".length() + 1),
						ClackData.CONSTANT_SENDFILE);
				if (dataToSendToServer instanceof FileClackData) {
					try {
						((FileClackData) dataToSendToServer).readFileContents(KEY);
					} catch (IOException e) {
						System.err.println("Error: unable to read file.");
					}
					((FileClackData) dataToSendToServer).writeFileContents(KEY);
				}
			} else if (input == "LISTUSERS") {
				System.out.println("Not Yet Supported");
				// NOTHING YET
			} else {
				dataToSendToServer = new MessageClackData(userName, input, KEY, ClackData.CONSTANT_SENDMESSAGE);
			}
		}
	}

	public void sendData() {
		try {
			outToServer.writeObject(dataToSendToServer);
		} catch (IOException e) {
			System.err.println("Cannot Write Object");
			e.printStackTrace();
		}
	}

	public void receiveData() {
		try {
			dataToReceiveFromServer = (ClackData) inFromServer.readObject();
		} catch (ClassNotFoundException e) {
			System.err.println("ClackData cannot be found. -THIS SHOULD NEVER HAPPEN");
			e.printStackTrace();
		} catch (IOException e) {
			System.err.println("Problem reading data from server");
			e.printStackTrace();
		}
	}

	/**
	 * Prints out the data that is to be sent to the server
	 */
	public void printData() {
		System.out.println(dataToReceiveFromServer.getData(KEY));
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

		if (dataToSendToServer == null && otherClient.dataToSendToServer == null) {
			if (dataToReceiveFromServer == null && otherClient.dataToReceiveFromServer == null) {
				return userName == otherClient.userName && hostName == otherClient.hostName && port == otherClient.port
						&& closeConnection == otherClient.closeConnection;
			} else {
				return userName == otherClient.userName && hostName == otherClient.hostName && port == otherClient.port
						&& closeConnection == otherClient.closeConnection
						&& dataToReceiveFromServer.equals(otherClient.dataToReceiveFromServer);
			}
		} else if (dataToSendToServer != null && otherClient.dataToSendToServer != null) {
			if (dataToReceiveFromServer != null && otherClient.dataToReceiveFromServer != null) {
				return userName == otherClient.userName && hostName == otherClient.hostName && port == otherClient.port
						&& closeConnection == otherClient.closeConnection
						&& dataToSendToServer.equals(otherClient.dataToSendToServer)
						&& dataToReceiveFromServer.equals(otherClient.dataToReceiveFromServer);
			} else {
				if (dataToReceiveFromServer == null && otherClient.dataToReceiveFromServer == null) {
					return userName == otherClient.userName && hostName == otherClient.hostName
							&& port == otherClient.port && closeConnection == otherClient.closeConnection
							&& dataToSendToServer.equals(otherClient.dataToSendToServer);
				}
				return false;
			}
		} else {
			return false;
		}
	}

	@Override
	public String toString() {
		return "Client: " + +port + " " + userName + " " + hostName + " " + closeConnection + " "
				+ dataToReceiveFromServer.toString() + " " + dataToSendToServer.toString();
	}

	public static void main(String args[]) {
		ClackClient client;
		if (args.length >= 1) {
			String[] arguments = args[0].split("(@|:)");
			try {
				if (arguments.length == 1) {
					client = new ClackClient(arguments[0]);
					client.start();
				} else if (arguments.length == 2) {
					client = new ClackClient(arguments[0], arguments[1]);
					client.start();
				} else if (arguments.length == 3){
					client = new ClackClient(arguments[0], arguments[1], Integer.parseInt(arguments[2]));
					client.start();
				}
			} catch (NumberFormatException nfe) {
				System.err.println("NumberFormatException invalid port number format");
			}
		} else {
			client = new ClackClient();
			client.start();
		}
		

		
			
			
			
			
			
//			String input;
//			Scanner inFromStd2 = new Scanner(System.in);
//			input = inFromStd2.nextLine();
//			if (input.equals("java ClackClient")) {
//				ClackClient test = new ClackClient("Anon");
//				test.start();
//			} else if (input.startsWith("java ClackClient") && !input.contains("@")) {
//				String inputUserName = input.substring("java ClackClient".length() + 1);
//				ClackClient test2 = new ClackClient(inputUserName);
//				test2.start();
//			} else if (input.startsWith("java ClackClient") && input.contains("@") && !input.contains(":")) {
//				String inputUserName = input.substring(("java ClackClient".length() + 1), input.indexOf('@'));
//				String inputHostName = input.substring(input.indexOf('@') + 1);
//				ClackClient test3 = new ClackClient(inputUserName, inputHostName);
//				test3.start();
//			} else if (input.startsWith("java ClackClient") && input.contains("@") && input.contains(":")) {
//				String inputUserName = input.substring(("java ClackClient".length() + 1), input.indexOf('@'));
//				String inputHostName = input.substring(input.indexOf('@') + 1, input.indexOf(':'));
//				int inputPortNum = Integer.parseInt(input.substring(input.indexOf(':') + 1));
//				ClackClient test4 = new ClackClient(inputUserName, inputHostName, inputPortNum);
//				test4.start();
//			} else
//				System.out.println("Invalid command");
//
//			inFromStd2.close();


	}
}
