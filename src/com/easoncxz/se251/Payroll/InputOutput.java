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

import com.easoncxz.se251.Payroll.Employee.EmploymentType;

/**
 * This class consists of static members. This class is created for organization
 * purpose.
 */
public class InputOutput {
	private static SimpleDateFormat dateFormat = new SimpleDateFormat(
			"yyyy-MM-dd");
	private static DecimalFormat decimalFormat = new DecimalFormat("0.00");

	/**
	 * produce a formatted output according to data from the given dataStore
	 * 
	 * @param dataStore
	 *            the dataStore whose data should be written out
	 */
	public static void formatFrom(DataStore dataStore) {
		println((new Date()).toString());
		EmployeeList employeeList = dataStore.getEmployeeList();
		for (Employee employee : employeeList) {
			print(employee.getName().getOutputFormatted());
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
			print(decimalFormat.format(employee.getWeekDeduction()));
			print(" Nett: $");
			print(decimalFormat.format(employee.getWeekNett()));
			print(" YTD: $");
			print(decimalFormat.format(employee.getYtdEnd()));
			println("");
		}
	}

	/**
	 * reads(parses) the contents of a file and stores it into a certain
	 * dataStore.
	 * 
	 * @param uri_str
	 *            the full URI of the input file
	 * @param dataStore
	 *            the dataStore into which the data should be stored
	 */
	public static void parseTo(String uri_str, DataStore dataStore) {
		URI uri = null;
		File file = null;
		FileInputStream fis = null;
		InputStreamReader isr;
		BufferedReader br;

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

				String tidStr, nameStr, employmentStr, rateStr, ytdStartStr, dateStartStr, dateEndStr, hoursStr, weekDeductionStr;

				int tid;
				Employee.Name name;
				Employee.EmploymentType employment;
				double rate;
				double ytdStart;
				Date dateStart, dateEnd;
				double hours;
				double weekDeduction;

				String[] lineComponents = line.split("\\t");

				tidStr = lineComponents[0];
				nameStr = lineComponents[1];
				employmentStr = lineComponents[2];
				rateStr = lineComponents[3];
				ytdStartStr = lineComponents[4];
				dateStartStr = lineComponents[5];
				dateEndStr = lineComponents[6];
				hoursStr = lineComponents[7];
				weekDeductionStr = lineComponents[8];

				tid = Integer.parseInt(tidStr);
				String lastName = nameStr.split(", ")[0];
				String firstName = nameStr.split(", ")[1];
				name = new Employee.Name(firstName, lastName);
				try {
					employment = Employee.EmploymentType.valueOf(employmentStr);
				} catch (IllegalArgumentException e) {
					println("the employment-type is invalid for employee \""
							+ name.getOutputFormatted() + "\".");
					br.close();
					isr.close();
					fis.close();
					return;
				}
				rate = Double.parseDouble(rateStr.substring(1));
				ytdStart = Double.parseDouble(ytdStartStr.substring(1));
				// try {
				dateStart = dateFormat.parse(dateStartStr);
				dateEnd = dateFormat.parse(dateEndStr);
				// } catch (ParseException e) {
				// println("your date format is invalid for employee \""
				// + name.getOutputFormatted() + "\"");
				// // e.printStackTrace();
				// }
				hours = Double.parseDouble(hoursStr);
				weekDeduction = Double.parseDouble(weekDeductionStr
						.substring(1));

				if (rate <= 0) {
					println("the rate value should be positive. it seems that rate for employee \""
							+ name.getOutputFormatted() + "\" is not positive.");
					br.close();
					isr.close();
					fis.close();
					return;
				}
				if (hours <= 0) {
					println("the hours value should be positive. it seems that hours for employee \""
							+ name.getOutputFormatted() + "\" is not positive.");
					br.close();
					isr.close();
					fis.close();
					return;
				}

				if (employment == EmploymentType.Hourly) {
					employeeList.addEmployee(new EmployeeHourly(name, tid,
							ytdStart, dateStart, dateEnd, hours, weekDeduction,
							rate));
				} else if (employment == EmploymentType.Salaried) {
					employeeList.addEmployee(new EmployeeSalaried(name, tid,
							ytdStart, dateStart, dateEnd, hours, weekDeduction,
							rate));
				} else {
					// unreachable
					throw new RuntimeException("the developer was an idiot.");
				}
			}

			dataStore.saveEmployeeList(employeeList);

			br.close();
			isr.close();
			fis.close();
		} catch (URISyntaxException e) {
			println("your URI was invalid/incomplete.");
			// e.printStackTrace();
		} catch (FileNotFoundException e) {
			println("no file found by your URI");
			// e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} catch (ParseException e) {
			// println("your date format is invalid for a certain employee");
			// ^ couldn't get it to work properly
			e.printStackTrace();
		}

	}

	public static <T> void println(T o) {
		System.out.println(o);
	}

	public static <T> void print(T o) {
		System.out.print(o);
	}
}
