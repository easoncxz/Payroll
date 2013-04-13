package com.easoncxz.se251.Payroll;

import java.util.Date;

public class EmployeeSalaried extends Employee {

	public EmployeeSalaried(Name name, int tid, double ytdStart,
			Date dateStart, Date dateEnd, double hours, double deduction,
			double rate) {
		super(name, tid, ytdStart, dateStart, dateEnd, hours, deduction, rate);
	}

	@Override
	public void setGross() {
		annualGross = rate;
		weekGross = annualGross / 52;
	}

	@Override
	public void setTax() {
		annualTax = calcPAYE(annualGross);
		weekTax = annualTax / 52;
	}

}
