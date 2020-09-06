

package data;

import java.util.Date;

/**
 * This class is the superclass for MessageClackData and FileClackData
 * 
 * @author Stephen Miner
 *
 */

abstract public class ClackData {
	/**
	 * constants for the different types
	 */
	public final static int CONSTANT_LISTUSERS = 0;
	public final static int CONSTANT_LOGOUT = 1;
	public final static int CONSTANT_SENDMESSAGE = 2;
	public final static int CONSTANT_SENDFILE = 3;
	
	/**
	 * data contained in ClackData
	 */
	private String userName;
	private int type;
	private Date date;
	
	/**
	 * default constructor, calls on another constructor
	 */
	public ClackData() {
		this("Anon", 2);
	}
	
	/**
	 * constructor that creates an anonymous user, calls another constructor
	 * @param type
	 */
	public ClackData(int type) {
		this("Anon",type);
	}
	
	/**
	 * constructor to set up userName, type, and date
	 * @param userName
	 * @param type
	 */
	public ClackData(String userName, int type) {
		this.userName = userName;
		this.type = type;
		this.date = new Date();
	}
	
	/**
	 * returns the type 
	 * @return
	 */
	public int getType() {
		return this.type;
	}
	
	
	/**
	 * returns the userName
	 * @return
	 */
	public String getUserName() {
		return this.userName;
	}
	
	/**
	 * returns the date
	 * @return
	 */
	public Date getDate() {
		return this.date;
	}
	
	/**
	 * returns the data contained in this class 
	 */
	abstract public String getData(); 
}