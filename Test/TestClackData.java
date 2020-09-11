package Test;
import data.MessageClackData;
import data.FileClackData;

public class TestClackData {
	public static void main(String args[]) {
		MessageClackData testMessageDefault = new MessageClackData();
		MessageClackData testMessageDefault2 = new MessageClackData();
		
		FileClackData testFileDefault = new FileClackData();
		FileClackData testFileDefault2 = new FileClackData();
		
		MessageClackData testMessageOther = new MessageClackData("Stephen1", "test message", 1);
		MessageClackData testMessageOther2 = new MessageClackData("Stephen1", "test message", 1);
		MessageClackData testMessageOther3 = new MessageClackData("StephenOther", "test message", 1);
		MessageClackData testMessageOther4 = new MessageClackData("Stephen1", "test message 1", 1);
		MessageClackData testMessageOther5 = new MessageClackData("Stephen1", "test message", 2);
		
		FileClackData testFileOther = new FileClackData("Stephen2", "test file", 1);
		FileClackData testFileOther2 = new FileClackData("Stephen2", "test file", 1);
		FileClackData testFileOther3 = new FileClackData("StephenOther", "test file", 1);
		FileClackData testFileOther4 = new FileClackData("Stephen 2", "test file 1", 1);
		FileClackData testFileOther5 = new FileClackData("Stephen 2", "test file", 2);
		
		
	
		
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
		System.out.println( "The file name is: " + testFileDefault.getFileName() );
		System.out.println( "The date is: " + testFileDefault.getDate() );
		System.out.println();
		
		//Testing getters on the other file constructor
		System.out.println( "The userName is " + testFileOther.getUserName() );
		System.out.println( "The type is: " + testFileOther.getType() );
		System.out.println( "The file name is: " + testFileOther.getFileName() );
		System.out.println( "The date is: " + testFileOther.getDate() );
		System.out.println();
		
		
		
		/**
		 * testing default message constructor
		 */
		//testing equals on default message constructor, should return true
		System.out.println("should be true: "+ testMessageDefault.equals(testMessageDefault2));
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
		System.out.println("should be true: " + testMessageOther.equals(testMessageOther2));
		System.out.println("should be false: " + testMessageOther.equals(testMessageOther3));
		System.out.println("should be false: " + testMessageOther.equals(testMessageOther4));
		System.out.println("should be false: " + testMessageOther.equals(testMessageOther5));
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
		System.out.println("Before: " + testFileDefault.getFileName());
		testFileDefault.setFileName("success");
		System.out.println("Setting...");
		System.out.println("After: " + testFileDefault.getFileName());
		testFileDefault.setFileName("");
		System.out.println("returning to normal: " + testFileDefault.getFileName());
		
		//testing equals on the default file constructor, should return true
		System.out.println("should be true: " + testFileDefault.equals(testFileDefault2));
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
		System.out.println("Before: " + testFileOther.getFileName());
		testFileOther.setFileName("success");
		System.out.println("Setting...");
		System.out.println("After: " + testFileOther.getFileName());
		testFileOther.setFileName("test file");
		System.out.println("returning to normal: " + testFileOther.getFileName());
		System.out.println();
		
		//testing equals on the other file constructor, should return true, false, false, false
		System.out.println("should be true: " + testFileOther.equals(testFileOther2));
		System.out.println("should be false: " + testFileOther.equals(testFileOther3));
		System.out.println("should be false: " + testFileOther.equals(testFileOther4));
		System.out.println("should be false: " + testFileOther.equals(testFileOther5));
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
		
		
	}

	 
	
}
