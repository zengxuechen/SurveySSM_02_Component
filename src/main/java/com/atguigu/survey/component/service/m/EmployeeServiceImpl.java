package com.atguigu.survey.component.service.m;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.atguigu.survey.component.dao.i.EmployeeMapper;
import com.atguigu.survey.component.service.i.EmployeeService;
import com.atguigu.survey.entities.guest.Employee;

@Service
public class EmployeeServiceImpl  implements EmployeeService {

	public EmployeeServiceImpl(){
		//System.out.println("EmployeeServiceImpl被创建了");
	}
	
	@Autowired
	private EmployeeMapper employeeMapper ;
	
	public Employee getEntity(Integer entityId) {
		return employeeMapper.selectByPrimaryKey(entityId);
	}

	public void updateEntity(Employee t) {
		employeeMapper.updateByPrimaryKey(t);
	}


	public void removeEntityById(Integer entityId) {
		employeeMapper.deleteByPrimaryKey(entityId);
	}

	public void saveEntity(Employee t) {
		employeeMapper.insert(t);
	}

	public List<Employee> queryList() {		
		return employeeMapper.selectAll();
	}

}
