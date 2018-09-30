package com.jim.spring.boot.graphql.service;

import com.jim.spring.boot.graphql.bean.User;
import org.springframework.stereotype.Service;

/**
 * @author jim lin
 * 2018/9/30.
 */
@Service
public class UserService {

    public User getUser(String userName){
        User user = new User();
        user.setUserName("hello");
        user.setPhone("18222221111");
        user.setUserID("1");
        User user2 = new User();
        user2.setUserName("not");
        user2.setPhone("18222221111");
        user2.setUserID("1");

        if ("hello".equals(userName)){
            return user;
        }
        return user2;
    }
}
