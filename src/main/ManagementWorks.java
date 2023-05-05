package main;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.Scanner;

public class ManagementWorks {
	static Scanner sc = new Scanner(System.in);

	public static void signUp() {
		System.out.println("----------------SignUp---------------------");
		System.out.println("Enter Your Name:");
		sc.nextLine();
		String name = sc.nextLine();
		System.out.println("Enter Your Email Id:");
		String email = sc.next();
		System.out.println("Enter Your Password:");
		String password = sc.next();
		try {
			Connection c = Connections.getConnection();
			PreparedStatement p = c.prepareStatement("insert into userdetails values(?,?,?)");
			p.setString(1, name);
			p.setString(2, email);
			p.setString(3, password);
			p.executeUpdate();
			System.out.println("Successfully Created Your Account Please Login to continue");
			logIn();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	public static void logIn() {
		System.out.println("----------------Login---------------------");

		try {
			Connection c = Connections.getConnection();
			System.out.println(c);
			System.out.println("Enter Your Email Id:");
			String email = sc.next();
			System.out.println("Enter Your password");
			String password = sc.next();
			
			PreparedStatement p = c.prepareStatement("select * from userdetails where email=?");
			p.setString(1, email);
			ResultSet rs = p.executeQuery();

			while (rs.next()) {
				if (rs.getString(2).equals(email) && rs.getString(3).equals(password)) {
					System.out.println("Welcome "+rs.getString(1));
				}
			}

		} catch (Exception e) {
			// TODO Auto-generated catch block
//			e.printStackTrace();
			System.out.println("something wents wrong");
		}

	}

}
