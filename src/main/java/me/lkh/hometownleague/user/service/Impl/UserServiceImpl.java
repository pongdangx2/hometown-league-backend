package me.lkh.hometownleague.user.service.Impl;

import me.lkh.hometownleague.user.domain.User;
import me.lkh.hometownleague.user.service.UserService;
import me.lkh.hometownleague.user.service.dao.UserDao;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl implements UserService {

    private final UserDao userDao;

    public UserServiceImpl(UserDao userDao) {
        this.userDao = userDao;
    }

    @Override
    public int insertUser(User user) {
        int result = userDao.insertUser(user);
        return result;
    }
}
