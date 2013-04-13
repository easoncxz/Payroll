package com.easoncxz.se251.Payroll;

/**
 * This class is currently just a wrapper around EmployeeList.
 * 
 * It could be changed later to interact with a database etc, build an
 * EmployeeList object on-the-fly, and return this EmployeeList object.
 */
public class DataStore {
	private EmployeeList employeeList = new EmployeeList();

	EmployeeList getEmployeeList() {
		return this.employeeList;
	}

	void saveEmployeeList(EmployeeList employeeList) {
		this.employeeList = employeeList;
	}

	public void completeFields() {
		for (Employee employee : employeeList) {
			// in the future, this if-statement could/should be replaced by
			// using type relations
			if (employee.getEmployment() == Employee.EmploymentType.Salaried) {
				employee.setAnnualGross(employee.getRate());
				employee.setWeekGross(employee.getAnnualGross() / 52);
				employee.setAnnualTax(Calculations.calcTaxAnnual(employee
						.getAnnualGross()));
				employee.setWeekTax(employee.getAnnualTax() / 52);
			} else if (employee.getEmployment() == Employee.EmploymentType.Hourly) {
				employee.setWeekGross(Calculations.calcWeeklyFromHours(
						employee.getRate(), employee.getHours()));
				employee.setAnnualGross(employee.getWeekGross() * 52);
				employee.setAnnualTax(Calculations.calcTaxAnnual(employee
						.getAnnualGross()));
				employee.setWeekTax(employee.getAnnualTax() / 52);
			} else {
				// shouldn't be reached
				throw new RuntimeException("invalid employment type");
			}
			employee.setYtdEnd(employee.getYtdStart() + employee.getWeekGross());
		}
	}
}
