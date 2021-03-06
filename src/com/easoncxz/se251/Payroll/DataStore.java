package com.easoncxz.se251.Payroll;

/**
 * This class is currently just a wrapper around EmployeeList.
 * <p>
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

	/**
	 * set all employees' calculation-result fields according to their
	 * input-given fields.
	 */
	public void completeFields() {
		for (Employee employee : employeeList) {
			employee.completeFields();
		}
	}
}
