package me.lkh.hometownleague.image.service;

import me.lkh.hometownleague.common.exception.image.CannotSaveFileException;
import me.lkh.hometownleague.team.domain.Team;
import me.lkh.hometownleague.team.repository.TeamRepository;
import me.lkh.hometownleague.team.service.TeamService;
import me.lkh.hometownleague.user.repository.UserRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;

@Service
public class ImageService {

    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    @Value("${image.team.path}")
    private String imageTeamPath;

    @Value("${image.user.path}")
    private String imageUserPath;

    private final TeamService teamService;

    private final TeamRepository teamRepository;

    private final UserRepository userRepository;

    public ImageService(TeamService teamService, TeamRepository teamRepository, UserRepository userRepository) {
        this.teamService = teamService;
        this.teamRepository = teamRepository;
        this.userRepository = userRepository;
    }

    /**
     * 팀 cI 저장 및 변경
     * @param multipartFile
     * @param teamId
     * @param userId
     */
    public void uploadTeamCi(MultipartFile multipartFile, int teamId, String userId) {

        // CI 수정은 팀 소유주만 가능해야함.
        Team selectedTeam = teamService.isOwner(userId, teamId);

        // 1. 디스크에 파일 저장
        String ciPath;
        try {
            ciPath = saveFile(multipartFile, imageTeamPath, String.valueOf(teamId));
        } catch(Exception exception){
            logger.error("디스크 파일저장 실패");
            exception.printStackTrace();
            throw new CannotSaveFileException("failed to write file on disk.");
        }

        // 2. DB에 파일명 업데이트
        if(1 != teamRepository.updateTeamCiPath(String.valueOf(teamId), ciPath)){
            logger.error("DB 업데이트 실패");
            throw new CannotSaveFileException("failed to update team_info table");
        }
    }

    /**
     * 사용자 CI 저장 및 변경
     * @param multipartFile
     * @param userId
     */
    public void uploadUserCi(MultipartFile multipartFile, String userId) {

        // 1. 디스크에 파일 저장
        String ciPath;
        try {
            ciPath = saveFile(multipartFile, imageUserPath, userId);
        } catch(Exception exception){
            exception.printStackTrace();
            throw new CannotSaveFileException("failed to write file on disk.");
        }

        // 2. DB에 파일명 업데이트
        if(1 != userRepository.updateUserCiPath(userId, ciPath)){
            throw new CannotSaveFileException("failed to update user_info table");
        }
    }

    /**
     * 파일을 특정 경로에 저장
     * @param multipartFile 저장할 파일
     * @param path 경로               ex) /app/files/team
     * @param fileName 저장할 파일명    ex) testFileName
     * @return 파일이름                ex) testFileName.jpg
     * @throws IOException
     */
    private String saveFile(MultipartFile multipartFile, String path, String fileName) throws IOException {
        String originalFileName = multipartFile.getOriginalFilename();
        String fileExtension = originalFileName.substring(originalFileName.lastIndexOf(".") + 1);
        String fileFullName = fileName + "."  +fileExtension;

        File file = new File(path, fileFullName);
        if(file.exists()){
            file.mkdirs();
        }

        multipartFile.transferTo(file);
        String fullPath = path + "/" + fileFullName;

        logger.debug("filePath: " + path);
        logger.debug("fileName: " + fileFullName);
        logger.debug("fileFullPath: " + fullPath);
        logger.debug("fileSize: " +  multipartFile.getSize());

        return fileFullName;
    }

}
