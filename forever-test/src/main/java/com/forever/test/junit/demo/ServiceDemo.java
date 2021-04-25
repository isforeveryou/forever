package com.forever.test.junit.demo;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author WJX
 * @date 2021/4/25 14:15
 */
@Service
public class ServiceDemo {

    @Autowired
    private DaoDemo daoDemo;

    public int doSelect() {
        return daoDemo.select();
    }

}
