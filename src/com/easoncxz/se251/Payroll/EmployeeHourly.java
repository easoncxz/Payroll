package com.easoncxz.se251.Payroll;

import java.util.Date;

public class EmployeeHourly extends Employee {

	public EmployeeHourly(Name name, int tid, double ytdStart, Date dateStart,
			Date dateEnd, double hours, double deduction, double rate) {
		super(name, tid, ytdStart, dateStart, dateEnd, hours, deduction, rate);
	}

	/**
	 * @param rate
	 * @param hours
	 *            - this should be positive
	 * @return weekly gross income
	 */
	public static double calcWeeklyFromHours(double rate, double hours) {
		if (hours > HOURLY_THRESHOLDS[0]) {
			return (hours - HOURLY_THRESHOLDS[0]) * rate
					* HOURLY_MULTIPLIERS[0]
					+ calcWeeklyFromHours(rate, HOURLY_THRESHOLDS[0]);
		} else if (hours <= HOURLY_THRESHOLDS[0] & hours > HOURLY_THRESHOLDS[1]) {
			return (hours - HOURLY_THRESHOLDS[1]) * rate
					* HOURLY_MULTIPLIERS[1]
					+ calcWeeklyFromHours(rate, HOURLY_THRESHOLDS[1]);
		} else if (hours <= HOURLY_THRESHOLDS[1] & hours > 0) {
			return (hours * rate * HOURLY_MULTIPLIERS[2]);
		} else {
			throw new RuntimeException("hours should be positive");
		}
	}

	@Override
	public void setGross() {
		weekGross = calcWeeklyFromHours(rate, hours);
		annualGross = weekGross * 52;
	}

	@Override
	public void setTax() {
		annualTax = calcPAYE(annualGross);
		weekTax = annualTax / 52;
	}

	static final double[] HOURLY_MULTIPLIERS = { 2, 1.5, 1 },
			HOURLY_THRESHOLDS = { 43, 40 };
}
