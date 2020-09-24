package data;
import java.io.*;

/**
 * subclass of ClackData for when the user sends a file
 * @author Stephen Miner
 *
 */

public class FileClackData extends ClackData {
	/**
	 * file name and contents
	 */
	private String fileName; 
	private String fileContents;
	
	/**
	 * constructor to set up username, filename, and type. calls the super class constructor
	 * @param userName
	 * @param fileName
	 * @param type
	 */
	public FileClackData(String userName, String fileName, int type) {
		super(userName, type);
		this.fileName = fileName;
		this.fileContents = null;
	}
	
	/**
	 * default constructor, calls the other constructor which calls the super class constructor
	 */
	public FileClackData() {
		this("Anon", "", 0);
	}
	
	/**
	 * sets the file name
	 * @param fileName
	 */
	public void setFileName(String fileName) {
		this.fileName = fileName;
	}
	
	/**
	 * returns the file name
	 * @return
	 */
	public String getFileName() {
		return fileName;
	}
	
	/**
	 * returns the file contents
	 */
	public String getData() {
		return fileContents;
	}
	
	public String getData(String key) {
		return(super.decrypt(fileContents,key));
	}
	
	/**
	 * opens a file with the name FileName, reads in all the lines of the file into a string, then sets fileContents
	 * equal to that string.
	 * @throws IOException
	 */
	public void readFileContents() throws IOException {
		final String EOF = null;
		String result = "";
		try {
			File file = new File(fileName);
			FileReader fr = new FileReader(file);
			BufferedReader inFromFile = new BufferedReader(fr);

			String input;
			while((input = inFromFile.readLine()) != EOF) {
				result += input;
			}
			
		inFromFile.close();
		}catch(FileNotFoundException fnf) {
			System.err.println("File not found");
		}catch (IOException ioe) {
			System.err.println("issue with reading"); 
		}
		this.fileContents = result;
	}
	
	public void readFileContents(String key) throws IOException {
		final String EOF = null;
		String unencryptedResult = "";
		try {
			File file = new File(fileName);
			FileReader fr = new FileReader(file);
			BufferedReader inFromFile = new BufferedReader(fr);
			
			String input;
			while((input = inFromFile.readLine()) != EOF) {
				unencryptedResult += input;
			}
		
			inFromFile.close();		
		} catch(FileNotFoundException fnf) {
			System.err.println("File not found");
		} catch(IOException ioe) {
			System.err.println("issue with reading");
		}
		this.fileContents = super.encrypt(unencryptedResult, key);
	}
	/**
	 * does nothing right now
	 */
	public void writeFileContents() {
		
	}
	
	/**
	 * override of equals(), compared the username, type, file name, and file contents
	 * @param otherFile
	 * @return
	 */
	public boolean equals(FileClackData otherFile) {
		return (getUserName() == otherFile.getUserName()
				&& getType() == otherFile.getType()
				&& getFileName() == otherFile.getFileName()
				&& getData() == otherFile.getData());
	}
	
	/**
	 * override of hashcode() using the username, type, file name, and file contents 
	 */
	public int hashCode() {
		int hash = 17;
		hash = 37 * hash + getUserName().hashCode();
		hash = 37 * hash + getType();
		hash = 37 * hash + getFileName().hashCode();
		if(getData() == null)
			hash = hash+1;
		else
			hash = 37 * hash + getData().hashCode();
		return hash;
	}
	
	/**
	 * outputs the information of the file data object, including the instance variables in the super class
	 */
	public String toString() {
		return("userName: " + getUserName() + "\n" 
				+ "type: " + getType() + "\n"
				+ "date: " + getDate() + "\n"
				+ "file name: " + getFileName() + "\n"
				+ "file contents: " + getData());
	
	}
}
