package main;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.Scanner;
import java.util.Vector;

import data.ClackData;
import data.FileClackData;
import data.MessageClackData;

import javafx.application.Application;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.TextArea;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;

/**
 * The Client version of the Clack program
 *
 * @author Jonathan Nordby <br> Stephen Miner
 *
 */
public class ClackClient extends Application {

	private String userName, hostName;
	private int port;
	private boolean closeConnection;
	private ClackData dataToSendToServer, dataToReceiveFromServer;
	private final static int DEFAULT_PORT = 7000;
	private final String KEY = "Armavirumquecano";
	private ObjectInputStream inFromServer;
	private ObjectOutputStream outToServer;
	private Vector<ClackData> history;
	private Socket connection;
	private TextArea userArea;
	private Controller controller;
	public boolean hasGuiInit = false;


	/**
	 * Creates a ClackClient with a default port of 7000
	 *
	 * @param userName the desired username
	 * @param hostName the hostname of the server to connect to
	 */
	public ClackClient(String userName, String hostName) {
		this(userName, hostName, DEFAULT_PORT);
	}

	/**
	 * Creates a ClackClient with a default port of 7000 and the default hostname is localhost (127.0.0.1)
	 *
	 * @param userName the desired username
	 */
	public ClackClient(String userName) {
		this(userName, "localhost", DEFAULT_PORT);
	}

	/**
	 * Creates a ClackClient with a default port of 7000 and the default hostname is localhost (127.0.0.1) and a default username of anonymous.
	 */
	public ClackClient() {
		this("anonymous", "localhost", DEFAULT_PORT);
	}

	@Override
	public void start(Stage primaryStage) throws Exception {

		Socket connection = new Socket(hostName, port);
		outToServer = new ObjectOutputStream(connection.getOutputStream());
		inFromServer = new ObjectInputStream(connection.getInputStream());
		closeConnection = false;

		MessageClackData newUser = new MessageClackData(this.getUserName(), this.getUserName(), ClackData.CONSTANT_NEWUSER);
		dataToSendToServer = newUser;
		sendData();

		System.out.println("Generating GUI");

		FXMLLoader loader = new FXMLLoader(getClass().getResource("ClackGUI.fxml"));
		Object obj = new Object();
		Parent root = loader.load();
		controller = loader.getController();
		controller.addClient(this);
		controller.initialize();


//		Platform.runLater(new Runnable() {
//			@Override
//			public void run() {
//				System.out.println("Time to initialize Controller");
//
//			}
//		});


		ClientSideServerListener server = new ClientSideServerListener(this);
		server.listenerService.start();
		//launchListener();


		primaryStage.setOnCloseRequest(new EventHandler<WindowEvent>() {
			@Override
			public void handle(WindowEvent event) {
				dataToSendToServer = createMessage("DONE", ClackData.CONSTANT_LOGOUT);
				sendData();
			}
		});

		Scene scene = new Scene(root, 600, 600);
		primaryStage.setTitle("Clack");
		primaryStage.setScene(scene);
		primaryStage.show();
		hasGuiInit = true;


	}


//	public synchronized void launchListener() {
//		ClientSideServerListener server = new ClientSideServerListener(this);
//		Thread clientThread = new Thread(server);
//		clientThread.start();
//	}


	public void initialize(String[] args) {

		try {
			launch(args);
			//while (!closeConnection) { }

			System.out.println("we are closing down");
			connection.close();
			inFromServer.close();
			outToServer.close();
			System.exit(0);
		} catch (Exception e) {
		}
	}

	/**
	 * Creates a ClackClient
	 *
	 * @param userName the desired username
	 * @param hostName the hostname of the server to connect to
	 * @param port     the port number to use
	 */
	public ClackClient(String userName, String hostName, int port) {
		if (port < 1024) {
			throw new IllegalArgumentException("Invalid port number!");
		}
		this.userName = userName;
		this.hostName = hostName;
		this.port = port;
		inFromServer = null;
		outToServer = null;
		history = new Vector<ClackData>();
	}

	public String getData(ClackData data) {
		return data.getData(KEY);
	}

	/**
	 * Reads in data provided in standard input
	 */
//	public void readClientData() {
//		String input;
//		if (inFromStd.hasNext()) {
//			input = inFromStd.nextLine();
//			if (input.startsWith("DONE")) {
//				closeConnection = true;
//				dataToSendToServer = createMessage("DONE", ClackData.CONSTANT_LOGOUT);
//			} else if (input.startsWith("SENDFILE")) {
//				dataToSendToServer = createMessage(input.substring("SENDFILE".length() + 1), ClackData.CONSTANT_SENDFILE);
//				if (dataToSendToServer instanceof FileClackData) {
//					try {
//						((FileClackData) dataToSendToServer).readFileContents(KEY);
//					} catch (IOException e) {
//						System.err.println("Error: unable to read file.");
//					}
//					((FileClackData) dataToSendToServer).writeFileContents(KEY);
//				}
//			} else if (input.startsWith("LISTUSERS")) {
//				dataToSendToServer = createMessage("", ClackData.CONSTANT_LISTUSERS);
//			} else {
//				dataToSendToServer = createMessage(input, ClackData.CONSTANT_SENDMESSAGE);
//			}
//		}
//	}

	public ClackData createMessage(String message, int type) {

		switch (type) {
			case ClackData.CONSTANT_LISTUSERS:
				return new MessageClackData(userName, "", ClackData.CONSTANT_LISTUSERS);
			case ClackData.CONSTANT_LOGOUT:
				return new MessageClackData(userName, "DONE", ClackData.CONSTANT_LOGOUT);
			case ClackData.CONSTANT_SENDMESSAGE:
				return new MessageClackData(userName, message, KEY, ClackData.CONSTANT_SENDMESSAGE);
			case ClackData.CONSTANT_SENDFILE:
				return new FileClackData(userName, message, ClackData.CONSTANT_SENDFILE);
			case ClackData.CONSTANT_NEWUSER:
				return new MessageClackData(userName, userName, ClackData.CONSTANT_NEWUSER);
			default:
				return null;
		}
	}

	/**
	 * Writes the data to the OutputStream
	 */
	public void sendData() {
		try {
			outToServer.writeObject(dataToSendToServer);
		} catch (IOException e) {
			System.err.println("Cannot Write Object");
			e.printStackTrace();
		}
	}

	public void sendData(ClackData data) {
		dataToSendToServer = data;
		sendData();
	}

	/**
	 * reads data out of the InputStream and stores it in a buffer.
	 */
	public void receiveData() {
		try {
			dataToReceiveFromServer = (ClackData) inFromServer.readObject();
		} catch (ClassNotFoundException e) {
			System.err.println("ClackData cannot be found. -THIS SHOULD NEVER HAPPEN");
			e.printStackTrace();
		} catch (IOException e) {
			if (!closeConnection) {
				System.err.println("Problem reading data from server");
				e.printStackTrace();
			}
		}
		System.out.println("Time to update the History!");
		history.add(dataToReceiveFromServer);
		controller.updateMessageList();
	}

	/**
	 * Prints out the data from the buffer.
	 */
	public void printData() {
		if (!closeConnection) {
			if (dataToReceiveFromServer.getType() == ClackData.CONSTANT_LISTUSERS) {
				System.out.println(dataToReceiveFromServer.getData());
			} else if (dataToReceiveFromServer != null && (dataToReceiveFromServer.getType() != ClackData.CONSTANT_LOGOUT && dataToReceiveFromServer.getType() != ClackData.CONSTANT_NEWUSER)) {
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

	public boolean getConnectionStatus() {
		return closeConnection;
	}

	public Vector<ClackData> getHistory() {
		return history;
	}





	@Override
	public String toString() {
		return "Client: " + " " + port + " " + userName + " " + hostName + " " + closeConnection + " "
				+ dataToReceiveFromServer.toString() + " " + dataToSendToServer.toString();
	}


	/**
	 * Main method, takes in user input from the command line and initializes the
	 * client accordingly.
	 *
	 * @param args
	 */
	public static void main(String[] args) {
		ClackClient client;
		if (args.length >= 1) {
			String[] arguments = args[0].split("(@|:)");
			try {
				if (arguments.length == 1) {
					client = new ClackClient(arguments[0]);
					client.initialize(args);
				} else if (arguments.length == 2) {
					client = new ClackClient(arguments[0], arguments[1]);
					client.initialize(args);
				} else if (arguments.length == 3) {
					client = new ClackClient(arguments[0], arguments[1], Integer.parseInt(arguments[2]));
					client.initialize(args);
				}
			} catch (NumberFormatException nfe) {
				System.err.println("NumberFormatException invalid port number format");
			} catch (IllegalArgumentException iae) {
				System.err.println("IllegalArgumentException invalid number of arguments");
			} catch (Exception e) {
				e.printStackTrace();
			}
		} else {
			client = new ClackClient();
			client.initialize(args);
		}
	}
}