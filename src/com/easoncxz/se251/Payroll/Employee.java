package com.easoncxz.se251.Payroll;

import java.util.Comparator;
import java.util.Date;

import com.easoncxz.se251.Payroll.Employee.Name;

/**
 * This is a storage class which stores all the information regarding a
 * particular employee. It does not have any methods except getters/setters.
 * 
 */
/**
 * @author Eason
 *
 */
public abstract class Employee {

	public enum EmploymentType {
		Salaried, Hourly
	};

	public static class FamilyNameAscComparator implements Comparator<Employee> {
		@Override
		public int compare(Employee o1, Employee o2) {
			return o1.name.getLastName().compareToIgnoreCase(
					o2.name.getLastName());
		}

	}

	public static class Name {
		private final String firstName, lastName;

		public Name(String firstName, String lastName) {
			this.firstName = firstName;
			this.lastName = lastName;
		}

		public String getFirstName() {
			return firstName;
		}

		public String getLastName() {
			return lastName;
		}
	}

	/**
	 * @param name
	 * @param tid
	 * @param ytdStart
	 * @param dateStart
	 * @param dateEnd
	 * @param hours
	 * @param weekDeduction
	 * @param rate
	 */
	public Employee(Name name, int tid, double ytdStart, Date dateStart,
			Date dateEnd, double hours, double weekDeduction, double rate) {
		this.tid = tid;
		this.name = name;
		// this.employment = employment;
		this.ytdStart = ytdStart;
		this.dateStart = dateStart;
		this.dateEnd = dateEnd;
		this.hours = hours;
		this.weekDeduction = weekDeduction;
		this.rate = rate;
	}

	public void completeFields() {
		setGross();
		setTax();
		setWeekNett();
		setYTD();
	}

	/**
	 * sets annualGross and weekGross fields to correct values
	 */
	public abstract void setGross();

	/**
	 * sets annualTax and weekTax fields to correct values
	 */
	public abstract void setTax();

	public void setWeekNett() {
		weekNett = weekGross - weekTax - weekDeduction;
	}
	
	public void setYTD(){
		ytdEnd = ytdStart + weekGross;
	}

	/**
	 * @param gross
	 *            - the annual gross. has to be positive.
	 * @return the annual PAYE tax
	 */
	public static double calcPAYE(double gross) {
		gross = Math.floor(gross);
		if (gross > TAX_INCOME_THRESHOLDS[0]) {
			return (gross - TAX_INCOME_THRESHOLDS[0]) * TAX_RATES[0]
					+ calcPAYE(TAX_INCOME_THRESHOLDS[0]);
		} else if (gross <= TAX_INCOME_THRESHOLDS[0]
				&& gross > TAX_INCOME_THRESHOLDS[1]) {
			return (gross - TAX_INCOME_THRESHOLDS[1]) * TAX_RATES[1]
					+ calcPAYE(TAX_INCOME_THRESHOLDS[1]);
		} else if (gross <= TAX_INCOME_THRESHOLDS[1]
				&& gross > TAX_INCOME_THRESHOLDS[2]) {
			return (gross - TAX_INCOME_THRESHOLDS[2]) * TAX_RATES[2]
					+ calcPAYE(TAX_INCOME_THRESHOLDS[2]);
		} else if (gross <= TAX_INCOME_THRESHOLDS[2] && gross > 0) {
			return gross * TAX_RATES[3];
		} else {
			throw new RuntimeException("annual gross should be positive");
		}
	}

	private static final double[] TAX_RATES = { 0.33, 0.3, 0.175, 0.105 },
			TAX_INCOME_THRESHOLDS = { 70000, 48000, 14000 };

	private final int tid;
	private final Name name;
	
	private Date dateStart, dateEnd;
	private double ytdStart;

	private double weekDeduction;

	private double weekNett;

	protected double ytdEnd;

	protected double hours;

	protected double rate;

	protected double annualGross;

	protected double annualTax;

	protected double weekGross;

	protected double weekTax;

	public double getWeekGross() {
		return weekGross;
	}

	public double getWeekTax() {
		return weekTax;
	}

	public double getWeekDeduction() {
		return weekDeduction;
	}

	public Name getName() {
		return name;
	}

	public int getTid() {
		return tid;
	}

	public Date getDateStart() {
		return dateStart;
	}

	public Date getDateEnd() {
		return dateEnd;
	}

	public double getWeekNett() {
		return weekNett;
	}

	public double getYtdEnd() {
		return ytdEnd;
	}

}