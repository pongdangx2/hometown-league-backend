package me.lkh.hometownleague.matching.controller.docs;

import com.fasterxml.jackson.databind.ObjectMapper;
import me.lkh.hometownleague.common.exception.ErrorCode;
import me.lkh.hometownleague.common.interceptor.SessionInterceptor;
import me.lkh.hometownleague.common.response.CommonResponse;
import me.lkh.hometownleague.common.util.SessionUtil;
import me.lkh.hometownleague.matching.controller.MatchingController;
import me.lkh.hometownleague.matching.domain.MatchingListElement;
import me.lkh.hometownleague.matching.domain.MatchingResultReportRequest;
import me.lkh.hometownleague.matching.domain.response.*;
import me.lkh.hometownleague.matching.service.MatchingService;
import me.lkh.hometownleague.session.domain.UserSession;
import me.lkh.hometownleague.session.service.SessionService;
import me.lkh.hometownleague.team.domain.Team;
import me.lkh.hometownleague.user.domain.User;
import org.junit.Rule;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.restdocs.AutoConfigureRestDocs;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.restdocs.JUnitRestDocumentation;
import org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders;
import org.springframework.restdocs.operation.preprocess.Preprocessors;
import org.springframework.restdocs.payload.JsonFieldType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.doNothing;
import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.document;
import static org.springframework.restdocs.payload.PayloadDocumentation.*;
import static org.springframework.restdocs.payload.PayloadDocumentation.fieldWithPath;
import static org.springframework.restdocs.request.RequestDocumentation.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@AutoConfigureRestDocs(uriHost="218.232.175.4", uriPort = 49156)
@AutoConfigureMockMvc
@WebMvcTest(MatchingController.class)
public class MatchingControllerTest_MockMvc {

    @Rule
    public JUnitRestDocumentation restDocumentation = new JUnitRestDocumentation("target/generated-snippets");

    @SuppressWarnings("SpringJavaInjectionPointsAutowiringInspection")  // 인텔리제이문제로 Could not autowire. No beans of 'MockMvc' type found.  에러가 나와서 붙였음.
    @Autowired
    private MockMvc mockMvc;

    @SuppressWarnings("SpringJavaInjectionPointsAutowiringInspection")  // 인텔리제이문제로 Could not autowire. No beans of 'MockMvc' type found.  에러가 나와서 붙였음.
    @Autowired
    private ObjectMapper objectMapper;

    @MockBean
    private SessionService sessionService;

    @MockBean
    private MatchingService matchingService;

    @MockBean
    private SessionInterceptor sessionInterceptor;

    @DisplayName("매칭 요청")
    @Test
    void matchingRequest() throws Exception {

        // 세션 관련 Start ======================================================================
        given(sessionInterceptor.preHandle(any(), any(), any())).willReturn(true);

        String id = "testid@gmail.ccom";
        String name = "testname";
        String password = "testPassword";
        User user = new User(id, name, password);
        UserSession userSession = new UserSession("spring-session" + SessionUtil.getSessionId(user.getId())
                , user.getId()
                , user.getNickname());
        given(sessionService.getSession(any())).willReturn(userSession);
        given(sessionService.getUserSession(any())).willReturn(userSession);
        given(sessionInterceptor.preHandle(any(), any(), any())).willReturn(true);
        // 세션 관련 End ======================================================================

        Map<String, Object> requestBody = new HashMap<>();
        requestBody.put("teamId", 16);
        String requestContent = objectMapper.writeValueAsString(requestBody);
        String responseContent = objectMapper.writeValueAsString(CommonResponse.withEmptyData(ErrorCode.SUCCESS));

        ResultActions resultActions =  this.mockMvc.perform( RestDocumentationRequestBuilders.post("/matching/request")
                .header("cookie", "SESSION=" + userSession.getSessionId())
                .content(requestContent)
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
        );

        resultActions
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.responseCode").exists())
                .andExpect(jsonPath("$.responseCode.code").exists())
                .andExpect(jsonPath("$.responseCode.message").exists())
                .andExpect((content().json(responseContent)))
                .andDo(document("matching-request",
                        Preprocessors.preprocessRequest(Preprocessors.prettyPrint()),
                        Preprocessors.preprocessResponse(Preprocessors.prettyPrint()),
                        requestFields(
                                fieldWithPath("teamId").type(JsonFieldType.NUMBER).description("매칭을 요청할 팀의 ID")
                        ),
                        responseFields(
                                fieldWithPath("responseCode.code").type(JsonFieldType.STRING).description("응답결과 코드"),
                                fieldWithPath("responseCode.message").type(JsonFieldType.STRING).description("응답결과 메시지")
                        )
                ));
    }

    @DisplayName("매칭 목록 조회")
    @Test
    void selectMatching() throws Exception {

        // 세션 관련 Start ======================================================================
        given(sessionInterceptor.preHandle(any(), any(), any())).willReturn(true);

        String id = "testid@gmail.ccom";
        String name = "testname";
        String password = "testPassword";
        User user = new User(id, name, password);
        UserSession userSession = new UserSession("spring-session" + SessionUtil.getSessionId(user.getId())
                , user.getId()
                , user.getNickname());
        given(sessionService.getSession(any())).willReturn(userSession);
        given(sessionService.getUserSession(any())).willReturn(userSession);
        given(sessionInterceptor.preHandle(any(), any(), any())).willReturn(true);
        // 세션 관련 End ======================================================================

        List<MatchingListElement> responseList = new ArrayList<>();
        responseList.add(new MatchingListElement(2, 1, "N","test team",1500, 1, "test description", "W", "대기", "202310182033"));

        given(matchingService.selectMatching(any())).willReturn(responseList);

        String responseContent = objectMapper.writeValueAsString(new CommonResponse<>(responseList));

        ResultActions resultActions =  this.mockMvc.perform( RestDocumentationRequestBuilders.get("/matching/{userId}", id)
                .header("cookie", "SESSION=" + userSession.getSessionId())
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
        );

        resultActions
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.data").exists())
                .andExpect(jsonPath("$.responseCode").exists())
                .andExpect(jsonPath("$.responseCode.code").exists())
                .andExpect(jsonPath("$.responseCode.message").exists())
                .andExpect((content().json(responseContent)))
                .andDo(document("matching-select-request",
                        Preprocessors.preprocessRequest(Preprocessors.prettyPrint()),
                        Preprocessors.preprocessResponse(Preprocessors.prettyPrint()),
                        pathParameters(
                                parameterWithName("userId").description("매칭정보를 조회할 사용자 ID")
                        ),
                        responseFields(
                                fieldWithPath("data").type(JsonFieldType.ARRAY).description("매칭 목록"),
                                fieldWithPath("data[].matchingRequestId").type(JsonFieldType.NUMBER).description("매칭 요청 ID"),
                                fieldWithPath("data[].status").type(JsonFieldType.STRING).description("매칭 요청 상태"),
                                fieldWithPath("data[].statusName").type(JsonFieldType.STRING).description("매칭 요청 상태명"),
                                fieldWithPath("data[].teamId").type(JsonFieldType.NUMBER).description("매칭의 팀ID"),
                                fieldWithPath("data[].name").type(JsonFieldType.STRING).description("매칭의 팀이름"),
                                fieldWithPath("data[].rankScore").type(JsonFieldType.NUMBER).description("매칭의 팀 점수"),
                                fieldWithPath("data[].kind").type(JsonFieldType.NUMBER).description("매칭의 팀 종목"),
                                fieldWithPath("data[].description").type(JsonFieldType.STRING).description("매칭의 팀 설명"),
                                fieldWithPath("data[].matchTimestamp").type(JsonFieldType.STRING).description("(Optional) 매칭의 경기시간(YYYYMMDDHH24MI 형태. ex-202310182058)").optional(),
                                fieldWithPath("responseCode.code").type(JsonFieldType.STRING).description("응답결과 코드"),
                                fieldWithPath("responseCode.message").type(JsonFieldType.STRING).description("응답결과 메시지")
                        )
                ));
    }


    @DisplayName("매칭 상세정보 조회")
    @Test
    void selectMatchingDetail() throws Exception {

        // 세션 관련 Start ======================================================================
        given(sessionInterceptor.preHandle(any(), any(), any())).willReturn(true);

        String id = "testid@gmail.ccom";
        String name = "testname";
        String password = "testPassword";
        User user = new User(id, name, password);
        UserSession userSession = new UserSession("spring-session" + SessionUtil.getSessionId(user.getId())
                , user.getId()
                , user.getNickname());
        given(sessionService.getSession(any())).willReturn(userSession);
        given(sessionService.getUserSession(any())).willReturn(userSession);
        given(sessionInterceptor.preHandle(any(), any(), any())).willReturn(true);
        // 세션 관련 End ======================================================================

        MatchingDetailBase matchingDetailBase = new MatchingDetailBase(null, 3, 2, "20230930164443", null, null, "W", "대기", null, null,null, null);
        Team team = new Team(155, "test-name", null, "테스트 설명", 1500, 1, "neta6603@naver.com");
        List<User> users = new ArrayList<>();
        User teamUser = new User("neta6603@naver.com", "테스트닉네임", null, "사용자 설명");
        users.add(teamUser);
        MatchingDetailTeam ourTeam = new MatchingDetailTeam(3, "W", "대기", null, team, users);
        MatchingDetailTeam otherTeam = null;
        MatchingDetailResponse response = new MatchingDetailResponse(matchingDetailBase, ourTeam, otherTeam);
        given(matchingService.selectMatchingDetail(any())).willReturn(response);

        String responseContent = objectMapper.writeValueAsString(new CommonResponse<>(response));

        ResultActions resultActions =  this.mockMvc.perform( RestDocumentationRequestBuilders.get("/matching/{matchingRequestId}/detail", 1)
                .header("cookie", "SESSION=" + userSession.getSessionId())
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
        );

        resultActions
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.data").exists())
                .andExpect(jsonPath("$.responseCode").exists())
                .andExpect(jsonPath("$.responseCode.code").exists())
                .andExpect(jsonPath("$.responseCode.message").exists())
                .andExpect((content().json(responseContent)))
                .andDo(document("matching-select-request-detail",
                        Preprocessors.preprocessRequest(Preprocessors.prettyPrint()),
                        Preprocessors.preprocessResponse(Preprocessors.prettyPrint()),
                        pathParameters(
                                parameterWithName("matchingRequestId").description("상세정보를 조회할 매칭 요청 ID")
                        ),
                        responseFields(
                                fieldWithPath("data").type(JsonFieldType.OBJECT).description("매칭 정보"),
                                fieldWithPath("data.matchingDetail").type(JsonFieldType.OBJECT).description("매칭 정보"),
                                fieldWithPath("data.matchingDetail.id").type(JsonFieldType.NUMBER).description("(Optional) 매칭이 잡힌 경우, 잡힌 매칭의 ID").optional(),
                                fieldWithPath("data.matchingDetail.teamId").type(JsonFieldType.NUMBER).description("매칭을 요청한 팀 ID"),
                                fieldWithPath("data.matchingDetail.requestTimestamp").type(JsonFieldType.STRING).description("매칭을 요청한 일시 (YYYYMMDDHH24MI형태)"),
                                fieldWithPath("data.matchingDetail.makeTimestamp").type(JsonFieldType.STRING).description("(Optional) 매칭 성공한 경우 매칭된 일시 (YYYYMMDDHH24MI형태)").optional(),
                                fieldWithPath("data.matchingDetail.matchTimestamp").type(JsonFieldType.STRING).description("(Optional) 매칭 성공한 경우 결정된 경기 일시 (YYYYMMDDHH24MI형태)").optional(),
                                fieldWithPath("data.matchingDetail.status").type(JsonFieldType.STRING).description("매칭의 상태 코드"),
                                fieldWithPath("data.matchingDetail.statusName").type(JsonFieldType.STRING).description("매칭의 상태 코드 명"),
                                fieldWithPath("data.matchingDetail.matchLatitude").type(JsonFieldType.NUMBER).description("(Optional) 매칭된 경기 장소 위도").optional(),
                                fieldWithPath("data.matchingDetail.matchLongitude").type(JsonFieldType.NUMBER).description("(Optional) 매칭된 경기 장소 경도").optional(),
                                fieldWithPath("data.matchingDetail.roadAddress").type(JsonFieldType.STRING).description("(Optional) 매칭된 경기 장소 도로명주소").optional(),
                                fieldWithPath("data.matchingDetail.jibunAddress").type(JsonFieldType.STRING).description("(Optional) 매칭된 경기 장소 지번주소").optional(),
                                fieldWithPath("data.ourTeam").type(JsonFieldType.OBJECT).description("매칭과 관련된 우리팀 정보"),
                                fieldWithPath("data.ourTeam.status").type(JsonFieldType.STRING).description("매칭 관련 우리팀 상태"),
                                fieldWithPath("data.ourTeam.statusName").type(JsonFieldType.STRING).description("매칭 관련 우리팀 상태명"),
                                fieldWithPath("data.ourTeam.team").type(JsonFieldType.OBJECT).description("매칭 관련 우리팀 상세 정보"),
                                fieldWithPath("data.ourTeam.team.id").type(JsonFieldType.NUMBER).description("팀 ID"),
                                fieldWithPath("data.ourTeam.team.name").type(JsonFieldType.STRING).description("팀명"),
                                fieldWithPath("data.ourTeam.team.ownerId").type(JsonFieldType.STRING).description("소유주 ID"),
                                fieldWithPath("data.ourTeam.team.description").type(JsonFieldType.STRING).description("팀 상세정보"),
                                fieldWithPath("data.ourTeam.team.rankScore").type(JsonFieldType.NUMBER).description("팀 점수"),
                                fieldWithPath("data.ourTeam.team.kind").type(JsonFieldType.NUMBER).description("팀 종목"),
                                fieldWithPath("data.ourTeam.team.ciPath").type(JsonFieldType.STRING).description("(Optional)팀로고 경로").optional(),
                                fieldWithPath("data.ourTeam.players").type(JsonFieldType.ARRAY).description("팀 소속 선수"),
                                fieldWithPath("data.ourTeam.players[].id").type(JsonFieldType.STRING).description("선수 ID"),
                                fieldWithPath("data.ourTeam.players[].nickname").type(JsonFieldType.STRING).description("선수 닉네임"),
                                fieldWithPath("data.ourTeam.players[].description").type(JsonFieldType.STRING).description("선수 소개글"),
                                fieldWithPath("data.ourTeam.players[].ciPath").type(JsonFieldType.STRING).description("(Optional) 선수로고 경로").optional(),
                                fieldWithPath("data.otherTeam").type(JsonFieldType.OBJECT).description("(Optional) 매칭과 관련된 상대팀 정보").optional(),
                                fieldWithPath("data.otherTeam.status").type(JsonFieldType.STRING).description("매칭 관련 우리팀 상태"),
                                fieldWithPath("data.otherTeam.statusName").type(JsonFieldType.STRING).description("매칭 관련 우리팀 상태명"),
                                fieldWithPath("data.otherTeam.team").type(JsonFieldType.OBJECT).description("매칭 관련 우리팀 상세 정보"),
                                fieldWithPath("data.otherTeam.team.id").type(JsonFieldType.NUMBER).description("팀 ID"),
                                fieldWithPath("data.otherTeam.team.name").type(JsonFieldType.STRING).description("팀명"),
                                fieldWithPath("data.otherTeam.team.ownerId").type(JsonFieldType.STRING).description("소유주 ID"),
                                fieldWithPath("data.otherTeam.team.description").type(JsonFieldType.STRING).description("팀 상세정보"),
                                fieldWithPath("data.otherTeam.team.rankScore").type(JsonFieldType.NUMBER).description("팀 점수"),
                                fieldWithPath("data.otherTeam.team.kind").type(JsonFieldType.NUMBER).description("팀 종목"),
                                fieldWithPath("data.otherTeam.team.ciPath").type(JsonFieldType.STRING).description("(Optional)팀로고 경로").optional(),
                                fieldWithPath("data.otherTeam.players").type(JsonFieldType.ARRAY).description("팀 소속 선수"),
                                fieldWithPath("data.otherTeam.players[].id").type(JsonFieldType.STRING).description("선수 ID"),
                                fieldWithPath("data.otherTeam.players[].nickname").type(JsonFieldType.STRING).description("선수 닉네임"),
                                fieldWithPath("data.otherTeam.players[].description").type(JsonFieldType.STRING).description("선수 소개글"),
                                fieldWithPath("data.otherTeam.players[].ciPath").type(JsonFieldType.STRING).description("(Optional) 선수로고 경로").optional(),
                                fieldWithPath("responseCode.code").type(JsonFieldType.STRING).description("응답결과 코드"),
                                fieldWithPath("responseCode.message").type(JsonFieldType.STRING).description("응답결과 메시지")
                        )
                ));
    }

    @DisplayName("매칭 요청 취소")
    @Test
    void cancelMatchingRequest() throws Exception {

        // 세션 관련 Start ======================================================================
        given(sessionInterceptor.preHandle(any(), any(), any())).willReturn(true);

        String id = "testid@gmail.ccom";
        String name = "testname";
        String password = "testPassword";
        User user = new User(id, name, password);
        UserSession userSession = new UserSession("spring-session" + SessionUtil.getSessionId(user.getId())
                , user.getId()
                , user.getNickname());
        given(sessionService.getSession(any())).willReturn(userSession);
        given(sessionService.getUserSession(any())).willReturn(userSession);
        given(sessionInterceptor.preHandle(any(), any(), any())).willReturn(true);
        // 세션 관련 End ======================================================================

        String responseContent = objectMapper.writeValueAsString(CommonResponse.withEmptyData(ErrorCode.SUCCESS));

        ResultActions resultActions =  this.mockMvc.perform( RestDocumentationRequestBuilders.delete("/matching/{matchingRequestId}", 1)
                .header("cookie", "SESSION=" + userSession.getSessionId())
                .accept(MediaType.APPLICATION_JSON)
        );

        resultActions
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.responseCode").exists())
                .andExpect(jsonPath("$.responseCode.code").exists())
                .andExpect(jsonPath("$.responseCode.message").exists())
                .andExpect((content().json(responseContent)))
                .andDo(document("matching-cancel-request",
                        Preprocessors.preprocessRequest(Preprocessors.prettyPrint()),
                        Preprocessors.preprocessResponse(Preprocessors.prettyPrint()),
                        pathParameters(
                                parameterWithName("matchingRequestId").description("취소할 매칭 요청 ID")
                        ),
                        responseFields(
                                fieldWithPath("responseCode.code").type(JsonFieldType.STRING).description("응답결과 코드"),
                                fieldWithPath("responseCode.message").type(JsonFieldType.STRING).description("응답결과 메시지")
                        )
                ));
    }

    @DisplayName("매칭 수락")
    @Test
    void acceptMatchingRequest() throws Exception {

        // 세션 관련 Start ======================================================================
        given(sessionInterceptor.preHandle(any(), any(), any())).willReturn(true);

        String id = "testid@gmail.ccom";
        String name = "testname";
        String password = "testPassword";
        User user = new User(id, name, password);
        UserSession userSession = new UserSession("spring-session" + SessionUtil.getSessionId(user.getId())
                , user.getId()
                , user.getNickname());
        given(sessionService.getSession(any())).willReturn(userSession);
        given(sessionService.getUserSession(any())).willReturn(userSession);
        given(sessionInterceptor.preHandle(any(), any(), any())).willReturn(true);
        // 세션 관련 End ======================================================================

        Map<String, Object> requestBody = new HashMap<>();
        requestBody.put("matchingRequestId", 16);
        String requestContent = objectMapper.writeValueAsString(requestBody);
        String responseContent = objectMapper.writeValueAsString(CommonResponse.withEmptyData(ErrorCode.SUCCESS));

        ResultActions resultActions =  this.mockMvc.perform( RestDocumentationRequestBuilders.post("/matching/accept")
                .header("cookie", "SESSION=" + userSession.getSessionId())
                .contentType(MediaType.APPLICATION_JSON)
                .content(requestContent)
                .accept(MediaType.APPLICATION_JSON)
        );

        doNothing().when(matchingService).acceptMatching(any(), any());

        resultActions
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.responseCode").exists())
                .andExpect(jsonPath("$.responseCode.code").exists())
                .andExpect(jsonPath("$.responseCode.message").exists())
                .andExpect((content().json(responseContent)))
                .andDo(document("matching-request-accept",
                        Preprocessors.preprocessRequest(Preprocessors.prettyPrint()),
                        Preprocessors.preprocessResponse(Preprocessors.prettyPrint()),
                        requestFields(
                                fieldWithPath("matchingRequestId").type(JsonFieldType.NUMBER).description("수락할 매칭 Request ID")
                        ),
                        responseFields(
                                fieldWithPath("responseCode.code").type(JsonFieldType.STRING).description("응답결과 코드"),
                                fieldWithPath("responseCode.message").type(JsonFieldType.STRING).description("응답결과 메시지")
                        )
                ));
    }

    @DisplayName("매칭 거절")
    @Test
    void refuseMatchingRequest() throws Exception {

        // 세션 관련 Start ======================================================================
        given(sessionInterceptor.preHandle(any(), any(), any())).willReturn(true);

        String id = "testid@gmail.ccom";
        String name = "testname";
        String password = "testPassword";
        User user = new User(id, name, password);
        UserSession userSession = new UserSession("spring-session" + SessionUtil.getSessionId(user.getId())
                , user.getId()
                , user.getNickname());
        given(sessionService.getSession(any())).willReturn(userSession);
        given(sessionService.getUserSession(any())).willReturn(userSession);
        given(sessionInterceptor.preHandle(any(), any(), any())).willReturn(true);
        // 세션 관련 End ======================================================================

        Map<String, Object> requestBody = new HashMap<>();
        requestBody.put("matchingRequestId", 16);
        String requestContent = objectMapper.writeValueAsString(requestBody);
        String responseContent = objectMapper.writeValueAsString(CommonResponse.withEmptyData(ErrorCode.SUCCESS));

        ResultActions resultActions =  this.mockMvc.perform( RestDocumentationRequestBuilders.post("/matching/refuse")
                .header("cookie", "SESSION=" + userSession.getSessionId())
                .contentType(MediaType.APPLICATION_JSON)
                .content(requestContent)
                .accept(MediaType.APPLICATION_JSON)
        );

        doNothing().when(matchingService).refuseMatching(any(), any());

        resultActions
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.responseCode").exists())
                .andExpect(jsonPath("$.responseCode.code").exists())
                .andExpect(jsonPath("$.responseCode.message").exists())
                .andExpect((content().json(responseContent)))
                .andDo(document("matching-request-refuse",
                        Preprocessors.preprocessRequest(Preprocessors.prettyPrint()),
                        Preprocessors.preprocessResponse(Preprocessors.prettyPrint()),
                        requestFields(
                                fieldWithPath("matchingRequestId").type(JsonFieldType.NUMBER).description("거절할 매칭 Request ID")
                        ),
                        responseFields(
                                fieldWithPath("responseCode.code").type(JsonFieldType.STRING).description("응답결과 코드"),
                                fieldWithPath("responseCode.message").type(JsonFieldType.STRING).description("응답결과 메시지")
                        )
                ));
    }

    @DisplayName("매칭 결과등록")
    @Test
    void reportMatchingResult() throws Exception {

        // 세션 관련 Start ======================================================================
        given(sessionInterceptor.preHandle(any(), any(), any())).willReturn(true);

        String id = "testid@gmail.ccom";
        String name = "testname";
        String password = "testPassword";
        User user = new User(id, name, password);
        UserSession userSession = new UserSession("spring-session" + SessionUtil.getSessionId(user.getId())
                , user.getId()
                , user.getNickname());
        given(sessionService.getSession(any())).willReturn(userSession);
        given(sessionService.getUserSession(any())).willReturn(userSession);
        given(sessionInterceptor.preHandle(any(), any(), any())).willReturn(true);
        // 세션 관련 End ======================================================================
        MatchingResultReportRequest request = new MatchingResultReportRequest(15, 2, 0);
        String requestContent = objectMapper.writeValueAsString(request);
        String responseContent = objectMapper.writeValueAsString(CommonResponse.withEmptyData(ErrorCode.SUCCESS));

        ResultActions resultActions =  this.mockMvc.perform( RestDocumentationRequestBuilders.post("/matching/result")
                .header("cookie", "SESSION=" + userSession.getSessionId())
                .contentType(MediaType.APPLICATION_JSON)
                .content(requestContent)
                .accept(MediaType.APPLICATION_JSON)
        );

        doNothing().when(matchingService).reportResult(any(), any());

        resultActions
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.responseCode").exists())
                .andExpect(jsonPath("$.responseCode.code").exists())
                .andExpect(jsonPath("$.responseCode.message").exists())
                .andExpect((content().json(responseContent)))
                .andDo(document("matching-report-result",
                        Preprocessors.preprocessRequest(Preprocessors.prettyPrint()),
                        Preprocessors.preprocessResponse(Preprocessors.prettyPrint()),
                        requestFields(
                                fieldWithPath("matchingRequestId").type(JsonFieldType.NUMBER).description("결과를 등록할 매칭 Request ID"),
                                fieldWithPath("ourTeamScore").type(JsonFieldType.NUMBER).description("우리팀의 점수"),
                                fieldWithPath("otherTeamScore").type(JsonFieldType.NUMBER).description("상대팀의 점수")
                        ),
                        responseFields(
                                fieldWithPath("responseCode.code").type(JsonFieldType.STRING).description("응답결과 코드"),
                                fieldWithPath("responseCode.message").type(JsonFieldType.STRING).description("응답결과 메시지")
                        )
                ));
    }

    @DisplayName("매칭 히스토리 조회")
    @Test
    void selectMatchingHistory() throws Exception {

        // 세션 관련 Start ======================================================================
        given(sessionInterceptor.preHandle(any(), any(), any())).willReturn(true);

        String id = "testid@gmail.ccom";
        String name = "testname";
        String password = "testPassword";
        User user = new User(id, name, password);
        UserSession userSession = new UserSession("spring-session" + SessionUtil.getSessionId(user.getId())
                , user.getId()
                , user.getNickname());
        given(sessionService.getSession(any())).willReturn(userSession);
        given(sessionService.getUserSession(any())).willReturn(userSession);
        given(sessionInterceptor.preHandle(any(), any(), any())).willReturn(true);
        // 세션 관련 End ======================================================================

        List<MatchingHistoryBase> responseList = new ArrayList<>();
        int ourTeamId = 1;
        int otherTeamId = 2;
        MatchingHistoryTeam ourTeam = new MatchingHistoryTeam(ourTeamId, "sunny eleven", "test.png", 2);
        MatchingHistoryTeam otherTeam = new MatchingHistoryTeam(otherTeamId, "test other team", "other.png", 0);
        MatchingHistoryBase res = new MatchingHistoryBase(1, ourTeamId, otherTeamId, "202311271000", "서울특별시 노원구 공릉로 232", "서울특별시 노원구 공릉동 172",
                37.6317692339419,127.0803445512275,"E", "경기종료", 2, 0, "202311251000", ourTeam, otherTeam
                );
        responseList.add(res);
        given(matchingService.selectMatchHistory(any(), any())).willReturn(responseList);

        String responseContent = objectMapper.writeValueAsString(new CommonResponse<>(responseList));

        ResultActions resultActions =  this.mockMvc.perform( RestDocumentationRequestBuilders.get("/matching/history/{teamId}", 1)
                .header("cookie", "SESSION=" + userSession.getSessionId())
                .param("page", "1")
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
        );

        resultActions
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.data").exists())
                .andExpect(jsonPath("$.responseCode").exists())
                .andExpect(jsonPath("$.responseCode.code").exists())
                .andExpect(jsonPath("$.responseCode.message").exists())
                .andExpect((content().json(responseContent)))
                .andDo(document("matching-select-history",
                        Preprocessors.preprocessRequest(Preprocessors.prettyPrint()),
                        Preprocessors.preprocessResponse(Preprocessors.prettyPrint()),
                        queryParameters(
                                parameterWithName("page").description("조회할 페이지")
                        ),
                        pathParameters(
                                parameterWithName("teamId").description("경기 결과 히스토리를 조회할 팀 ID")
                        ),
                        responseFields(
                                fieldWithPath("data").type(JsonFieldType.ARRAY).description("경기 결과 목록"),
                                fieldWithPath("data[].id").type(JsonFieldType.NUMBER).description("매칭 매핑 ID"),
                                fieldWithPath("data[].matchTimestamp").type(JsonFieldType.STRING).description("(Optional) 매칭의 경기시간(YYYYMMDDHH24MI 형태. ex-202310182058)"),
                                fieldWithPath("data[].roadAddress").type(JsonFieldType.STRING).description("매칭 장소(도로명주소)"),
                                fieldWithPath("data[].jibunAddress").type(JsonFieldType.STRING).description("매칭 장소(지번주소)"),
                                fieldWithPath("data[].latitude").type(JsonFieldType.NUMBER).description("매칭 장소(위도)"),
                                fieldWithPath("data[].longitude").type(JsonFieldType.NUMBER).description("매칭 장소(경도)"),
                                fieldWithPath("data[].status").type(JsonFieldType.STRING).description("매칭 요청 상태코드"),
                                fieldWithPath("data[].statusName").type(JsonFieldType.STRING).description("매칭 요청 상태명"),
                                fieldWithPath("data[].ourTeam").type(JsonFieldType.OBJECT).description("우리팀 정보"),
                                fieldWithPath("data[].ourTeam.id").type(JsonFieldType.NUMBER).description("팀ID"),
                                fieldWithPath("data[].ourTeam.name").type(JsonFieldType.STRING).description("팀명"),
                                fieldWithPath("data[].ourTeam.ciPath").type(JsonFieldType.STRING).description("팀로고명"),
                                fieldWithPath("data[].ourTeam.score").type(JsonFieldType.NUMBER).description("우리팀 점수(Optional)"),
                                fieldWithPath("data[].otherTeam").type(JsonFieldType.OBJECT).description("상대팀 정보"),
                                fieldWithPath("data[].otherTeam.id").type(JsonFieldType.NUMBER).description("팀ID"),
                                fieldWithPath("data[].otherTeam.name").type(JsonFieldType.STRING).description("팀명"),
                                fieldWithPath("data[].otherTeam.ciPath").type(JsonFieldType.STRING).description("팀로고명"),
                                fieldWithPath("data[].otherTeam.score").type(JsonFieldType.NUMBER).description("우리팀 점수(Optional)"),
                                fieldWithPath("responseCode.code").type(JsonFieldType.STRING).description("응답결과 코드"),
                                fieldWithPath("responseCode.message").type(JsonFieldType.STRING).description("응답결과 메시지")
                        )
                ));
    }
}
