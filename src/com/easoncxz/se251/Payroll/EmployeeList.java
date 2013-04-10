package com.easoncxz.se251.Payroll;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;

public class EmployeeList implements Iterable<Employee>{
	private List<Employee> list = new ArrayList<Employee>();

	@Override
	public Iterator<Employee> iterator() {
		return list.iterator();
	}
	
	void addEmployee(Employee employee){
		list.add(employee);
	}
	
	public void sortByLastNameAsc(){
		Collections.sort(list,new Employee.FamilyNameAscComparator());
	}
	
}
