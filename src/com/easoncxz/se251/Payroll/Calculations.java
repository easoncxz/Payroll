package com.easoncxz.se251.Payroll;

/**
 * This class consists of static members. It is created for code organization
 * purpose.
 * 
 */
public class Calculations {

	static final double[] TAX_RATES = { 0.33, 0.3, 0.175, 0.105 },
			TAX_INCOME_THRESHOLDS = { 70000, 48000, 14000 },
			HOURLY_MULTIPLIERS = {2,1.5,1},
			HOURLY_THRESHOLDS = {43,40};

	static double calcTaxAnnual(double gross) {
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

	static double calcWeeklyFromHours(double rate, double hours) {
		// 'magic' numbers below could be recorded as constants instead
		if (hours > 43) {
			return (hours - 43) * rate * 2 + calcWeeklyFromHours(rate, 43);
		} else if (hours <= 43 & hours > 40) {
			return (hours - 40) * rate * 1.5 + calcWeeklyFromHours(rate, 40);
		} else if (hours <= 40 & hours > 0) {
			return hours * rate;
		} else {
			// shouldn't be reachable
			return -0.618; // random value
		}
	}

}
