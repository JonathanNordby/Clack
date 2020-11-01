package src.Test;
import src.data.MessageClackData;

import java.io.IOException;
import src.data.ClackData;
import src.data.FileClackData;

/**
 * test class for the data package
 * @author Stephen Miner
 *
 */

public class TestClackData {

	public static void main(String args[]) throws IOException {
		ClackData testMessageDefault = new MessageClackData();
		ClackData testMessageDefault2 = new MessageClackData();
		
		ClackData testFileDefault = new FileClackData();
		ClackData testFileDefault2 = new FileClackData();
		
		ClackData testMessageOther = new MessageClackData("Stephen1", "test message", 1);
		ClackData testMessageOther2 = new MessageClackData("Stephen1", "test message", 1);
		ClackData testMessageOther3 = new MessageClackData("StephenOther", "test message", 1);
		ClackData testMessageOther4 = new MessageClackData("Stephen1", "test message 1", 1);
		ClackData testMessageOther5 = new MessageClackData("Stephen1", "test message", 2);
		
		ClackData testFileOther = new FileClackData("Stephen2", "test file", 1);
		ClackData testFileOther2 = new FileClackData("Stephen2", "test file", 1);
		ClackData testFileOther3 = new FileClackData("StephenOther", "test file", 1);
		ClackData testFileOther4 = new FileClackData("Stephen 2", "test file 1", 1);
		ClackData testFileOther5 = new FileClackData("Stephen 2", "test file", 2);
		
		
	
		
		/**
		 * testing getters
		 */
		//testing getters on the default message constructor
		System.out.println( "The userName is " + testMessageDefault.getUserName() );
		System.out.println( "The type is: " + testMessageDefault.getType() );
		System.out.println( "The message is: " + testMessageDefault.getData() );
		System.out.println( "The date is: " + testMessageDefault.getDate() );
		System.out.println();
		
		//Testing getters on the other message constructor
		System.out.println( "The userName is " + testMessageOther.getUserName() );
		System.out.println( "The type is: " + testMessageOther.getType() );
		System.out.println( "The message is: " + testMessageOther.getData() );
		System.out.println( "The date is: " + testMessageOther.getDate() );
		System.out.println();
		
		//Testing getters on the default file constructor
		System.out.println( "The userName is " + testFileDefault.getUserName() );
		System.out.println( "The type is: " + testFileDefault.getType() );
		System.out.println( "The file name is: " + ((FileClackData) testFileDefault).getFileName() );
		System.out.println( "The date is: " + testFileDefault.getDate() );
		System.out.println();
		
		//Testing getters on the other file constructor
		System.out.println( "The userName is " + testFileOther.getUserName() );
		System.out.println( "The type is: " + testFileOther.getType() );
		System.out.println( "The file name is: " + ((FileClackData) testFileOther).getFileName() );
		System.out.println( "The date is: " + testFileOther.getDate() );
		System.out.println();
		
		
		
		/**
		 * testing default message constructor
		 */
		//testing equals on default message constructor, should return true
		System.out.println("should be true: "+ ((MessageClackData)testMessageDefault).equals((MessageClackData)testMessageDefault2));
		System.out.println();
		
		//testing hashcode on default message constructor, should return the same number 4 times
		System.out.println("hash code: " + testMessageDefault.hashCode());
		System.out.println("hash code: " + testMessageDefault2.hashCode());
		System.out.println("hash code: " + testMessageDefault.hashCode());
		System.out.println("hash code: " + testMessageDefault2.hashCode());
		System.out.println();
		
		//testing toString on default message constructor
		System.out.println(testMessageDefault.toString());
		
		
		
		/**
		 * testing the other message constructor
		 */
		//testing equals on other message constructor, should return true, false, false, false
		System.out.println("should be true: " + ((MessageClackData)testMessageOther).equals((MessageClackData)testMessageOther2));
		System.out.println("should be false: " + ((MessageClackData)testMessageOther).equals((MessageClackData)testMessageOther3));
		System.out.println("should be false: " + ((MessageClackData)testMessageOther).equals((MessageClackData)testMessageOther4));
		System.out.println("should be false: " + ((MessageClackData)testMessageOther).equals((MessageClackData)testMessageOther5));
		System.out.println();
		
		//testing hashcode on other message constructor, should return the same number twice followed by 3 other unique numbers
		System.out.println("hash code: " + testMessageOther.hashCode());
		System.out.println("hash code: " + testMessageOther2.hashCode());
		System.out.println("hash code: " + testMessageOther3.hashCode());
		System.out.println("hash code: " + testMessageOther4.hashCode());
		System.out.println("hash code: " + testMessageOther5.hashCode());
		System.out.println();
		
		//testing toString on other message constructor
		System.out.println(testMessageOther.toString());
		System.out.println();
		
		
		
		/**
		 * testing the file default constructor
		 */
		//testing setter for fileName
		System.out.println("Before: " + ((FileClackData) testFileDefault).getFileName());
		((FileClackData) testFileDefault).setFileName("success");
		System.out.println("Setting...");
		System.out.println("After: " + ((FileClackData) testFileDefault).getFileName());
		((FileClackData) testFileDefault).setFileName("");
		System.out.println("returning to normal: " + ((FileClackData) testFileDefault).getFileName());
		
		//testing equals on the default file constructor, should return true
		System.out.println("should be true: " + ((FileClackData)testFileDefault).equals((FileClackData)testFileDefault2));
		System.out.println();
		
		//testing  hashcode on the default file constructor, should be the same
		System.out.println("hash code: " + testFileDefault.hashCode());
		System.out.println("hash code: " + testFileDefault2.hashCode());
		System.out.println("hash code: " + testFileDefault.hashCode());
		System.out.println("hash code: " + testFileDefault2.hashCode());
		System.out.println();
		
		//testing toString for the default file constructor
		System.out.println(testFileDefault.toString());
		System.out.println();
		
		
		
		
		/**
		 * testing the other file constructor
		 */
		//testing setter for file name
		System.out.println("Before: " + ((FileClackData) testFileOther).getFileName());
		((FileClackData) testFileOther).setFileName("success");
		System.out.println("Setting...");
		System.out.println("After: " + ((FileClackData) testFileOther).getFileName());
		((FileClackData) testFileOther).setFileName("test file");
		System.out.println("returning to normal: " + ((FileClackData) testFileOther).getFileName());
		System.out.println();
		
		//testing equals on the other file constructor, should return true, false, false, false
		System.out.println("should be true: " + ((FileClackData)testFileOther).equals((FileClackData)testFileOther2));

		System.out.println("should be false: " + ((FileClackData) testFileOther).equals((FileClackData)testFileOther3));
		System.out.println("should be false: " + ((FileClackData) testFileOther).equals((FileClackData)testFileOther4));
		System.out.println("should be false: " + ((FileClackData) testFileOther).equals((FileClackData)testFileOther5));

		System.out.println("should be false: " + ((FileClackData)testFileOther).equals((FileClackData)testFileOther3));
		System.out.println("should be false: " + ((FileClackData)testFileOther).equals((FileClackData)testFileOther4));
		System.out.println("should be false: " + ((FileClackData)testFileOther).equals((FileClackData)testFileOther5));

		System.out.println();
		
		//testing  hashcode on the other file constructor, should be 2 of the same numbers followed by 3 unique numbers
		System.out.println("hash code: " + testFileOther.hashCode());
		System.out.println("hash code: " + testFileOther2.hashCode());
		System.out.println("hash code: " + testFileOther3.hashCode());
		System.out.println("hash code: " + testFileOther4.hashCode());
		System.out.println("hash code: " + testFileOther5.hashCode());
		System.out.println();
		
		//testing toString for the other file constructor
		System.out.println(testFileOther.toString());
		System.out.println();
		

		
		/*
		 * 
		 * 
		 * 
		 * TESTING FOR PART 2 OF THE PROJECT
		 * __________________________________
		 */
		
		//this should return an encrypted line followed by the original line again preserving punctuation, capitalization, whitespace, etc.
		/*Expected output:
		 * Mlal sw y mikm wiqleyx....;;;;....
		 * This is a test message....;;;;....
		 */
		ClackData testMessageEncryption = new MessageClackData("Stephen", "This is a test message....;;;;....", "TestKey", 2); //new constructor
		System.out.println(((MessageClackData)testMessageEncryption).getData()); //no key get data is already tested above
		System.out.println(((MessageClackData)testMessageEncryption).getData("TestKey")); //testing key get data
		System.out.println();
		
		//this should return the unsecurely read line, followed by the same line after it has been read securely then decrypted by getData(String key), followed by the encrypted version of the data returned by getData()
		/*expected output:
		 * the file content as written
		 * the file content as written
		 * the file content encrypted
		 */
		ClackData testFileEncryption = new FileClackData("Stephen", "test.txt", 2);
		((FileClackData)testFileEncryption).readFileContents(); //testing unsecure file reads
		((FileClackData)testFileEncryption).writeFileContents(); //testing unsecure file writes
		System.out.println(((FileClackData)testFileEncryption).getData()); //no key get data is already tested above
		((FileClackData)testFileEncryption).readFileContents("TestKey"); //testing secure file reads
		System.out.println(((FileClackData)testFileEncryption).getData("TestKey")); //testing key get data
		System.out.println(((FileClackData)testFileEncryption).getData());
		((FileClackData)testFileEncryption).writeFileContents("TestKey"); //testing secure writes, check if Test.txt says "Test file content." If it does this also confirms that the unsecure file write works as well

		
		/*
		 * should do the same as the above block just with the file specified in the project assignment
		 */
		ClackData testDoc2 = new FileClackData("Stephen", "Part2_document.txt", 2);
		((FileClackData)testDoc2).readFileContents(); 
		((FileClackData)testDoc2).writeFileContents(); 
		System.out.println(((FileClackData)testDoc2).getData()); 
		((FileClackData)testDoc2).readFileContents("AnotherTestKey"); 
		System.out.println(((FileClackData)testDoc2).getData("AnotherTestKey")); 
		System.out.println(((FileClackData)testDoc2).getData());
		((FileClackData)testDoc2).writeFileContents("AnotherTestKey"); 
	}

	 
	
}
