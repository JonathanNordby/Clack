

package data;

import java.util.Date;

/**
 * This class is the superclass for MessageClackData
 * 
 * @author Stephen Miner
 *
 */

abstract public class ClackData {
	
	private final static int CONSTANT_LISTUSERS = 0;
	private final static int CONSTANT_LOGOUT = 1;
	private final static int CONSTANT_SENDMESSAGE = 2;
	private final static int CONSTANT_SENDFILE = 3;
	
	private String userName;
	private int type;
	private Date date;
	
	public ClackData() {
		this("Anon", 2);
	}
	
	public ClackData(int type) {
		this("Anon",type);
	}
	
	public ClackData(String userName, int type) {
		this.userName = userName;
		this.type = type;
	}
	
	public int getType() {
		return this.type;
	}
	
	public String getUserName() {
		return this.userName;
	}
	
	
	public Date getDate() {
		return this.date;
	}
	
	abstract public void getData(); //not sure what the return type is supposed to be here
}
