package com.easoncxz.se251.Payroll;

/**
 * Main entry point of the programme.
 * 
 */
public class PayrollSystem {

	public static void main(String[] args) {
		String uri_str;
		if (args.length == 0) {
			System.out
					.println("please provide the URI of the input file as an argument.");
			return;
		}
		uri_str = args[0];

		DataStore dataStore = new DataStore();
		InputOutput.readTo(uri_str, dataStore);
		dataStore.completeFields();
		InputOutput.writeFrom(dataStore);
	}
}
