package com.aliashik.service;

import java.util.List;
import java.util.Map;

public interface UserService {
    Map getUserByUsername(String username);
    List<String> getUserRolesByUserID(Integer userId);
}
