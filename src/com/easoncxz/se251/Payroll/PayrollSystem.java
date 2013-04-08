package com.easoncxz.se251.Payroll;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URI;
import java.net.URISyntaxException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Scanner;

public class PayrollSystem {
	private static List<Employee> employeeList = new ArrayList<Employee>();

	public static void main(String[] args) {
		String uri_str;
		if (args.length == 0) {
			System.out.println("please provide the URI of the input file.");
			return;
		}
		uri_str = args[0]; // uri_str = "file:///D:/workspace/Payroll/e.txt";
		List<Employee> employeeList = readInput(uri_str);
		Employee employee = employeeList.get(0);
		System.out
				.println(((Float) employee.getDeduction()).toString() + "###");
		System.out.println(employee.getName().getFirstName() + "###");
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
				EmploymentType employment;
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
				employment = EmploymentType.valueOf(employmentStr);
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
			System.out.println("done!!!");

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

	private static float calcTax(float gross) {
		// TODO
		return 0;
	}
}
