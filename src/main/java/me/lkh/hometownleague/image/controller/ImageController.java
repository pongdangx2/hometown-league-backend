package me.lkh.hometownleague.image.controller;

import jakarta.servlet.http.HttpServletRequest;
import me.lkh.hometownleague.common.exception.ErrorCode;
import me.lkh.hometownleague.common.response.CommonResponse;
import me.lkh.hometownleague.common.util.SessionUtil;
import me.lkh.hometownleague.image.service.ImageService;
import me.lkh.hometownleague.session.domain.AuthCheck;
import me.lkh.hometownleague.session.domain.UserSession;
import me.lkh.hometownleague.session.service.SessionService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.InputStreamResource;
import org.springframework.core.io.Resource;
import org.springframework.http.ContentDisposition;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

@RestController
@RequestMapping("/image")
public class ImageController {

    @Value("${image.team.path}")
    private String imageTeamPath;

    @Value("${image.user.path}")
    private String imageUserPath;

    private final ImageService imageService;

    private final SessionService sessionService;

    public ImageController(ImageService imageService, SessionService sessionService) {
        this.imageService = imageService;
        this.sessionService = sessionService;
    }

    @PatchMapping ("/team")
    @AuthCheck
    public CommonResponse uploadTeamCi(@RequestParam("imageFile")MultipartFile multipartFile, @RequestParam("id") int teamId, HttpServletRequest httpServletRequest) {

        UserSession userSession = sessionService.getUserSession(SessionUtil.getSessionIdFromRequest(httpServletRequest).get());

        imageService.uploadTeamCi(multipartFile, teamId, userSession.getUserId());
        return CommonResponse.withEmptyData(ErrorCode.SUCCESS);
    }

    @PatchMapping("/user")
    @AuthCheck
    public CommonResponse uploadUserCi(@RequestParam("imageFile")MultipartFile multipartFile, HttpServletRequest httpServletRequest) {

        UserSession userSession = sessionService.getUserSession(SessionUtil.getSessionIdFromRequest(httpServletRequest).get());

        imageService.uploadUserCi(multipartFile, userSession.getUserId());
        return CommonResponse.withEmptyData(ErrorCode.SUCCESS);
    }

    /**
     * 이미지 다운로드
     * @param name 파일명
     * @param type 다운로드할 이미지의 타입 (user, team)
     * @return
     */
    @GetMapping("/team")
    public ResponseEntity<Object> downloadTeamCi(@RequestParam("name")String name, @RequestParam("type") String type) {

        String path;
        if("user".equals(type)){
            path = imageUserPath + "/" + name;
        } else if("team".equals(type)){
            path = imageTeamPath + "/" + name;
        } else {
            return new ResponseEntity<>(null, HttpStatus.CONFLICT);
        }
        try {
            Path filePath = Paths.get(path);
            Resource resource = new InputStreamResource(Files.newInputStream(filePath));

            File file = new File(path);

            HttpHeaders headers = new HttpHeaders();
            // 다운로드 되거나 로컬에 저장되는 용도로 쓰이는지를 알려주는 헤더
            headers.setContentDisposition(ContentDisposition.builder("attachment").filename(file.getName()).build());

            return new ResponseEntity<>(resource, headers, HttpStatus.OK);
        } catch(Exception e) {
            return new ResponseEntity<>(null, HttpStatus.CONFLICT);
        }
    }
}

