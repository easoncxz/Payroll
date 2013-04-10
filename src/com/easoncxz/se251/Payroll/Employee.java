package com.easoncxz.se251.Payroll;

import java.util.Date;

public class Employee {
	public enum EmploymentType {
		Salaried, Hourly
	};

	private final int tid;
	private final Name name;
	private final EmploymentType employment;
	private double ytdStart, ytdEnd;
	private Date dateStart, dateEnd;
	private double hours;
	private double deduction;
	private double rate;

	private double annualGross, annualTax, weekGross, weekTax, nett;

	// public Employee() {
	// this.name = new Name("John", "Doe");
	// this.employment = EmploymentType.Salaried;
	// this.tid = 0;
	// }

	// public Employee(Name name, EmploymentType employment, int tid) {
	public Employee(Name name, EmploymentType employment, int tid,
			double ytdStart, Date dateStart, Date dateEnd, double hours,
			double deduction, double rate) {
		this.tid = tid;
		this.name = name;
		this.employment = employment;
		this.ytdStart = ytdStart;
		this.dateStart = dateStart;
		this.dateEnd = dateEnd;
		this.hours = hours;
		this.deduction = deduction;
		this.rate = rate;
	}


	// generated getters & setters below
	public double getYtdStart() {
		return ytdStart;
	}

	public void setYtdStart(double ytdStart) {
		this.ytdStart = ytdStart;
	}

	public double getYtdEnd() {
		return ytdEnd;
	}

	public void setYtdEnd(double ytdEnd) {
		this.ytdEnd = ytdEnd;
	}

	public Date getDateStart() {
		return dateStart;
	}

	public void setDateStart(Date dateStart) {
		this.dateStart = dateStart;
	}

	public Date getDateEnd() {
		return dateEnd;
	}

	public void setDateEnd(Date dateEnd) {
		this.dateEnd = dateEnd;
	}

	public double getHours() {
		return hours;
	}

	public void setHours(double hours) {
		this.hours = hours;
	}

	public double getDeduction() {
		return deduction;
	}

	public void setDeduction(double deduction) {
		this.deduction = deduction;
	}

	public double getRate() {
		return rate;
	}

	public void setRate(double rate) {
		this.rate = rate;
	}

	public double getAnnualGross() {
		return annualGross;
	}

	public void setAnnualGross(double annualGross) {
		this.annualGross = annualGross;
	}

	public double getAnnualTax() {
		return annualTax;
	}

	public void setAnnualTax(double annualTax) {
		this.annualTax = annualTax;
	}

	public double getWeekGross() {
		return weekGross;
	}

	public void setWeekGross(double weekGross) {
		this.weekGross = weekGross;
	}

	public double getWeekTax() {
		return weekTax;
	}

	public void setWeekTax(double weekTax) {
		this.weekTax = weekTax;
	}

	public int getTid() {
		return tid;
	}

	public Name getName() {
		return name;
	}

	public EmploymentType getEmployment() {
		return employment;
	}


	public double getNett() {
		return nett;
	}


	public void setNett(double nett) {
		this.nett = nett;
	}
	
}