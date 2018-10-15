package com.xu.springbootmybatis.controller;


import com.xu.springbootmybatis.bean.Department;
import com.xu.springbootmybatis.bean.Employee;
import com.xu.springbootmybatis.mapper.EmployeeMapper;
import com.xu.springbootmybatis.service.DeptService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class DeptController {

    @Autowired
    DeptService deptService;
    @Autowired
    EmployeeMapper employeeMapper;

    @GetMapping("/dept/{id}")
    public Department getDepartment(@PathVariable("id") Integer id){
        return deptService.getDepartById(id);
    }

    @GetMapping("/dept/name/{name}")
    public Department getEmpByName(@PathVariable("name") String name){
        return deptService.getDepartByLastName(name);
    }

    @GetMapping("/dept")
    public Department insertDept(Department department){
        deptService.addDepart(department);
        return department;
    }

    @GetMapping("/listDepart")
    public List<Department>  listDepart(Department department){
        return deptService.listDepart();
    }


    @GetMapping("/emp/{id}")
    public Employee getEmp(@PathVariable("id") Integer id){
       return employeeMapper.getEmpById(id);
    }

}
