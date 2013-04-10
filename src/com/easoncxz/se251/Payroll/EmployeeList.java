package com.easoncxz.se251.Payroll;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;

/**
 * This class, which is currently a wrapper around the java List<T>, is created
 * to hide away the implementation details£¬ e.g. the data structure, of the list
 * abstraction.
 * 
 */
public class EmployeeList implements Iterable<Employee> {
	private List<Employee> list = new ArrayList<Employee>();

	@Override
	public Iterator<Employee> iterator() {
		return list.iterator();
	}

	void addEmployee(Employee employee) {
		list.add(employee);
	}

	public void sortByLastNameAsc() {
		Collections.sort(list, new Employee.FamilyNameAscComparator());
	}

}
