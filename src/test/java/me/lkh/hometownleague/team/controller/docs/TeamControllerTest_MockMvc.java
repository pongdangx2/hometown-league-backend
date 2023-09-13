package me.lkh.hometownleague.team.controller.docs;

import com.fasterxml.jackson.databind.ObjectMapper;
import me.lkh.hometownleague.common.exception.ErrorCode;
import me.lkh.hometownleague.common.interceptor.SessionInterceptor;
import me.lkh.hometownleague.common.response.CommonResponse;
import me.lkh.hometownleague.common.util.SessionUtil;
import me.lkh.hometownleague.session.domain.UserSession;
import me.lkh.hometownleague.session.service.SessionService;
import me.lkh.hometownleague.team.controller.TeamController;
import me.lkh.hometownleague.team.domain.Team;
import me.lkh.hometownleague.team.domain.request.UpdateTeamPlayLocationRequest;
import me.lkh.hometownleague.team.domain.request.UpdateTeamPlayTimeRequest;
import me.lkh.hometownleague.team.domain.request.UpdateTeamRequest;
import me.lkh.hometownleague.team.service.TeamService;
import me.lkh.hometownleague.team.domain.TeamPlayLocation;
import me.lkh.hometownleague.team.domain.TeamPlayTime;
import me.lkh.hometownleague.team.domain.request.MakeTeamRequest;
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
import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.document;
import static org.springframework.restdocs.payload.PayloadDocumentation.*;
import static org.springframework.restdocs.request.RequestDocumentation.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@AutoConfigureRestDocs(uriHost="218.232.175.4", uriPort = 49156)
@AutoConfigureMockMvc
@WebMvcTest(TeamController.class)
public class TeamControllerTest_MockMvc {

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
    private TeamService teamService;

    @MockBean
    private SessionInterceptor sessionInterceptor;

    @DisplayName("팀명 중복체크")
    @Test
    void isDuplicate() throws Exception {

        String teamName = "testTeamName";
        given(sessionInterceptor.preHandle(any(), any(), any())).willReturn(true);

        ResultActions resultActions =  this.mockMvc.perform( RestDocumentationRequestBuilders.get("/team/is-duplicate/{teamName}", teamName)
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
        );

        resultActions
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.responseCode").exists())
                .andExpect(jsonPath("$.responseCode.code").exists())
                .andExpect(jsonPath("$.responseCode.message").exists())
                .andDo(document("team-is-duplicate",
                        Preprocessors.preprocessRequest(Preprocessors.prettyPrint()),
                        Preprocessors.preprocessResponse(Preprocessors.prettyPrint()),
                        pathParameters(
                                parameterWithName("teamName").description("중복체크할 팀 이름")
                        ),
                        responseFields(
                                fieldWithPath("data").type(JsonFieldType.STRING).description("이미 존재하는 경우 Y / 존재하지 않는 경우 N"),
                                fieldWithPath("responseCode.code").type(JsonFieldType.STRING).description("응답결과 코드"),
                                fieldWithPath("responseCode.message").type(JsonFieldType.STRING).description("응답결과 메시지")
                        )
                ));
    }

    @DisplayName("팀 생성")
    @Test
    void makeTeam() throws Exception {
        List<TeamPlayTime> time = new ArrayList<>();
        time.add(new TeamPlayTime(null, null, 1, "1000", "1200"));
        time.add(new TeamPlayTime(null, null, 2, "2000", "2200"));


        List<TeamPlayLocation> location = new ArrayList<>();
        location.add(new TeamPlayLocation(null, null, "서울특별시 노원구 공릉동 172", "서울특별시 노원구 공릉로 232", 37.6317692339419, 127.0803445512275, "11350103"));

        String teamName = "testTeamName";
        String requestContent = objectMapper.writeValueAsString(new MakeTeamRequest(teamName, null, "tempPath", "테스트로 만든 팀입니다.", 0, 3, time, location));
        String responseContent = objectMapper.writeValueAsString(CommonResponse.withEmptyData(ErrorCode.SUCCESS));

        String id = "testid";
        String name = "testname";
        String password = "testPassword";
        User user = new User(id, name, password);
        UserSession userSession = new UserSession("spring-session" + SessionUtil.getSessionId(user.getId())
                , user.getId()
                , user.getNickname());
        given(sessionService.getSession(any())).willReturn(userSession);
        given(sessionService.getUserSession(any())).willReturn(userSession);

        given(sessionInterceptor.preHandle(any(), any(), any())).willReturn(true);

        ResultActions resultActions =  this.mockMvc.perform( RestDocumentationRequestBuilders.post("/team")
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
                .andDo(document("team-create",
                        Preprocessors.preprocessRequest(Preprocessors.prettyPrint()),
                        Preprocessors.preprocessResponse(Preprocessors.prettyPrint()),
                        requestFields(
                                fieldWithPath("name").type(JsonFieldType.STRING).description("생성할 팀의 이름"),
                                fieldWithPath("kind").type(JsonFieldType.NUMBER).description("생성할 팀의 종목 코드 (축구: 1)"),
                                fieldWithPath("description").type(JsonFieldType.STRING).description("생성할 팀의 소개글"),
                                fieldWithPath("time").type(JsonFieldType.ARRAY).description("주로 운동하는 시간"),
                                fieldWithPath("time[].dayOfWeek").type(JsonFieldType.NUMBER).description("운동하는 요일(1:월요일 ~ 7: 일요일)"),
                                fieldWithPath("time[].playTimeFrom").type(JsonFieldType.STRING).description("운동 시작시간 (HH24MI형태, ex-1030)"),
                                fieldWithPath("time[].playTimeTo").type(JsonFieldType.STRING).description("운동 종료시간 (HH24MI형태, ex-1030)"),
                                fieldWithPath("location").type(JsonFieldType.ARRAY).description("주로 운동하는 장소"),
                                fieldWithPath("location[].latitude").type(JsonFieldType.NUMBER).description("위도"),
                                fieldWithPath("location[].longitude").type(JsonFieldType.NUMBER).description("경도"),
                                fieldWithPath("location[].legalCode").type(JsonFieldType.STRING).description("법정동 코드"),
                                fieldWithPath("location[].jibunAddress").type(JsonFieldType.STRING).description("(Optional)지번주소").optional(),
                                fieldWithPath("location[].roadAddress").type(JsonFieldType.STRING).description("(Optional)도로명주소").optional()
                        ),
                        responseFields(
                                fieldWithPath("responseCode.code").type(JsonFieldType.STRING).description("응답결과 코드"),
                                fieldWithPath("responseCode.message").type(JsonFieldType.STRING).description("응답결과 메시지")
                        )
                ));
    }

    @DisplayName("팀 삭제")
    @Test
    void deleteTeam() throws Exception {

        String teamId = "16";
        given(sessionInterceptor.preHandle(any(), any(), any())).willReturn(true);

        String id = "testid";
        String name = "testname";
        String password = "testPassword";
        User user = new User(id, name, password);
        UserSession userSession = new UserSession("spring-session" + SessionUtil.getSessionId(user.getId())
                , user.getId()
                , user.getNickname());
        given(sessionService.getSession(any())).willReturn(userSession);
        given(sessionService.getUserSession(any())).willReturn(userSession);
        ResultActions resultActions =  this.mockMvc.perform( RestDocumentationRequestBuilders.delete("/team/{teamId}", teamId)
                .header("cookie", "SESSION=" + userSession.getSessionId())
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
        );

        resultActions
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.responseCode").exists())
                .andExpect(jsonPath("$.responseCode.code").exists())
                .andExpect(jsonPath("$.responseCode.message").exists())
                .andDo(document("team-delete",
                        Preprocessors.preprocessRequest(Preprocessors.prettyPrint()),
                        Preprocessors.preprocessResponse(Preprocessors.prettyPrint()),
                        pathParameters(
                                parameterWithName("teamId").description("삭제할 팀 ID")
                        ),
                        responseFields(
                                fieldWithPath("responseCode.code").type(JsonFieldType.STRING).description("응답결과 코드"),
                                fieldWithPath("responseCode.message").type(JsonFieldType.STRING).description("응답결과 메시지")
                        )
                ));
    }

    @DisplayName("팀 상세정보 조회")
    @Test
    void selectTeamDetail() throws Exception {

        List<TeamPlayTime> time = new ArrayList<>();
        time.add(new TeamPlayTime(1, null, 1, "1000", "1200"));
        time.add(new TeamPlayTime(2, null, 2, "2000", "2200"));

        List<TeamPlayLocation> location = new ArrayList<>();
        location.add(new TeamPlayLocation(1, null, "서울특별시 노원구 공릉동 172", "서울특별시 노원구 공릉로 232", 37.6317692339419, 127.0803445512275, "11350103"));

        Team team = Team.forSelectTeamResponse(16, "Sunny Eleven", "..", "테스트 설명입니다.", 0, "UNRANKED", 1, "Y", time, location);
        String responseContent = objectMapper.writeValueAsString(new CommonResponse<>(team));
        given(teamService.selectTeam(any(), any())).willReturn(team);
        // 세션 관련 Start ======================================================================
        given(sessionInterceptor.preHandle(any(), any(), any())).willReturn(true);

        String id = "testid";
        String name = "testname";
        String password = "testPassword";
        User user = new User(id, name, password);
        UserSession userSession = new UserSession("spring-session" + SessionUtil.getSessionId(user.getId())
                , user.getId()
                , user.getNickname());
        given(sessionService.getSession(any())).willReturn(userSession);
        given(sessionService.getUserSession(any())).willReturn(userSession);
        // 세션 관련 End ======================================================================

        ResultActions resultActions = this.mockMvc.perform(RestDocumentationRequestBuilders.get("/team/{teamId}", 16)
                .header("cookie", "SESSION=" + userSession.getSessionId())
                .accept(MediaType.APPLICATION_JSON)
        );

        resultActions
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.data").exists())
                .andExpect(jsonPath("$.responseCode").exists())
                .andExpect(jsonPath("$.responseCode.code").exists())
                .andExpect(jsonPath("$.responseCode.message").exists())
                .andExpect((content().json(responseContent)))
                .andDo(document("team-select-detail",
                        Preprocessors.preprocessRequest(Preprocessors.prettyPrint()),
                        Preprocessors.preprocessResponse(Preprocessors.prettyPrint()),
                        pathParameters(
                                parameterWithName("teamId").description("상세정보를 조회할 팀 ID")
                        ),
                        responseFields(
                                fieldWithPath("data").type(JsonFieldType.OBJECT).description("조회한 팀의 상세 정보"),
                                fieldWithPath("data.id").type(JsonFieldType.NUMBER).description("팀 ID"),
                                fieldWithPath("data.name").type(JsonFieldType.STRING).description("팀 이름"),
                                fieldWithPath("data.ciPath").type(JsonFieldType.STRING).description("(Optional)팀 로고 경로"),
                                fieldWithPath("data.description").type(JsonFieldType.STRING).description("팀 설명"),
                                fieldWithPath("data.rankScore").type(JsonFieldType.NUMBER).description("팀 점수"),
                                fieldWithPath("data.rank").type(JsonFieldType.STRING).description("팀 점수별 랭크"),
                                fieldWithPath("data.kind").type(JsonFieldType.NUMBER).description("팀 종목"),
                                fieldWithPath("data.ownerYn").type(JsonFieldType.STRING).description("조회한 유저가 팀의 소유주인지 여부"),
                                fieldWithPath("data.time").type(JsonFieldType.ARRAY).description("주로 운동하는 시간"),
                                fieldWithPath("data.time[].id").type(JsonFieldType.NUMBER).description("운동시간의 ID"),
                                fieldWithPath("data.time[].dayOfWeek").type(JsonFieldType.NUMBER).description("운동하는 요일(1:월요일 ~ 7: 일요일)"),
                                fieldWithPath("data.time[].playTimeFrom").type(JsonFieldType.STRING).description("운동 시작시간 (HH24MI형태, ex-1030)"),
                                fieldWithPath("data.time[].playTimeTo").type(JsonFieldType.STRING).description("운동 종료시간 (HH24MI형태, ex-1030)"),
                                fieldWithPath("data.location").type(JsonFieldType.ARRAY).description("주로 운동하는 장소"),
                                fieldWithPath("data.location[].id").type(JsonFieldType.NUMBER).description("운동장소의 ID"),
                                fieldWithPath("data.location[].latitude").type(JsonFieldType.NUMBER).description("위도"),
                                fieldWithPath("data.location[].longitude").type(JsonFieldType.NUMBER).description("경도"),
                                fieldWithPath("data.location[].legalCode").type(JsonFieldType.STRING).description("법정동 코드"),
                                fieldWithPath("data.location[].jibunAddress").type(JsonFieldType.STRING).description("(Optional)지번주소").optional(),
                                fieldWithPath("data.location[].roadAddress").type(JsonFieldType.STRING).description("(Optional)도로명주소").optional(),
                                fieldWithPath("responseCode.code").type(JsonFieldType.STRING).description("응답결과 코드"),
                                fieldWithPath("responseCode.message").type(JsonFieldType.STRING).description("응답결과 메시지")
                        )
                ));
    }

    @DisplayName("팀 기본 정보 업데이트")
    @Test
    void updateTeamBaseInfo() throws Exception {

        List<TeamPlayTime> time = new ArrayList<>();
        time.add(new TeamPlayTime(1, null, 1, "1000", "1200"));
        time.add(new TeamPlayTime(2, null, 2, "2000", "2200"));

        List<TeamPlayLocation> location = new ArrayList<>();
        location.add(new TeamPlayLocation(1, null, "서울특별시 노원구 공릉동 172", "서울특별시 노원구 공릉로 232", 37.6317692339419, 127.0803445512275, "11350103"));

        Team team = Team.forSelectTeamResponse(16, "Sunny Eleven", "..", "테스트 설명입니다.", 0, "UNRANKED", 1, "Y", time, location);
        String responseContent = objectMapper.writeValueAsString(new CommonResponse<>(team));
        given(teamService.selectTeam(any(), any())).willReturn(team);
        // 세션 관련 Start ======================================================================
        given(sessionInterceptor.preHandle(any(), any(), any())).willReturn(true);

        String id = "testid";
        String name = "testname";
        String password = "testPassword";
        User user = new User(id, name, password);
        UserSession userSession = new UserSession("spring-session" + SessionUtil.getSessionId(user.getId())
                , user.getId()
                , user.getNickname());
        given(sessionService.getSession(any())).willReturn(userSession);
        given(sessionService.getUserSession(any())).willReturn(userSession);
        // 세션 관련 End ======================================================================

        String requestContent = objectMapper.writeValueAsString(new UpdateTeamRequest(16, "TestTeamName",  "테스트로 만든 팀입니다."));

        ResultActions resultActions = this.mockMvc.perform(RestDocumentationRequestBuilders.patch("/team")
                .header("cookie", "SESSION=" + userSession.getSessionId())
                .content(requestContent)
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
                .andDo(document("team-update-base",
                        Preprocessors.preprocessRequest(Preprocessors.prettyPrint()),
                        Preprocessors.preprocessResponse(Preprocessors.prettyPrint()),
                        requestFields(
                                fieldWithPath("id").description("(Required)업데이트할 팀 ID-변경되지는 않고 key로 사용됨."),
                                fieldWithPath("name").description("수정할 새로운 팀명"),
                                fieldWithPath("description").description("수정할 새로운 소개글")
                        ),
                        responseFields(
                                fieldWithPath("data").type(JsonFieldType.OBJECT).description("수정 후 팀의 상세 정보"),
                                fieldWithPath("data.id").type(JsonFieldType.NUMBER).description("팀 ID"),
                                fieldWithPath("data.name").type(JsonFieldType.STRING).description("팀 이름"),
                                fieldWithPath("data.ciPath").type(JsonFieldType.STRING).description("(Optional)팀 로고 경로"),
                                fieldWithPath("data.description").type(JsonFieldType.STRING).description("팀 설명"),
                                fieldWithPath("data.rankScore").type(JsonFieldType.NUMBER).description("팀 점수"),
                                fieldWithPath("data.rank").type(JsonFieldType.STRING).description("팀 점수별 랭크"),
                                fieldWithPath("data.kind").type(JsonFieldType.NUMBER).description("팀 종목"),
                                fieldWithPath("data.ownerYn").type(JsonFieldType.STRING).description("조회한 유저가 팀의 소유주인지 여부"),
                                fieldWithPath("data.time").type(JsonFieldType.ARRAY).description("주로 운동하는 시간"),
                                fieldWithPath("data.time[].id").type(JsonFieldType.NUMBER).description("운동시간의 ID"),
                                fieldWithPath("data.time[].dayOfWeek").type(JsonFieldType.NUMBER).description("운동하는 요일(1:월요일 ~ 7: 일요일)"),
                                fieldWithPath("data.time[].playTimeFrom").type(JsonFieldType.STRING).description("운동 시작시간 (HH24MI형태, ex-1030)"),
                                fieldWithPath("data.time[].playTimeTo").type(JsonFieldType.STRING).description("운동 종료시간 (HH24MI형태, ex-1030)"),
                                fieldWithPath("data.location").type(JsonFieldType.ARRAY).description("주로 운동하는 장소"),
                                fieldWithPath("data.location[].id").type(JsonFieldType.NUMBER).description("운동장소의 ID"),
                                fieldWithPath("data.location[].latitude").type(JsonFieldType.NUMBER).description("위도"),
                                fieldWithPath("data.location[].longitude").type(JsonFieldType.NUMBER).description("경도"),
                                fieldWithPath("data.location[].legalCode").type(JsonFieldType.STRING).description("법정동 코드"),
                                fieldWithPath("data.location[].jibunAddress").type(JsonFieldType.STRING).description("(Optional)지번주소").optional(),
                                fieldWithPath("data.location[].roadAddress").type(JsonFieldType.STRING).description("(Optional)도로명주소").optional(),
                                fieldWithPath("responseCode.code").type(JsonFieldType.STRING).description("응답결과 코드"),
                                fieldWithPath("responseCode.message").type(JsonFieldType.STRING).description("응답결과 메시지")
                        )
                ));
    }

    @DisplayName("팀 운동 시간 업데이트")
    @Test
    void updateTeamPlayTime() throws Exception {

        List<TeamPlayTime> time = new ArrayList<>();
        time.add(new TeamPlayTime(1, null, 1, "1000", "1200"));
        time.add(new TeamPlayTime(2, null, 2, "2000", "2200"));

        List<TeamPlayLocation> location = new ArrayList<>();
        location.add(new TeamPlayLocation(1, null, "서울특별시 노원구 공릉동 172", "서울특별시 노원구 공릉로 232", 37.6317692339419, 127.0803445512275, "11350103"));

        Team team = Team.forSelectTeamResponse(16, "Sunny Eleven", "..", "테스트 설명입니다.", 0, "UNRANKED", 1, "Y", time, location);
        String responseContent = objectMapper.writeValueAsString(new CommonResponse<>(team));
        given(teamService.selectTeam(any(), any())).willReturn(team);
        // 세션 관련 Start ======================================================================
        given(sessionInterceptor.preHandle(any(), any(), any())).willReturn(true);

        String id = "testid";
        String name = "testname";
        String password = "testPassword";
        User user = new User(id, name, password);
        UserSession userSession = new UserSession("spring-session" + SessionUtil.getSessionId(user.getId())
                , user.getId()
                , user.getNickname());
        given(sessionService.getSession(any())).willReturn(userSession);
        given(sessionService.getUserSession(any())).willReturn(userSession);
        // 세션 관련 End ======================================================================

        String requestContent = objectMapper.writeValueAsString(new UpdateTeamPlayTimeRequest(16, time));

        ResultActions resultActions = this.mockMvc.perform(RestDocumentationRequestBuilders.put("/team/play-time")
                .header("cookie", "SESSION=" + userSession.getSessionId())
                .content(requestContent)
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
                .andDo(document("team-update-play-time",
                        Preprocessors.preprocessRequest(Preprocessors.prettyPrint()),
                        Preprocessors.preprocessResponse(Preprocessors.prettyPrint()),
                        requestFields(
                                fieldWithPath("teamId").description("(Required)업데이트할 팀 ID - 변경되지는 않고 key로 사용됨."),
                                fieldWithPath("time").type(JsonFieldType.ARRAY).description("수정할 시간"),
                                fieldWithPath("time[].id").type(JsonFieldType.NUMBER).description("수정할 시간 ID"),
                                fieldWithPath("time[].dayOfWeek").type(JsonFieldType.NUMBER).description("수정할 시간 요일"),
                                fieldWithPath("time[].playTimeFrom").type(JsonFieldType.STRING).description("수정할 From시간"),
                                fieldWithPath("time[].playTimeTo").type(JsonFieldType.STRING).description("수정할 To시간")
                        ),
                        responseFields(
                                fieldWithPath("data").type(JsonFieldType.OBJECT).description("수정 후 팀의 상세 정보"),
                                fieldWithPath("data.id").type(JsonFieldType.NUMBER).description("팀 ID"),
                                fieldWithPath("data.name").type(JsonFieldType.STRING).description("팀 이름"),
                                fieldWithPath("data.ciPath").type(JsonFieldType.STRING).description("(Optional)팀 로고 경로"),
                                fieldWithPath("data.description").type(JsonFieldType.STRING).description("팀 설명"),
                                fieldWithPath("data.rankScore").type(JsonFieldType.NUMBER).description("팀 점수"),
                                fieldWithPath("data.rank").type(JsonFieldType.STRING).description("팀 점수별 랭크"),
                                fieldWithPath("data.kind").type(JsonFieldType.NUMBER).description("팀 종목"),
                                fieldWithPath("data.ownerYn").type(JsonFieldType.STRING).description("조회한 유저가 팀의 소유주인지 여부"),
                                fieldWithPath("data.time").type(JsonFieldType.ARRAY).description("주로 운동하는 시간"),
                                fieldWithPath("data.time[].id").type(JsonFieldType.NUMBER).description("운동시간의 ID"),
                                fieldWithPath("data.time[].dayOfWeek").type(JsonFieldType.NUMBER).description("운동하는 요일(1:월요일 ~ 7: 일요일)"),
                                fieldWithPath("data.time[].playTimeFrom").type(JsonFieldType.STRING).description("운동 시작시간 (HH24MI형태, ex-1030)"),
                                fieldWithPath("data.time[].playTimeTo").type(JsonFieldType.STRING).description("운동 종료시간 (HH24MI형태, ex-1030)"),
                                fieldWithPath("data.location").type(JsonFieldType.ARRAY).description("주로 운동하는 장소"),
                                fieldWithPath("data.location[].id").type(JsonFieldType.NUMBER).description("운동장소의 ID"),
                                fieldWithPath("data.location[].latitude").type(JsonFieldType.NUMBER).description("위도"),
                                fieldWithPath("data.location[].longitude").type(JsonFieldType.NUMBER).description("경도"),
                                fieldWithPath("data.location[].legalCode").type(JsonFieldType.STRING).description("법정동 코드"),
                                fieldWithPath("data.location[].jibunAddress").type(JsonFieldType.STRING).description("(Optional)지번주소").optional(),
                                fieldWithPath("data.location[].roadAddress").type(JsonFieldType.STRING).description("(Optional)도로명주소").optional(),
                                fieldWithPath("responseCode.code").type(JsonFieldType.STRING).description("응답결과 코드"),
                                fieldWithPath("responseCode.message").type(JsonFieldType.STRING).description("응답결과 메시지")
                        )
                ));
    }

    @DisplayName("팀 운동 장소 업데이트")
    @Test
    void updateTeamPlayLocation() throws Exception {

        List<TeamPlayTime> time = new ArrayList<>();
        time.add(new TeamPlayTime(1, null, 1, "1000", "1200"));
        time.add(new TeamPlayTime(2, null, 2, "2000", "2200"));

        List<TeamPlayLocation> location = new ArrayList<>();
        location.add(new TeamPlayLocation(1, null, "서울특별시 노원구 공릉동 172", "서울특별시 노원구 공릉로 232", 37.6317692339419, 127.0803445512275, "11350103"));

        Team team = Team.forSelectTeamResponse(16, "Sunny Eleven", "..", "테스트 설명입니다.", 0, "UNRANKED", 1, "Y", time, location);
        String responseContent = objectMapper.writeValueAsString(new CommonResponse<>(team));
        given(teamService.selectTeam(any(), any())).willReturn(team);
        // 세션 관련 Start ======================================================================
        given(sessionInterceptor.preHandle(any(), any(), any())).willReturn(true);

        String id = "testid";
        String name = "testname";
        String password = "testPassword";
        User user = new User(id, name, password);
        UserSession userSession = new UserSession("spring-session" + SessionUtil.getSessionId(user.getId())
                , user.getId()
                , user.getNickname());
        given(sessionService.getSession(any())).willReturn(userSession);
        given(sessionService.getUserSession(any())).willReturn(userSession);
        // 세션 관련 End ======================================================================

        String requestContent = objectMapper.writeValueAsString(new UpdateTeamPlayLocationRequest(16, location));

        ResultActions resultActions = this.mockMvc.perform(RestDocumentationRequestBuilders.put("/team/play-location")
                .header("cookie", "SESSION=" + userSession.getSessionId())
                .content(requestContent)
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
                .andDo(document("team-update-play-location",
                        Preprocessors.preprocessRequest(Preprocessors.prettyPrint()),
                        Preprocessors.preprocessResponse(Preprocessors.prettyPrint()),
                        requestFields(
                                fieldWithPath("teamId").description("(Required)업데이트할 팀 ID - 변경되지는 않고 key로 사용됨."),
                                fieldWithPath("location").type(JsonFieldType.ARRAY).description("수정할 장소들"),
                                fieldWithPath("location[].id").type(JsonFieldType.NUMBER).description("운동장소의 ID"),
                                fieldWithPath("location[].latitude").type(JsonFieldType.NUMBER).description("위도"),
                                fieldWithPath("location[].longitude").type(JsonFieldType.NUMBER).description("경도"),
                                fieldWithPath("location[].legalCode").type(JsonFieldType.STRING).description("법정동 코드"),
                                fieldWithPath("location[].jibunAddress").type(JsonFieldType.STRING).description("(Optional)지번주소").optional(),
                                fieldWithPath("location[].roadAddress").type(JsonFieldType.STRING).description("(Optional)도로명주소").optional()
                        ),
                        responseFields(
                                fieldWithPath("data").type(JsonFieldType.OBJECT).description("수정 후 팀의 상세 정보"),
                                fieldWithPath("data.id").type(JsonFieldType.NUMBER).description("팀 ID"),
                                fieldWithPath("data.name").type(JsonFieldType.STRING).description("팀 이름"),
                                fieldWithPath("data.ciPath").type(JsonFieldType.STRING).description("(Optional)팀 로고 경로"),
                                fieldWithPath("data.description").type(JsonFieldType.STRING).description("팀 설명"),
                                fieldWithPath("data.rankScore").type(JsonFieldType.NUMBER).description("팀 점수"),
                                fieldWithPath("data.rank").type(JsonFieldType.STRING).description("팀 점수별 랭크"),
                                fieldWithPath("data.kind").type(JsonFieldType.NUMBER).description("팀 종목"),
                                fieldWithPath("data.ownerYn").type(JsonFieldType.STRING).description("조회한 유저가 팀의 소유주인지 여부"),
                                fieldWithPath("data.time").type(JsonFieldType.ARRAY).description("주로 운동하는 시간"),
                                fieldWithPath("data.time[].id").type(JsonFieldType.NUMBER).description("운동시간의 ID"),
                                fieldWithPath("data.time[].dayOfWeek").type(JsonFieldType.NUMBER).description("운동하는 요일(1:월요일 ~ 7: 일요일)"),
                                fieldWithPath("data.time[].playTimeFrom").type(JsonFieldType.STRING).description("운동 시작시간 (HH24MI형태, ex-1030)"),
                                fieldWithPath("data.time[].playTimeTo").type(JsonFieldType.STRING).description("운동 종료시간 (HH24MI형태, ex-1030)"),
                                fieldWithPath("data.location").type(JsonFieldType.ARRAY).description("주로 운동하는 장소"),
                                fieldWithPath("data.location[].id").type(JsonFieldType.NUMBER).description("운동장소의 ID"),
                                fieldWithPath("data.location[].latitude").type(JsonFieldType.NUMBER).description("위도"),
                                fieldWithPath("data.location[].longitude").type(JsonFieldType.NUMBER).description("경도"),
                                fieldWithPath("data.location[].legalCode").type(JsonFieldType.STRING).description("법정동 코드"),
                                fieldWithPath("data.location[].jibunAddress").type(JsonFieldType.STRING).description("(Optional)지번주소").optional(),
                                fieldWithPath("data.location[].roadAddress").type(JsonFieldType.STRING).description("(Optional)도로명주소").optional(),
                                fieldWithPath("responseCode.code").type(JsonFieldType.STRING).description("응답결과 코드"),
                                fieldWithPath("responseCode.message").type(JsonFieldType.STRING).description("응답결과 메시지")
                        )
                ));
    }
}
