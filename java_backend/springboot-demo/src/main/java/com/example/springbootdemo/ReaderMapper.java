package com.example.springbootdemo;

import org.apache.ibatis.annotations.*;

import java.util.List;

@Mapper
public interface ReaderMapper {
    @Select("select * from reader where id = #{id}")
    Reader findById(Integer id);

    @Select("select * from reader")
    List<Reader> findAll();

    @Insert("insert into reader(name, gender, phone) values (#{name}, #{gender}, #{phone})")
    void insert(Reader reader);

    @Update("update reader set name = #{name}, gender = #{gender}, phone = #{phone} where id = #{id}")
    int update(Reader reader);

    @Delete("delete from reader where id = #{id}")
    int delete(Integer id);

}
