package com.tx;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class UserService {

    @Autowired
    private UserDao userDao;

    @Transactional(rollbackFor = Exception.class)
    public void insertUser() {
        userDao.insert();
        System.out.println("插入完成");
        //抛出异常
        int i = 10/0;
    }
}
