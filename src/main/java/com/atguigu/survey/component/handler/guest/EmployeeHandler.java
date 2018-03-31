package com.atguigu.survey.component.handler.guest;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.atguigu.survey.component.service.i.EmployeeService;
import com.atguigu.survey.entities.guest.Employee;

@Controller
public class EmployeeHandler {

	@Autowired
	private EmployeeService employeeService ;
	
	public EmployeeHandler(){		
		//System.out.println("EmployeeHandler被创建了");		
	}
	
	@RequestMapping("/guest/employee/showList")
	public String showList(Map<String, Object> map){
		List<Employee> list = employeeService.queryList();
		map.put("empList", list);
		return "employee/showList";
	}	
	
}
