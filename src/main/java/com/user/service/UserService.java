package com.user.service;

import com.user.entity.User;
import org.springframework.validation.Errors;

import java.util.List;

public interface UserService {

    List<User> getUserInfo(String userIds);
    User createUser(User user);
    void removeUser(String id);
    User modifyUser(User user);
}
