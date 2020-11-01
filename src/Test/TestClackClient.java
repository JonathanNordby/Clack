package src.Test;

import src.main.ClackClient;

public class TestClackClient {

	public static void main(String[] args) {
		
		ClackClient testClient1 = new ClackClient();
		ClackClient testClient2 = new ClackClient("doejohn");
		ClackClient testClient3 = new ClackClient("doejohn", "ourHost");
		ClackClient testClient4 = new ClackClient("doejohn", "ourHost", 13699);
		ClackClient testClientOther = new ClackClient();
		
		//Testing getters on ClackClient
		System.out.println("The hostname is: " + testClient1.getHostName());
		System.out.println("The port is: " + testClient1.getPort());
		System.out.println("the username is: " + testClient1.getUserName());
		System.out.println();
		
		System.out.println("The hostname is: " + testClient2.getHostName());
		System.out.println("The port is: " + testClient2.getPort());
		System.out.println("the username is: " + testClient2.getUserName());
		System.out.println();
		
		System.out.println("The hostname is: " + testClient3.getHostName());
		System.out.println("The port is: " + testClient3.getPort());
		System.out.println("the username is: " + testClient3.getUserName());
		System.out.println();
		
		System.out.println("The hostname is: " + testClient4.getHostName());
		System.out.println("The port is: " + testClient4.getPort());
		System.out.println("the username is: " + testClient4.getUserName());
		System.out.println();
		
		//checking equalities
		System.out.println("Should be equal: " + testClient1.equals(testClientOther));
		System.out.println("Should not be equal: " + testClient1.equals(testClient2));
		
		//checking hashcodes
		System.out.println("Should be the same: " + testClient1.hashCode() + " " + testClientOther.hashCode());
		System.out.println("Should be different: " + testClient1.hashCode() + " " + testClient2.hashCode());
		
		testClient2.start();
	}
}
