package main;

import java.sql.Connection;

public class ManagementWorks {

	public static void signUp() {
		
	}

	public static void logIn() {
		
		try {
			Connection c=Connections.getConnection();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

}
