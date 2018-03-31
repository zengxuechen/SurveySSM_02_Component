package com.atguigu.survey.component.dao.i;

import java.util.List;

import com.atguigu.survey.entities.guest.Employee;

public interface EmployeeMapper {
	
	int deleteByPrimaryKey(Integer empId);

	int insert(Employee record);

	Employee selectByPrimaryKey(Integer empId);

	List<Employee> selectAll();

	int updateByPrimaryKey(Employee record);

}