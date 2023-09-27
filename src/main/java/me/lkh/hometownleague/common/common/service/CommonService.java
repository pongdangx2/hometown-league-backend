package me.lkh.hometownleague.common.common.service;

import me.lkh.hometownleague.common.common.repository.CommonRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service
public class CommonService {

    private final CommonRepository commonRepository;

    public CommonService(CommonRepository commonRepository) {
        this.commonRepository = commonRepository;
    }

    public List<Map<String, String>> selectCommonCode(String groupId){
        return commonRepository.selectCommonCode(groupId);
    }
}
