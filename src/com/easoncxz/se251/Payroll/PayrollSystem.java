package com.easoncxz.se251.Payroll;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.Scanner;

public class PayrollSystem {

	public static void main(String[] args) {
		String uri_str;
		URI uri = null;
		File file = null;
		FileInputStream fis = null;
		InputStreamReader isr;
		BufferedReader br;
		Scanner scanner;

		if (args.length == 0) {
			// System.out.println("please provide the URI of the input file.");
			// return;
		}

		// uri_str = args[0];
		uri_str = "file:///C:/Users/Eason/Desktop/e.txt";
		// uri_str = "c:\\users\\eason\\desktop\\e.txt";

		try {
			uri = new URI(uri_str);
		} catch (URISyntaxException e) {
			e.printStackTrace();
		}
		file = new File(uri);
		try {
			fis = new FileInputStream(file);
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		isr = new InputStreamReader(fis); // assumes charset is the default
		br = new BufferedReader(isr);
		scanner = new Scanner(br);

		// while (scanner.hasNext()) {
		// System.out.println(scanner.next());
		// }
		// System.out.println("done!!!");
		// scanner.close();

		try {
			String l;
			while ((l = br.readLine()) != null) {
				System.out.println(l);
			}
			System.out.println("done!!!");
		} catch (IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}

		try {
			br.close();
			isr.close();
			fis.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

}
