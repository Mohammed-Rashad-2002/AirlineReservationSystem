package main;

import java.util.Scanner;

public class Management {
	static Scanner sc = new Scanner(System.in);

	public static void main(String[] args) {
		System.out.println("\t\t\t Welcome to Airline");
		System.out.println("1.Press to Login\n2.New User Press to Signup");
		switch (sc.nextInt()) {
		case 1: {
			ManagementWorks.logIn();
			break;
		}
		}
	}

}
