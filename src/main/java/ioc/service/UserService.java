package ioc.service;

import ioc.annotation.BService;
import ioc.model.User;

/**
 * @Description
 * @Author bin
 * @Create 2017/3/3
 **/
@BService
public class UserService {

    public User getUser(){
        User user = new User();
        user.setId(1L);
        user.setName("bin");
        user.setPwd("123456");
        return user;
    }
}
