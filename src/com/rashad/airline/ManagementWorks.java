package com.rashad.airline;

import java.sql.Connection;
import java.util.Date;
import java.util.Locale;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Scanner;

import com.mysql.cj.protocol.Resultset;

public class ManagementWorks {
	static Scanner sc = new Scanner(System.in);

	static LocalDateTime ldt = LocalDateTime.now();
	static String formatDate = DateTimeFormatter.ofPattern("yyyy-MM-dd", Locale.ENGLISH).format(ldt);
	static java.sql.Date date = java.sql.Date.valueOf(formatDate);

	public static String flightList() {
		try {
			Connection c = Connections.getConnection();
			PreparedStatement p = c.prepareStatement("select * from flight where date>=?",
					ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
			p.setDate(1, date);
			ResultSet rs = p.executeQuery();
			if (rs.next()) {
				rs.beforeFirst();
				System.out.println("Flight Details\n");
				Result(rs);
				return "Present";
			} else {
				System.out.println("No flight founds");
				return "";
			}

		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return "";
		}
	}

	public static void Result(ResultSet rs) {

		try {
			while (rs.next()) {
				System.out.print("flight_id: " + rs.getInt(1));
				System.out.print(" Date: " + rs.getDate(2));
				System.out.print(" From:" + rs.getString(3));
				System.out.print(" To:" + rs.getString(4));
				System.out.print(" Depature_time: " + rs.getTime(5));
				System.out.print(" Arrival_time: " + rs.getTime(6));
				System.out.print(" Seat_Availabe: " + rs.getInt(7));
				System.out.print(" Price: " + rs.getInt(8));
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	public static String filterByLocation() {
		try {
			Connection c = Connections.getConnection();
			PreparedStatement p = c.prepareStatement("select * from flight where from_loc=? and to_loc=? and date>=?",
					ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
			System.out.print("Enter Location You want to travel from:");
			String from_loc = sc.next();
			System.out.print("\nEnter Destination:");
			String to_loc = sc.next();
			p.setString(1, from_loc);
			p.setString(2, to_loc);
			p.setDate(3, date);
			ResultSet rs = p.executeQuery();
			if (rs.next()) {
				rs.beforeFirst();
				System.out.println("Flight Details\n");
				Result(rs);
				return "Present";
			} else {
				System.out.println("No flight founds based on your search");
				return "";
			}

		} catch (Exception e) {
			// TODO Auto-generated catch block
//			e.printStackTrace();
			System.out.println("some error!!!");
			return "";
		}
	}

	public static void upComingFlightsDetails(String email) {
		try {
			Connection c = Connections.getConnection();
			PreparedStatement p = c.prepareStatement("select * from flightbookrecord where c_email=? and date>=?",
					ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
			p.setString(1, email);
			p.setDate(2, date);
			ResultSet rs = p.executeQuery();
			if (rs.next()) {
				rs.beforeFirst();

				while (rs.next()) {
					System.out.print("Flight_id: " + rs.getInt(1));
					System.out.print(" Date: " + rs.getDate(2));
					System.out.print(" From: " + rs.getString(3));
					System.out.print(" To: " + rs.getString(4));
					System.out.println();
				}
			} else {
				System.out.println("You don't have any upcoming flight");
			}
			System.out.println("Thanks For visited Airline By Rashad");

		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public static void bookFlight(String name, String email) {
		System.out.println("\n---------------------Booking--------------------------");
		try {
			Connection c = Connections.getConnection();
			System.out.println("Enter Flight Id To book the flight");
			int flight_id = sc.nextInt();
			PreparedStatement p = c.prepareStatement("select * from flight where flight_id=?");
			p.setInt(1, flight_id);
			ResultSet rs = p.executeQuery();

			while (rs.next()) {
				PreparedStatement p1 = c.prepareStatement("insert into flightbookrecord values(?,?,?,?,?,?)");
				PreparedStatement p2 = c.prepareStatement("update flight set seat_available=? where flight_id=?");
				p1.setInt(1, rs.getInt(1));
				p1.setDate(2, rs.getDate(2));
				p1.setString(3, rs.getString(3));
				p1.setString(4, rs.getString(4));
				p1.setString(5, name);
				p1.setString(6, email);
				p1.executeUpdate();
				p2.setInt(1, rs.getInt(7) - 1);
				p2.setInt(2, flight_id);
				p2.executeUpdate();
				System.out.println("Your Flight ticket is booked successfully");
				System.out.println("1.Press To check your upcoming flight details\n2.press to exit");
				if (sc.nextInt() == 1) {
					upComingFlightsDetails(email);
				} else {
					System.out.println("Thanks For visited Airline By Rashad");
				}

			}

		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	public static void signUp() {
		System.out.println("----------------SignUp---------------------");
		System.out.println("Enter Your Name:");
		String name = sc.next();
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
//			System.out.println(c);
			System.out.println("Enter Your Email Id:");
			String email = sc.next();

			PreparedStatement p = c.prepareStatement("select * from userdetails where email=?");
			p.setString(1, email);
			ResultSet rs = p.executeQuery();

			if (rs.next()) {
				System.out.println("Enter Your password");
				String password = sc.next();
				if (rs.getString(2).equals(email) && rs.getString(3).equals(password)) {
					System.out.println("Welcome " + rs.getString(1));

					System.out.println(
							"1.Press for list all available flight\n2.Press for Your Prefered location available flight\n3.To check your upcoming flights details");
					switch (sc.nextInt()) {
					case 1: {
						String data = flightList();
						if (data == "Present" && data != null) {
							bookFlight(rs.getString(1), rs.getString(2));
						}

						break;
					}
					case 2: {
						String data = filterByLocation();
						if (data == "Present" && data != null) {
							bookFlight(rs.getString(1), rs.getString(2));
						}

						break;
					}

					case 3: {
						upComingFlightsDetails(email);
						break;
					}

					default:
						break;
					}

				} else {
					System.out.println("Password incorrect please login again");
					logIn();
				}
			} else {
				System.out.println("Email not found please signup");
				signUp();
			}

		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			System.out.println("something wents wrong");
		}

	}

}
