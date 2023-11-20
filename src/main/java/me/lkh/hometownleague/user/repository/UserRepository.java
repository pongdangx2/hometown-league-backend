package me.lkh.hometownleague.user.repository;

import me.lkh.hometownleague.user.domain.JoinDuplicateCheck;
import me.lkh.hometownleague.user.domain.User;
import me.lkh.hometownleague.user.domain.UserDupCheck;
import me.lkh.hometownleague.user.domain.UserTeam;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface UserRepository {

    User selectUserById(String id);

    UserDupCheck selectUserDupCheck(User user);

    int insertUser(User user);

    String selectIsDuplicate(JoinDuplicateCheck joinDuplicateCheck);

    int updateUser(User user);

    List<UserTeam> selectTeamOfUser(String userId);

    int updateUserCiPath(String userId, String ciPath);
}
