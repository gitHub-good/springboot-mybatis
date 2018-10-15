package com.xu.springbootmybatis.mapper;

import com.xu.springbootmybatis.bean.Department;
import org.apache.ibatis.annotations.*;

import java.util.List;


//指定这是一个操作数据库的mapper
//@Mapper
public interface DepartmentMapper {

    @Select("select * from department where id=#{id}")
    public Department getDeptById(Integer id);

    @Select("select * from department")
    List<Department> listDepartment();

    @Delete("delete from department where id=#{id}")
    public int deleteDeptById(Integer id);

    @Options(useGeneratedKeys = true,keyProperty = "id")/*设置自增主键*/
    @Insert("insert into department(departmentName) values(#{departmentName})")
    public int insertDept(Department department);

    @Update("update department set departmentName=#{departmentName} where id=#{id}")
    public int updateDept(Department department);

    @Select("select * from department where departmentName=#{departmentName}")
    Department getDepartByDepartmentName(String departmentName);
}
