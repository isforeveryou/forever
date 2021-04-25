package com.forever.test.junit.demo;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author WJX
 * @date 2021/4/25 15:03
 */
@Service
public class AddServiceDemo {

    @Autowired
    private DaoDemo dao;

    @Autowired
    private ServiceDemo service;

    public int add() {
        return dao.select() + service.doSelect();
    }

}
