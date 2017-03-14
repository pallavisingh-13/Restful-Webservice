package com.user.service;

import com.user.entity.User;
import com.user.exception.UserException;
import com.user.provider.UserProvider;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;

import java.util.Date;
import java.util.List;

@Component
@Slf4j
public class UserServiceImpl implements UserService {

    @Autowired
    UserProvider userProvider;

    @Override
    public List<User> getUserInfo(String userIds) {
        log.debug("UserServiceImpl - getUserInfo method enters");
        log.info("userIds To Get User Info" + userIds);
        return userProvider.getUserInfo(userIds);
    }

    @Override
    public User createUser(User user) {
        log.info("user to create [{}]" + user);
        List<User> users = getUserInfo(user.getId());
        if (users.size() > 0) {
            throw new UserException("User with this id already exists");
        }
        return userProvider.createOrUpdateUser(user);
    }

    @Override
    public User modifyUser(User user) {
        log.info("user to create [{}]" + user);
        List<User> users = getUserInfo(user.getId());
        if (users.size() == 0) {
            throw new UserException("User with this id was not found");
        }
        return userProvider.createOrUpdateUser(user);
    }

    @Override
    public void removeUser(String id) {
        log.info("user to create [{}]" + id);
        List<User> users = getUserInfo(id);
        if (users.size() == 0) {
            throw new UserException("User with this id was not found");
        }
        userProvider.removeUser(id);
    }

}
