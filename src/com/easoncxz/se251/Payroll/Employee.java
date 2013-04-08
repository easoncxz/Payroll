package com.easoncxz.se251.Payroll;

import java.util.Date;

public class Employee {
	private final int tid;
	private final Name name;
	private final EmploymentType employment;
	private float ytdStart, ytdEnd;
	private Date dateStart, dateEnd;
	private float hours;
	private float deduction;
	private float rate;

	// public Employee() {
	// this.name = new Name("John", "Doe");
	// this.employment = EmploymentType.Salaried;
	// this.tid = 0;
	// }

	// public Employee(Name name, EmploymentType employment, int tid) {
	public Employee(Name name, EmploymentType employment, int tid,
			float ytdStart, Date dateStart, Date dateEnd,
			float hours, float deduction, float rate) {
		this.name = name;
		this.employment = employment;
		this.tid = tid;
		this.ytdStart = ytdStart;
		this.ytdEnd = ytdEnd;
		this.dateStart = dateStart;
		this.dateEnd = dateEnd;
		this.hours = hours;
		this.deduction = deduction;
		this.rate = rate;
	}

	// generated getters & setters below
	public float getYtdStart() {
		return ytdStart;
	}

	public void setYtdStart(float ytdStart) {
		this.ytdStart = ytdStart;
	}

	public float getYtdEnd() {
		return ytdEnd;
	}

	public void setYtdEnd(float ytdEnd) {
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

	public float getHours() {
		return hours;
	}

	public void setHours(float hours) {
		this.hours = hours;
	}

	public float getDeduction() {
		return deduction;
	}

	public void setDeduction(float deduction) {
		this.deduction = deduction;
	}

	public float getRate() {
		return rate;
	}

	public void setRate(float rate) {
		this.rate = rate;
	}

	public Name getName() {
		return name;
	}

	public EmploymentType getEmployment() {
		return employment;
	}

	public int getTid() {
		return tid;
	}

}
