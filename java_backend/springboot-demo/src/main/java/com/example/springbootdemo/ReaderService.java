package com.example.springbootdemo;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ReaderService {
    @Autowired
    private ReaderMapper readerMapper;

    public Reader findById(Integer id){return readerMapper.findById(id);}
    public List<Reader> findAll(){return readerMapper.findAll();}

    public void insert(Reader reader){readerMapper.insert(reader);}

    public int update(Reader reader){return readerMapper.update(reader);}

    public int delete(Integer id){return readerMapper.delete(id);}

}
