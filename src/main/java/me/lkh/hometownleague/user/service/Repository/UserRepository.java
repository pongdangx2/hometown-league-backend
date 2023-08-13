package me.lkh.hometownleague.user.service.Repository;

import me.lkh.hometownleague.user.domain.User;
import me.lkh.hometownleague.user.domain.UserDupCheck;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface UserRepository {

    User selectUserById(String id);

    UserDupCheck selectUserDupCheck(User user);

    int insertUser(User user);
}
