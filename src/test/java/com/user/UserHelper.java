package com.user;

import com.user.entity.User;

import java.util.ArrayList;
import java.util.List;

public class UserHelper {

    public static User getMockUser(){
        User user = new User();
        user.setId("10");
        user.setContent("abc");
        return user;
    }
    public static List<User> getMockUserList(){
        List<User> userList = new ArrayList<>();
        User user = new User();
        user.setId("10");
        user.setContent("abc");
        userList.add(user);
        return userList;
    }
}
