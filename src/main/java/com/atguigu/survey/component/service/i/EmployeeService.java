package com.atguigu.survey.component.service.i;

import java.io.FileNotFoundException;
import java.util.List;

import com.atguigu.survey.entities.guest.Employee;

public interface EmployeeService {
	Employee getEntity(Integer entityId);

	void updateEntity(Employee t);

	void removeEntityById(Integer entityId);

	void saveEntity(Employee t);
	
	//-----------------------------

	List<Employee> queryList();
}
