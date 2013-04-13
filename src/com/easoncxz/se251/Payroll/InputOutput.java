package com.easoncxz.se251.Payroll;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URI;
import java.net.URISyntaxException;
import java.text.DecimalFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * This class consists of static members. This class is created for organization
 * purpose.
 * 
 */
public class InputOutput {
	private static SimpleDateFormat dateFormat = new SimpleDateFormat(
			"yyyy-MM-dd");
	private static DecimalFormat decimalFormat = new DecimalFormat("0.00");

	/**
	 * produce a formatted output according to data from the given dataStore
	 * @param dataStore the dataStore whose data should be written out
	 */
	static void writeFrom(DataStore dataStore) {
		println((new Date()).toString());

		EmployeeList employeeList = dataStore.getEmployeeList();

		for (Employee employee : employeeList) {
			employee.setNett(employee.getWeekGross() - employee.getWeekTax()
					- employee.getDeduction());
			employee.setYtdEnd(employee.getYtdStart() + employee.getWeekGross());

			print(employee.getName().getFirstName());
			print(" ");
			print(employee.getName().getLastName());
			print(" (");
			print(employee.getTid());
			print(") Period: ");
			print(dateFormat.format(employee.getDateStart()));
			print(" to ");
			print(dateFormat.format(employee.getDateEnd()));
			print(". Gross: $");
			print(decimalFormat.format(employee.getWeekGross()));
			print(", PAYE: $");
			print(decimalFormat.format(employee.getWeekTax()));
			print(", Deductions: $");
			print(decimalFormat.format(employee.getDeduction()));
			print(" Nett: $");
			print(decimalFormat.format(employee.getNett()));
			print(" YTD: $");
			print(decimalFormat.format(employee.getYtdEnd()));
			println("");
		}
	}

	/**
	 * reads(parses) the contents of a file and stores it into a certain dataStore.
	 * @param uri_str 
	 * the full URI of the input file
	 * @param dataStore 
	 * the dataStore into which the data should be stored
	 */
	static void readTo(String uri_str, DataStore dataStore) {
		URI uri = null;
		File file = null;
		FileInputStream fis = null;
		InputStreamReader isr;
		BufferedReader br;
		// Scanner scanner;

		EmployeeList employeeList = new EmployeeList();

		try {
			uri = new URI(uri_str);
			file = new File(uri);
			fis = new FileInputStream(file);
			isr = new InputStreamReader(fis); // assumes charset is the default
			br = new BufferedReader(isr);

			String line;
			while ((line = br.readLine()) != null) {
				if (line.charAt(0) == '#') {
					continue;
				}

				String tidStr, nameInStr, employmentStr, rateStr, ytdStr, dateStartStr, dateEndStr, hoursStr, deductionStr;

				int tid;
				Employee.Name name;
				Employee.EmploymentType employment;
				double rate;
				double ytd;
				Date dateStart, dateEnd;
				double hours;
				double deduction;

				String[] lineComponents = line.split("\\t");

				tidStr = lineComponents[0];
				nameInStr = lineComponents[1];
				employmentStr = lineComponents[2];
				rateStr = lineComponents[3];
				ytdStr = lineComponents[4];
				dateStartStr = lineComponents[5];
				dateEndStr = lineComponents[6];
				hoursStr = lineComponents[7];
				deductionStr = lineComponents[8];
				tid = Integer.parseInt(tidStr);
				String lastName = nameInStr.split(", ")[0];
				String firstName = nameInStr.split(", ")[1];

				name = new Employee.Name(firstName, lastName);
				employment = Employee.EmploymentType.valueOf(employmentStr);
				rate = Double.parseDouble(rateStr.substring(1));
				ytd = Double.parseDouble(ytdStr.substring(1));
				dateStart = dateFormat.parse(dateStartStr);
				dateEnd = dateFormat.parse(dateEndStr);
				hours = Double.parseDouble(hoursStr);
				deduction = Double.parseDouble(deductionStr.substring(1));

				Employee employee = new Employee(name, employment, tid, ytd,
						dateStart, dateEnd, hours, deduction, rate);
				employeeList.addEmployee(employee);
			}

			dataStore.saveEmployeeList(employeeList);

			br.close();
			isr.close();
			fis.close();
		} catch (URISyntaxException e) {
			e.printStackTrace();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} catch (ParseException e) {
			e.printStackTrace();
		}

	}

	// scaffolding assist below
	static <T> void println(T o) {
		System.out.println(o);
	}

	static <T> void print(T o) {
		System.out.print(o);
	}
}
