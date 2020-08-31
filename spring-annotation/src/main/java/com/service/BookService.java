package com.service;

import com.dao.BookDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

@Service
public class BookService {

    //@Qualifier("bookDao")
    //@Autowired(required = false)
    @Resource(name = "bookDao2")
    private BookDao bookDao; //换个名字就会报错

    @Override
    public String toString() {
        return "BookService{" +
          "bookDao=" + bookDao +
          '}';
    }
}
