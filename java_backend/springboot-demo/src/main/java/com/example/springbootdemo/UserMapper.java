package com.example.springbootdemo;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Update;
import org.apache.ibatis.annotations.Delete;

import java.util.List;

@Mapper
public interface UserMapper {
    @Select("select u.id, u.name, u.age, u.status, u.gender, u.dept_id, d.name as dept_name " +
            "from user u left join dept d on u.dept_id = d.id where u.id = #{id}")
    User findById(Integer id);

    @Select("select u.id, u.name, u.age, u.status, u.gender, u.dept_id, d.name as dept_name " +
            "from user u left join dept d on u.dept_id = d.id")
    List<User> findAll();

    @Insert("insert into user(name,age,status,gender,dept_id) values(#{name},#{age},#{status},#{gender},#{deptId})")
    void insert(User user);

    @Update("Update user set name=#{name}, age=#{age},status=#{status}, gender=#{gender}, dept_id=#{deptId} where id=#{id}")
    int update(User user);

    @Delete("delete from user where id=#{id}")
    int delete(Integer id);

}
