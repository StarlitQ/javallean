package com.example.springbootdemo;

import org.apache.ibatis.annotations.*;

import java.util.List;

@Mapper
public interface BookMapper {
    @Select("select * from book where id = #{id}")
    Book findById(Integer id);

    @Select("select * from book")
    List<Book> findAll();

    @Insert("insert into book(title, author, status) values (#{title}, #{author}, #{status})")
    void insert(Book book);

    @Update("update book set title = #{title}, author = #{author}, status = #{status} where id = #{id}")
    int update(Book book);

    @Delete("delete from book where id = #{id}")
    int delete(Integer id);

    @Update("update book set status = #{status} where id = #{id}")
    int updateStatus(@Param("id") Integer id, @Param("status") String status);

}
