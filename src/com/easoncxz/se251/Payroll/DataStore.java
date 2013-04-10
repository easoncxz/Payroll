package com.easoncxz.se251.Payroll;


public class DataStore {
	private EmployeeList employeeList = new EmployeeList();
	
	EmployeeList getEmployeeList(){
		return this.employeeList;
	}
	
	void saveEmployeeList(EmployeeList employeeList) {
		this.employeeList = employeeList;
	}
}
