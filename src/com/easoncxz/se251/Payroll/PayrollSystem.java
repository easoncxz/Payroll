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
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Scanner;

public class PayrollSystem {
	public static final double[] TAX_RATES = { 0.33, 0.3, 0.175, 0.105 };
	public static final double[] TAX_INCOME_THRESHOLDS = { 70000, 48000, 14000 };

	private static List<Employee> employeeList = new ArrayList<Employee>();

	public static void main(String[] args) {
		String uri_str;
		if (args.length == 0) {
			System.out.println("please provide the URI of the input file.");
			return;
		}
		uri_str = args[0]; // uri_str = "file:///D:/workspace/Payroll/e.txt";
		List<Employee> employeeList = readInput(uri_str);
		// Employee employee = employeeList.get(0);
		// System.out
		// .println(((Float) employee.getDeduction()).toString() + "###");
		// System.out.println(employee.getName().getFirstName() + "###");

		// println(calcTaxAnnual(48500.62));

		Date date = new Date();
		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
		DecimalFormat df = new DecimalFormat("#.00");
		System.out.println(date);
		for (Employee employee : employeeList) {
			if (employee.getEmployment() == Employee.EmploymentType.Salaried) {
				employee.setAnnualGross(employee.getRate());
				employee.setWeekGross(employee.getAnnualGross() / 52);
				employee.setAnnualTax(calcTaxAnnual(employee.getAnnualTax()));
				employee.setWeekTax(employee.getAnnualTax() / 52);
			} else if (employee.getEmployment() == Employee.EmploymentType.Hourly) {
				employee.setWeekGross(calcWeeklyFromHours(employee.getRate(),
						employee.getHours()));
				employee.setAnnualGross(employee.getWeekGross() * 52);
				employee.setAnnualTax(calcTaxAnnual(employee.getAnnualGross()));
				employee.setWeekTax(employee.getAnnualTax() / 52);
			} else {
				// shouldn't be reached
			}
			employee.setNett(employee.getWeekGross() - employee.getWeekTax()
				- employee.getDeduction());
			employee.setYtdEnd(employee.getYtdStart() + employee.getWeekGross());
			
			print(employee.getName().getFirstName());
			print(" ");
			print(employee.getName().getLastName());
			print(" (");
			print(employee.getTid());
			print(") Period: ");
			print(format.format(employee.getDateStart()));
			print(" to ");
			print(format.format(employee.getDateEnd()));
			print(". Gross: $");
			print(df.format(employee.getWeekGross()));
			print(", PAYE: $");
			print(df.format(employee.getWeekTax()));
			print(", Deductions: $");
			print(df.format(employee.getWeekTax()));
			print(" Nett: $");
			print(df.format(employee.getNett()));
			print(" YTD: $");
			print(df.format(employee.getNett()));
			println("");
		}
	}

	private static List<Employee> readInput(String uri_str) {
		URI uri = null;
		File file = null;
		FileInputStream fis = null;
		InputStreamReader isr;
		BufferedReader br;
		Scanner scanner;

		List<Employee> employeeList = new ArrayList<Employee>();

		try {
			uri = new URI(uri_str);
			file = new File(uri);
			fis = new FileInputStream(file);
			isr = new InputStreamReader(fis); // assumes charset is the default
			br = new BufferedReader(isr);
			scanner = new Scanner(br);

			// while (scanner.hasNext()) {
			// System.out.println(scanner.next());
			// }
			// System.out.println("done!!!");
			// scanner.close();

			String line;
			while ((line = br.readLine()) != null) {
				if (line.charAt(0) == '#') {
					continue;
				}

				String tidStr, nameInStr, employmentStr, rateStr, ytdStr, dateStartStr, dateEndStr, hoursStr, deductionStr;

				int tid;
				Name name;
				Employee.EmploymentType employment;
				float rate;
				float ytd;
				Date dateStart, dateEnd;
				float hours;
				float deduction;

				String[] lineComponents = line.split("\\t");

				// // scaffolding
				// for (String s : lineComponents) {
				// System.out.print(s + "###");
				// }
				// System.out.println();

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
				String lastName = nameInStr.split(", ")[0], firstName = nameInStr
						.split(", ")[1];
				name = new Name(firstName, lastName);
				employment = Employee.EmploymentType.valueOf(employmentStr);
				rate = Float.parseFloat(rateStr.substring(1));
				ytd = Float.parseFloat(ytdStr.substring(1));
				SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
				dateStart = format.parse(dateStartStr);
				dateEnd = format.parse(dateEndStr);
				hours = Float.parseFloat(hoursStr);
				deduction = Float.parseFloat(deductionStr.substring(1));

				Employee employee = new Employee(name, employment, tid, ytd,
						dateStart, dateEnd, hours, deduction, rate);
				employeeList.add(employee);
			}

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

		return employeeList;
	}

	private static double calcTaxAnnual(double gross) {
		gross = Math.floor(gross);
		if (gross > TAX_INCOME_THRESHOLDS[0]) {
			return (gross - TAX_INCOME_THRESHOLDS[0]) * TAX_RATES[0]
					+ calcTaxAnnual(TAX_INCOME_THRESHOLDS[0]);
		} else if (gross <= TAX_INCOME_THRESHOLDS[0]
				&& gross > TAX_INCOME_THRESHOLDS[1]) {
			return (gross - TAX_INCOME_THRESHOLDS[1]) * TAX_RATES[1]
					+ calcTaxAnnual(TAX_INCOME_THRESHOLDS[1]);
		} else if (gross <= TAX_INCOME_THRESHOLDS[1]
				&& gross > TAX_INCOME_THRESHOLDS[2]) {
			return (gross - TAX_INCOME_THRESHOLDS[2]) * TAX_RATES[2]
					+ calcTaxAnnual(TAX_INCOME_THRESHOLDS[2]);
		} else if (gross <= TAX_INCOME_THRESHOLDS[2] && gross > 0) {
			return gross * TAX_RATES[3];
		} else {
			// should not be reached
			return gross;
		}

	}

	private static double calcWeeklyFromHours(double rate, double hours) {
		if (hours > 43) {
			return (hours - 43) * rate * 2 + calcWeeklyFromHours(rate, 43);
		} else if (hours <= 43 & hours > 40) {
			return (hours - 40) * rate * 1.5 + calcWeeklyFromHours(rate, 40);
		} else if (hours <= 40 & hours > 0) {
			return hours * rate;
		} else {
			// shouldn't be reachable
			return -0.277; // random value
		}
	}

	// scaffolding assist below
	private static <T> void println(T o) {
		System.out.println(o);
	}

	private static <T> void print(T o) {
		System.out.print(o);
	}
}
