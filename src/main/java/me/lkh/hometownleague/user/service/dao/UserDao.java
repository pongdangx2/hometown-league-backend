package me.lkh.hometownleague.user.service.dao;

import me.lkh.hometownleague.user.domain.User;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface UserDao {

    User selectUserById(String id);
}
