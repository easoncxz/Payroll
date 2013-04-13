package com.easoncxz.se251.Payroll;

/**
 * Main entry point of the program.
 */
public class PayrollSystem {

	public static void main(String[] args) {

		// Note:
		// I used a lot of RuntimeExceptions throughout the program to
		// replace some 'required' return statements, because I am not familiar
		// enough with checked exceptions. Later these RuntimeExceptions could
		// be changed to Exceptions and used to validate user input.

		String uri_str;
		if (args.length == 0) {
			System.out
					.println("please provide the URI of the input file as an argument.");
			return;
		}
		uri_str = args[0];

		DataStore dataStore = new DataStore();
		InputOutput.parseTo(uri_str, dataStore);
		dataStore.completeFields();
		InputOutput.formatFrom(dataStore);
	}
}
