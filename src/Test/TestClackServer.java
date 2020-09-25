package Test;

import main.ClackServer;

public class TestClackServer {

	public static void main(String[] args) {
		
		ClackServer testServer1 = new ClackServer();
		ClackServer testServer2 = new ClackServer(13699);
		ClackServer testServer3 = new ClackServer(-9);
		ClackServer testServerOther = new ClackServer();
		
		//Testing getters on ClackServer
		System.out.println("The port is: " + testServer1.getPort());
		System.out.println("The port is: " + testServer2.getPort());
		System.out.println("The port is: " + testServer3.getPort());
		System.out.println();
		
		//Testing equalities
		System.out.println("Should be true: " + testServer1.equals(testServerOther));
		System.out.println("Should be false: " + testServer1.equals(testServer2));
		System.out.println();
		
		//Checking Hashcodes
		System.out.println("Should be the same: " + testServer1.hashCode() + " "  + testServerOther.hashCode());
		System.out.println("Should be different: " + testServer1.hashCode() + " " + testServer2.hashCode());
		System.out.println();
		
		//Checking ToString
		System.out.println(testServer1.toString());
		System.out.println(testServer2.toString());
		System.out.println(testServer3.toString());
	}
}
