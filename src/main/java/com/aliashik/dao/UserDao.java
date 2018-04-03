package com.aliashik.dao;

import java.util.List;
import java.util.Map;

public interface UserDao {
    Map getUserByUsername(String username);
    List<String> getUserRolesByUserID(Integer userId);
}
