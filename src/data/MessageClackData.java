package data;

/**
 * Subclass of ClackData for when a user sends a message
 * 
 * @author Stephen Miner
 *
 */

public class MessageClackData extends ClackData {
	
	private String message;
	
	/**
	 * constructor to set up the username, mesage, and type. Calls a constructor from it's superclass ClackData
	 * @param userName
	 * @param message
	 * @param type
	 */
	public MessageClackData(String userName, String message, int type) {
		super(userName, type);
		this.message = message;
	}
	
	/**
	 * default constructor, calls the constructor above which relies on the superclass
	 */
	public MessageClackData(){
		this("Anon", "", 0);
	}
	
	public MessageClackData(String userName, String message, String key, int type) {
		super(userName,type);
		this.message = encrypt(message, key);
	}
	
	/**
	 * return the instant message
	 */
	public String getData() {
		return this.message;
	}
	
	public String getData(String key) {
		return (super.decrypt(message, key));
	}
	
	
	/**
	 * override of equals, compares the userName, type, and message
	 * @param otherMessage
	 * @return
	 */
	public boolean equals(MessageClackData otherMessage) {
		return (this.getUserName() == otherMessage.getUserName()
				&& this.getType() == otherMessage.getType()
				&& this.message == otherMessage.message);
	}
	
	/**
	 * override of hashCode, uses the userName, type, and message
	 */
	public int hashCode() {
		int hash = 17;
		hash = 37 * hash + getUserName().hashCode();
		hash = 37 * hash + getType();
		hash = 37 * hash + message.hashCode();
		return hash;
	}
	
	/**
	 * returns a description of the class including all local instance variables and super class variables
	 */
	public String toString() {
		return("userName: " + getUserName() + "\n" 
				+ "type: " + getType() + "\n"
				+ "date: " + getDate() + "\n"
				+ "message: " + message);
	}

}