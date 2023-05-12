package com.rashad.airline;

import java.util.Scanner;

public class Management {
	static Scanner sc = new Scanner(System.in);
	static boolean b = true;

	public static void main(String[] args) {
		System.out.println("\t\t\t Welcome to Airline");
		while (b) {
			System.out.println("1.Press to Login\n2.New User Press to Signup\n3.Exit");
			switch (sc.nextInt()) {
			case 1: {
				ManagementWorks.logIn();
				break;
			}
			case 2: {
				ManagementWorks.signUp();
				break;
			}
			case 3: {
				b = false;
			}
			default: {
				System.out.println("Please enter correct option");
				break;
			}
			}
		}
	}

}
