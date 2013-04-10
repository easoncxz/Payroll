package com.easoncxz.se251.Payroll;

/**
 * Main entry point of the programme.
 *
 */
public class PayrollSystem {

	public static void main(String[] args) {
		String uri_str;
		if (args.length == 0) {
			System.out.println("please provide the URI of the input file.");
			return;
		}
		uri_str = args[0];
		
		DataStore dataStore = new DataStore();
		InputOutput.readInputTo(uri_str, dataStore);
		Calculations.resolveEmployeeFor(dataStore);
		InputOutput.produceOutputFrom(dataStore);
	}
}
