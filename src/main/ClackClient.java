package src.main;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.ConnectException;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.Scanner;
import java.util.Vector;

import src.data.ClackData;
import src.data.FileClackData;
import src.data.ImageClackData;
import src.data.MessageClackData;
import src.data.VideoClackData;

import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Group;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.media.MediaPlayer;
import javafx.scene.media.MediaView;
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
	private Scanner inFromStd;
	private final String KEY = "Armavirumquecano";
	private ObjectInputStream inFromServer;
	private ObjectOutputStream outToServer;
	private Vector<ClackData> history;
	private Group messageHistoryArea;
	private Socket connection;


	/**
	 * Creates a ClackClient with a default port of 7000
	 * @param userName the desired username
	 * @param hostName the hostname of the server to connect to
	 */
	public ClackClient(String userName, String hostName) {
		this(userName, hostName, DEFAULT_PORT);
	}

	/**
	 * Creates a ClackClient with a default port of 7000 and the default hostname is localhost (127.0.0.1)
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

		connection = new Socket(hostName, port);
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

		System.out.println("Generating GUI");

		Group root = new Group();

		messageHistoryArea = new Group();

		TextArea userArea = new TextArea("Debug: No Users");

		TextField sendMessage = new TextField();
		sendMessage.setPromptText("Send Message...");

		Button messageButton = new Button("Send Message");

		Button fileButton = new Button("Send File");

		root.getChildren().add(messageHistoryArea);
		root.getChildren().add(userArea);
		root.getChildren().add(sendMessage);
		root.getChildren().add(messageButton);
		root.getChildren().add(fileButton);
		userArea.setPrefSize(100,500);
		sendMessage.setPrefSize(400,100);
		messageButton.setPrefSize(100,100);
		fileButton.setPrefSize(100,100);
		messageHistoryArea.setLayoutX(0);
		messageHistoryArea.setLayoutY(0);
		userArea.setLayoutX(500);
		userArea.setLayoutY(0);
		messageButton.setLayoutX(400);
		messageButton.setLayoutY(500);
		fileButton.setLayoutX(500);
		fileButton.setLayoutY(500);
		sendMessage.setLayoutX(0);
		sendMessage.setLayoutY(500);

		messageButton.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent event) {
				dataToSendToServer = createMessage(sendMessage.getText(), ClackData.CONSTANT_SENDMESSAGE);
				sendData();
				sendMessage.clear();

			}
		});

		fileButton.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent event) {
				dataToSendToServer = createMessage(sendMessage.getText(), ClackData.CONSTANT_SENDFILE);
				sendData();
				sendMessage.clear();
			}
		});

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

		while (!closeConnection) {
			receiveData();
		}

		System.out.println("we are closing down");
		inFromStd.close();
		connection.close();
		inFromServer.close();
		outToServer.close();
		System.exit(0);

	}

	/**
	 * Creates a ClackClient
	 * @param userName the desired username
	 * @param hostName the hostname of the server to connect to
	 * @param port the port number to use
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
	}

	/**
	 * Initializes a Client
	 */

	/**
	 * Reads in data provided in standard input
	 */
	public void readClientData() {
		String input;
		if (inFromStd.hasNext()) {
			input = inFromStd.nextLine();
			if (input.startsWith("DONE")) {
				closeConnection = true;
				dataToSendToServer = createMessage("DONE",ClackData.CONSTANT_LOGOUT);
			} else if (input.startsWith("SENDFILE")) {
				dataToSendToServer = createMessage(input.substring("SENDFILE".length() + 1), ClackData.CONSTANT_SENDFILE);
				if (dataToSendToServer instanceof FileClackData) {
					try {
						((FileClackData) dataToSendToServer).readFileContents(KEY);
					} catch (IOException e) {
						System.err.println("Error: unable to read file.");
					}
					((FileClackData) dataToSendToServer).writeFileContents(KEY);
				}
			} else if (input.startsWith("LISTUSERS")) {
				dataToSendToServer = createMessage("", ClackData.CONSTANT_LISTUSERS);
			} else {
				dataToSendToServer = createMessage(input, ClackData.CONSTANT_SENDMESSAGE);
			}
		}
	}

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
	}

	/**
	 * Prints out the data from the buffer.
	 */
	public void printData() {
		if (!closeConnection) {
			if (dataToReceiveFromServer.getType() == ClackData.CONSTANT_LISTUSERS) {
				System.out.println(dataToReceiveFromServer.getData());
			}else if (dataToReceiveFromServer != null && (dataToReceiveFromServer.getType() != ClackData.CONSTANT_LOGOUT && dataToReceiveFromServer.getType() != ClackData.CONSTANT_NEWUSER)) {
				System.out.println(dataToReceiveFromServer.getData(KEY));
			}
		}
	}

	public void updateMessageList(ClackData message) {

		Node messageToDisplay;

		history.add(message);
		if (message instanceof MessageClackData || message instanceof FileClackData) {
			messageToDisplay = new TextArea(message.getData(KEY));
			((TextArea) messageToDisplay).setEditable(false);
			messageHistoryArea.getChildren().add(messageToDisplay);
		} else if (message instanceof ImageClackData) {
			messageToDisplay = new ImageView(((ImageClackData) message).getImage());
			messageHistoryArea.getChildren().add(messageToDisplay);
		} else if (message instanceof VideoClackData) {
			MediaPlayer player = new MediaPlayer(((VideoClackData) message).getVideo());
			messageToDisplay = new MediaView(player);
			messageToDisplay.addEventFilter(MouseEvent.MOUSE_CLICKED, new EventHandler<MouseEvent>() {
				@Override
				public void handle(MouseEvent event) {
					player.play();
					event.consume();
				}
			});
			messageHistoryArea.getChildren().add(messageToDisplay);
		} else {
			System.err.println("Invalid Type");
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
	public static void main(String[] args) {
		ClackClient client;
		if (args.length >= 1) {
			String[] arguments = args[0].split("(@|:)");
			try {
				if (arguments.length == 1) {
					client = new ClackClient(arguments[0]);
					launch(args);
				} else if (arguments.length == 2) {
					client = new ClackClient(arguments[0], arguments[1]);
					launch(args);
				} else if (arguments.length == 3) {
					client = new ClackClient(arguments[0], arguments[1], Integer.parseInt(arguments[2]));
					launch(args);
				}
			} catch (NumberFormatException nfe) {
				System.err.println("NumberFormatException invalid port number format");
			} catch (IllegalArgumentException iae) {
				System.err.println("IllegalArgumentException invalid number of arguments");
			}
		} else {
			client = new ClackClient();
			launch(args);
		}
	}
}