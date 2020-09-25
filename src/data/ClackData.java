

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
		this("Anon", 0);
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
	/**
	 * returns the data contained in this class, with the message or file contents encrypted
	 * @param key
	 * @return
	 */
	abstract public String getData(String key);
	
	/**
	 * uses the viginere cipher to encrypt a string using a key
	 * @param inputStringToEncrypt
	 * @param key
	 * @return
	 */
	protected String encrypt(String inputStringToEncrypt, String key) {
		String encodedString = "";
	    char inputStringArray[] = inputStringToEncrypt.toCharArray();
	    int inputStringLength = inputStringToEncrypt.length();
	    char keyArray[] = key.toCharArray();
	    char newKeyArray[] = new char[inputStringLength];
	   
	    for(int index = 0, secondIndex = 0; index < inputStringLength; index++, secondIndex++) {
	    	if(secondIndex == key.length())
	    		secondIndex = 0;
	    	newKeyArray[index] = keyArray[secondIndex];
	    	 }
	        
	    for (int inputIndex = 0, keyIndex = 0; inputIndex <= inputStringLength-1; inputIndex++) {
	        char currentChar = inputStringArray[inputIndex];
            if (currentChar <= 'z' && currentChar  >= 'a') {
	            encodedString += (char)((currentChar + Character.toLowerCase(newKeyArray[keyIndex]) - (2*'a')) % 26 + 'a');
	            keyIndex = keyIndex++ % key.length();
	            keyIndex++;
	        }
            else if (currentChar <= 'Z' && currentChar  >= 'A') {
	            encodedString += (char)((currentChar + Character.toUpperCase(newKeyArray[keyIndex]) - (2*'A')) % 26 + 'A');
	            keyIndex = keyIndex++ % newKeyArray.length;
	            keyIndex++;
            }
            else
            	encodedString += currentChar;
        }
        return encodedString;
	}
	
	/**
	 * uses the viginere cipher to decrypt a string using a key
	 * @param inputStringToDecrypt
	 * @param key
	 * @return
	 */
	protected String decrypt(String inputStringToDecrypt, String key) {
		String decodedString = "";
		char inputStringArray[] = inputStringToDecrypt.toCharArray();
		int inputStringLength = inputStringToDecrypt.length();
		char keyArray[] = key.toCharArray();
		char newKeyArray[] = new char[inputStringLength];
		
	    for(int index = 0, secondIndex = 0; index < inputStringLength; index++, secondIndex++) {
	    	if(secondIndex == key.length())
	    		secondIndex = 0;
	    	newKeyArray[index] = keyArray[secondIndex];
	    }

		
		for (int inputIndex = 0, keyIndex = 0; inputIndex <= inputStringLength-1; inputIndex++) {
			char currentChar = inputStringArray[inputIndex];
			if (currentChar <= 'z' && currentChar >= 'a') {
				decodedString += (char)((currentChar - Character.toLowerCase(newKeyArray[keyIndex]) + 26) % 26 + 'a');
				keyIndex = keyIndex++ % key.length();
				keyIndex++;
			}
			else if(currentChar <= 'Z' && currentChar >= 'A') {
				decodedString += (char)((currentChar - Character.toUpperCase(newKeyArray[keyIndex]) + 26) % 26 + 'A');
				keyIndex = keyIndex++ % key.length();
				keyIndex++;
			}
			else
				decodedString += currentChar;
		}
		return decodedString;
	}
}