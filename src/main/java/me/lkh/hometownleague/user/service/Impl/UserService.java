package me.lkh.hometownleague.user.service.Impl;

import me.lkh.hometownleague.user.domain.User;
import me.lkh.hometownleague.user.service.dao.UserDao;
import org.springframework.stereotype.Service;

@Service
public class UserService{

    private final UserDao userDao;

    public UserService(UserDao userDao) {
        this.userDao = userDao;
    }

    public User getUserById(String id) {
        return userDao.selectUserById(id);
    }
}
