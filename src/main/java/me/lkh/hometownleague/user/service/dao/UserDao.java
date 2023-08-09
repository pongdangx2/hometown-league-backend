package me.lkh.hometownleague.user.service.dao;

import me.lkh.hometownleague.user.domain.User;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

@Mapper
public interface UserDao {

    @Insert("INSERT INTO user_info VALUES(#{user.id}, #{user.name}, #{user.password}, #{user.useYn}, NOW(), NOW())")
    int insertUser(@Param("user")User user);

}
