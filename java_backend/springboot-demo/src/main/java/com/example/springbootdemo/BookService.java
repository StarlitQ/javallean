package com.example.springbootdemo;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class BookService {
    @Autowired
    private BookMapper bookMapper;

    public Book findById(Integer id){return bookMapper.findById(id);}
    public List<Book> findAll(){return bookMapper.findAll();}

    public void insert(Book book){bookMapper.insert(book);}

    public int update(Book book){return bookMapper.update(book);}

    public int delete(Integer id){return bookMapper.delete(id);}

}
