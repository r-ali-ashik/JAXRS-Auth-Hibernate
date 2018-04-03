package com.aliashik.service.impl;

import com.aliashik.dao.UserDao;
import com.aliashik.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import org.apache.log4j.Logger;

@Service
public class UserServiceImpl implements UserService{

    @Autowired
    private Logger logger;

    @Autowired
    private UserDao userDao;

    @Override
    public Map getUserByUsername(String username) {
        return userDao.getUserByUsername(username);
    }

    @Override
    public List<String> getUserRolesByUserID(Integer userId) {
        return userDao.getUserRolesByUserID(userId);
    }
}
