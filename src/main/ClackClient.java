package main;

import java.io.IOException;
import java.util.Scanner;

import data.ClackData;
import data.FileClackData;
import data.MessageClackData;

/**
 * The Client version of the Clack program
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
	}
	
	public void start() {
		//TODO Implement Later
		inFromStd = new Scanner(System.in);
		closeConnection = false;
		do {
			readClientData();
			printData();
		} while(!closeConnection);
		inFromStd.close();
	}
	
	public void readClientData() {
		//TODO Implement Later
		String input;
		if (inFromStd.hasNext()) {
			input = inFromStd.nextLine();
//			System.out.println(input);
			if (input == "DONE") {
				closeConnection = true;
			} else if (input.startsWith("SENDFILE")) {
				dataToSendToServer = new FileClackData(userName, input.substring("SENDFILE".length() + 1), ClackData.CONSTANT_SENDFILE);
				if (dataToSendToServer instanceof FileClackData) {
						try {
							((FileClackData) dataToSendToServer).readFileContents(KEY);
						} catch (IOException e) {
							System.err.println("Error: unable to read file.DON");
						}
						((FileClackData) dataToSendToServer).writeFileContents(KEY);
				}
			} else if (input == "LISTUSERS") {
				System.out.println("Not Yet Supported");
				//NOTHING YET
			} else {
				dataToSendToServer = new MessageClackData(userName, input, KEY, ClackData.CONSTANT_SENDMESSAGE);
			}
		}
	}
	
	public void sendData() {
		//TODO Implement Later
	}
	
	public void receiveData() {
		//TODO Implement Later
	}
	
	public void printData() {
		System.out.println(dataToSendToServer.getData(KEY));
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
				return userName == otherClient.userName &&
					   hostName == otherClient.hostName &&
					   port == otherClient.port &&
					   closeConnection == otherClient.closeConnection;
			} else {
				return userName == otherClient.userName &&
					   hostName == otherClient.hostName &&
					   port == otherClient.port &&
					   closeConnection == otherClient.closeConnection &&
					   dataToReceiveFromServer.equals(otherClient.dataToReceiveFromServer);				
			}
		} else if (dataToSendToServer != null && otherClient.dataToSendToServer != null) {
			if (dataToReceiveFromServer != null && otherClient.dataToReceiveFromServer != null) {
				return userName == otherClient.userName &&
					   hostName == otherClient.hostName &&
					   port == otherClient.port &&
					   closeConnection == otherClient.closeConnection &&
					   dataToSendToServer.equals(otherClient.dataToSendToServer) && 
					   dataToReceiveFromServer.equals(otherClient.dataToReceiveFromServer);
			} else {
				if (dataToReceiveFromServer == null && otherClient.dataToReceiveFromServer == null) {
					return userName == otherClient.userName &&
							   hostName == otherClient.hostName &&
							   port == otherClient.port &&
							   closeConnection == otherClient.closeConnection &&
							   dataToSendToServer.equals(otherClient.dataToSendToServer);
				}
				return false;
			}
		} else {
			return false;
		}	
	}
	
	@Override
	public String toString() {
		return "Client: " +  + port + " " + userName + " " + hostName + " " + closeConnection + " " + dataToReceiveFromServer.toString() + " " + dataToSendToServer.toString();
	}
}
