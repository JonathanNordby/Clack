package src.main;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.ConnectException;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.Scanner;

import src.data.ClackData;
import src.data.FileClackData;
import src.data.MessageClackData;

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

			MessageClackData newUser = new MessageClackData(this.getUserName(), this.getUserName(), ClackData.CONSTANT_NEWUSER);
			dataToSendToServer = newUser;
			sendData();

			ClientSideServerListener server = new ClientSideServerListener(this);
			Thread clientThread = new Thread(server);
			clientThread.start();

			while (!closeConnection) {
				readClientData();
				sendData();
				//wait();
			}
			inFromStd.close();
			connection.close();
			inFromServer.close();
			outToServer.close();
			System.exit(0);
		} catch (UnknownHostException e) {
			System.err.println("Cannot resolve IP Address");
			e.printStackTrace();
		} catch (ConnectException ce) {
			System.err.println("Could not connect to the server.");
		} catch (IOException e) {
			System.err.println("I/O Error occurred");
			e.printStackTrace();
			//	} catch (InterruptedException e) {
			//		System.err.println("interrupted");
		}

	}

	/**
	 * Reads in data provided in standard input
	 */
	public void readClientData() {
		String input;
		if (inFromStd.hasNext()) {
			input = inFromStd.nextLine();
			if (input.startsWith("DONE")) {
				closeConnection = true;
				dataToSendToServer = new MessageClackData(userName, "DONE", ClackData.CONSTANT_LOGOUT);
			} else if (input.startsWith("SENDFILE")) {
				dataToSendToServer = new FileClackData(userName, input.substring("SENDFILE".length() + 1), ClackData.CONSTANT_SENDFILE);
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
				dataToSendToServer = new MessageClackData(userName, "", ClackData.CONSTANT_LISTUSERS);
			} else {
				dataToSendToServer = new MessageClackData(userName, input, KEY, ClackData.CONSTANT_SENDMESSAGE);
				//System.out.println((MessageClackData) dataToSendToServer);
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
//		System.out.println("Receiving Data");
		try {
//			if (dataToReceiveFromServer != null) {
//				System.out.println("Data isn't null, should print something");
			//System.out.println((MessageClackData) dataToSendToServer);
			//System.out.println(dataToSendToServer.getType());
			dataToReceiveFromServer = (ClackData) inFromServer.readObject();


//			}
		} catch (ClassNotFoundException e) {
			System.err.println("ClackData cannot be found. -THIS SHOULD NEVER HAPPEN");
			e.printStackTrace();
		} catch (IOException e) {
			if (!closeConnection) {
				System.err.println("Problem reading data from server");
				e.printStackTrace();
			}
		}
	}

	/**
	 * Prints out the data that is to be sent to the server
	 */
	public void printData() {
		if (!closeConnection) {
			if (dataToReceiveFromServer != null || dataToReceiveFromServer.getType() != ClackData.CONSTANT_LOGOUT) {
				System.out.println(dataToReceiveFromServer.getData(KEY));
			}
		}
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

	public boolean getConnectionStatus() { return closeConnection; }

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

	/**
	 * Main method, takes in user input from the command line and initializes the
	 * client accordingly.
	 *
	 * @param args
	 */
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
				} else if (arguments.length == 3) {
					client = new ClackClient(arguments[0], arguments[1], Integer.parseInt(arguments[2]));
					client.start();
				}
			} catch (NumberFormatException nfe) {
				System.err.println("NumberFormatException invalid port number format");
			} catch (IllegalArgumentException iae) {
				System.err.println("IllegalArgumentException invalid number of arguments");
			}
		} else {
			client = new ClackClient();
			client.start();
		}
	}
}