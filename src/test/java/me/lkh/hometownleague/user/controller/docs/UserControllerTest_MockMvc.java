package me.lkh.hometownleague.user.controller.docs;

import com.fasterxml.jackson.databind.ObjectMapper;
import me.lkh.hometownleague.common.exception.ErrorCode;
import me.lkh.hometownleague.common.interceptor.SessionInterceptor;
import me.lkh.hometownleague.common.response.CommonResponse;
import me.lkh.hometownleague.common.util.SessionUtil;
import me.lkh.hometownleague.session.domain.UserSession;
import me.lkh.hometownleague.session.service.SessionService;
import me.lkh.hometownleague.user.controller.UserController;
import me.lkh.hometownleague.user.domain.User;
import me.lkh.hometownleague.user.domain.UserTeam;
import me.lkh.hometownleague.user.domain.request.JoinRequest;
import me.lkh.hometownleague.user.domain.request.LoginRequest;
import me.lkh.hometownleague.user.domain.request.UpdateRequest;
import me.lkh.hometownleague.user.service.UserService;
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
@WebMvcTest(UserController.class)
class UserControllerTest_MockMvc {

    @Rule
    public JUnitRestDocumentation restDocumentation = new JUnitRestDocumentation("target/generated-snippets");

    @SuppressWarnings("SpringJavaInjectionPointsAutowiringInspection")
    // 인텔리제이문제로 Could not autowire. No beans of 'MockMvc' type found.  에러가 나와서 붙였음.
    @Autowired
    private MockMvc mockMvc;

    @SuppressWarnings("SpringJavaInjectionPointsAutowiringInspection")
    // 인텔리제이문제로 Could not autowire. No beans of 'MockMvc' type found.  에러가 나와서 붙였음.
    @Autowired
    private ObjectMapper objectMapper;

    @MockBean
    private SessionService sessionService;

    @MockBean
    private UserService userService;

    @MockBean
    private SessionInterceptor sessionInterceptor;

    @DisplayName("로그인 테스트")
    @Test
    void login() throws Exception {

        String id = "testid";
        String name = "testname";
        String password = "testPassword";
        String description = "testDescription";

        User user = new User(id, name, password, description);
        given(sessionInterceptor.preHandle(any(), any(), any())).willReturn(true);
        given(userService.loginCheck(any())).willReturn(user);
        given(sessionService.getSession(any())).willReturn(new UserSession("spring-session" + SessionUtil.getSessionId(user.getId())
                , user.getId()
                , user.getNickname()));

        String requestContent = objectMapper.writeValueAsString(new LoginRequest(id, password));
        String responseContent = objectMapper.writeValueAsString(CommonResponse.withEmptyData(ErrorCode.SUCCESS));

        ResultActions resultActions = this.mockMvc.perform(RestDocumentationRequestBuilders.post("/user/login")
                .content(requestContent)
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
        );

        resultActions
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.responseCode").exists())
//                .andExpect(jsonPath("$.responseCode.code").value(ErrorCode.SUCCESS.getCode()))
//                .andExpect(jsonPath("$.responseCode.message").value(ErrorCode.SUCCESS.getMessage()))
                .andExpect((content().json(responseContent)))
                .andDo(document("user-login",
                        Preprocessors.preprocessRequest(Preprocessors.prettyPrint()),
                        Preprocessors.preprocessResponse(Preprocessors.prettyPrint()),
                        requestFields(
                                fieldWithPath("id").type(JsonFieldType.STRING).description("로그인하고자 하는 사용자의 ID"),
                                fieldWithPath("password").type(JsonFieldType.STRING).description("로그인하고자 하는 사용자의 패스워드")
                        ),
                        responseFields(
                                fieldWithPath("data").type(JsonFieldType.OBJECT).description("로그인한 유저 정보"),
                                fieldWithPath("data.id").type(JsonFieldType.STRING).description("유저 ID"),
                                fieldWithPath("data.nickname").type(JsonFieldType.STRING).description("유저 닉네임"),
                                fieldWithPath("data.description").type(JsonFieldType.STRING).description("유저 소개글"),
                                fieldWithPath("data.ciPath").type(JsonFieldType.STRING).description("(Optional)유저 로고 경로").optional(),
                                fieldWithPath("responseCode.code").type(JsonFieldType.STRING).description("응답결과 코드"),
                                fieldWithPath("responseCode.message").type(JsonFieldType.STRING).description("응답결과 메시지")
                        )
                ));
    }

    @DisplayName("회원가입 테스트")
    @Test
    void join() throws Exception {

        String id = "testid";
        String name = "testname";
        String password = "testPassword";
        String description = "test소개글";

        String requestContent = objectMapper.writeValueAsString(new JoinRequest(id, name, password, description));
        String responseContent = objectMapper.writeValueAsString(CommonResponse.withEmptyData(ErrorCode.SUCCESS));

        given(sessionInterceptor.preHandle(any(), any(), any())).willReturn(true);

        ResultActions resultActions = this.mockMvc.perform(RestDocumentationRequestBuilders.post("/user/join")
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
                .andDo(document("user-join",
                        Preprocessors.preprocessRequest(Preprocessors.prettyPrint()),
                        Preprocessors.preprocessResponse(Preprocessors.prettyPrint()),
                        requestFields(
                                fieldWithPath("id").type(JsonFieldType.STRING).description("사용자의 ID"),
                                fieldWithPath("nickname").type(JsonFieldType.STRING).description("사용자의 닉네임"),
                                fieldWithPath("password").type(JsonFieldType.STRING).description("사용자의 패스워드"),
                                fieldWithPath("description").type(JsonFieldType.STRING).description("사용자의 소개글")
                        ),
                        responseFields(
                                fieldWithPath("responseCode.code").type(JsonFieldType.STRING).description("응답결과 코드"),
                                fieldWithPath("responseCode.message").type(JsonFieldType.STRING).description("응답결과 메시지")
                        )
                ));
    }

    @DisplayName("중복체크 테스트")
    @Test
    void isDuplicate() throws Exception {

        String id = "testid";

        String responseContent = objectMapper.writeValueAsString(CommonResponse.withEmptyData(ErrorCode.SUCCESS));

        given(sessionInterceptor.preHandle(any(), any(), any())).willReturn(true);

        ResultActions resultActions = this.mockMvc.perform(RestDocumentationRequestBuilders.get("/user/is-duplicate")
                .param("type", "id")
                .param("value", id)
                .accept(MediaType.APPLICATION_JSON)
        );

        resultActions
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.responseCode").exists())
                .andExpect(jsonPath("$.responseCode.code").exists())
                .andExpect(jsonPath("$.responseCode.message").exists())
                .andExpect((content().json(responseContent)))
                .andDo(document("is-duplicate",
                        Preprocessors.preprocessRequest(Preprocessors.prettyPrint()),
                        Preprocessors.preprocessResponse(Preprocessors.prettyPrint()),
                        queryParameters(
                                parameterWithName("type").description("중복체크할 데이터의 타입(id / nickname)"),
                                parameterWithName("value").description("type에 따라 id 혹은 nickname")
                        ),
                        responseFields(
                                fieldWithPath("data").type(JsonFieldType.STRING).description("이미 존재하는 경우 Y / 존재하지 않는 경우 N"),
                                fieldWithPath("responseCode.code").type(JsonFieldType.STRING).description("응답결과 코드"),
                                fieldWithPath("responseCode.message").type(JsonFieldType.STRING).description("응답결과 메시지")
                        )
                ));
    }

    @DisplayName("Id로 유저조회")
    @Test
    void selectUserById() throws Exception {

        String id = "testid";
        String name = "testname";
        String password = "testPassword";
        String description = "testDescription";
        User user = new User(id, name, password, description);
        String responseContent = objectMapper.writeValueAsString(CommonResponse.withEmptyData(ErrorCode.SUCCESS));
        given(userService.selectUserById(any())).willReturn(user);
        given(sessionInterceptor.preHandle(any(), any(), any())).willReturn(true);

        ResultActions resultActions = this.mockMvc.perform(RestDocumentationRequestBuilders.get("/user/{id}", id)
                .accept(MediaType.APPLICATION_JSON)
        );

        resultActions
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.responseCode").exists())
                .andExpect(jsonPath("$.responseCode.code").exists())
                .andExpect(jsonPath("$.responseCode.message").exists())
                .andExpect((content().json(responseContent)))
                .andDo(document("select-user-by-id",
                        Preprocessors.preprocessRequest(Preprocessors.prettyPrint()),
                        Preprocessors.preprocessResponse(Preprocessors.prettyPrint()),
                        pathParameters(
                                parameterWithName("id").description("조회할 사용자의 ID")
                        ),
                        responseFields(
                                fieldWithPath("data").type(User.class).description("조회된 사용자 정보"),
                                fieldWithPath("data.id").type(User.class).description("ID"),
                                fieldWithPath("data.nickname").type(User.class).description("닉네임"),
                                fieldWithPath("data.description").type(User.class).description("소개"),
                                fieldWithPath("responseCode.code").type(JsonFieldType.STRING).description("응답결과 코드"),
                                fieldWithPath("responseCode.message").type(JsonFieldType.STRING).description("응답결과 메시지")
                        )
                ));
    }


    @DisplayName("회원정보수정 테스트")
    @Test
    void updateUser() throws Exception {

        String id = "testid";
        String name = "testname";
        String password = "testPassword";
        String description = "test소개글";

        String requestContent = objectMapper.writeValueAsString(new UpdateRequest(id, name, password, description));
        String responseContent = objectMapper.writeValueAsString(CommonResponse.withEmptyData(ErrorCode.SUCCESS));

        given(sessionInterceptor.preHandle(any(), any(), any())).willReturn(true);
        ResultActions resultActions = this.mockMvc.perform(RestDocumentationRequestBuilders.patch("/user")
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
                .andDo(document("user-update",
                        Preprocessors.preprocessRequest(Preprocessors.prettyPrint()),
                        Preprocessors.preprocessResponse(Preprocessors.prettyPrint()),
                        requestFields(
                                fieldWithPath("id").type(JsonFieldType.STRING).description("ID (Required)"),
                                fieldWithPath("nickname").type(JsonFieldType.STRING).description("(Optional)변경할 닉네임 ").optional(),
                                fieldWithPath("password").type(JsonFieldType.STRING).description("(Optional)변경할 패스워드").optional(),
                                fieldWithPath("description").type(JsonFieldType.STRING).description("(Optional)변경할 소개글").optional()
                        ),
                        responseFields(
                                fieldWithPath("responseCode.code").type(JsonFieldType.STRING).description("응답결과 코드"),
                                fieldWithPath("responseCode.message").type(JsonFieldType.STRING).description("응답결과 메시지")
                        )
                ));
    }

    @DisplayName("회원 소속 팀 조회 테스트")
    @Test
    void selectUserTeam() throws Exception {
        List<UserTeam> userTeams = new ArrayList<>();
        userTeams.add(new UserTeam(16, "Sunny Eleven", null, "Y"));
        String responseContent = objectMapper.writeValueAsString(new CommonResponse<>(userTeams));
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
        given(userService.selectTeamOfUser(any())).willReturn(userTeams);


        ResultActions resultActions = this.mockMvc.perform(RestDocumentationRequestBuilders.get("/user/team")
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
                .andDo(document("user-team-select",
                        Preprocessors.preprocessRequest(Preprocessors.prettyPrint()),
                        Preprocessors.preprocessResponse(Preprocessors.prettyPrint()),
                        responseFields(
                                fieldWithPath("data").type(JsonFieldType.ARRAY).description("조회한 정보"),
                                fieldWithPath("data[].id").type(JsonFieldType.NUMBER).description("팀 ID"),
                                fieldWithPath("data[].name").type(JsonFieldType.STRING).description("팀 이름"),
                                fieldWithPath("data[].ciPath").type(JsonFieldType.STRING).description("(Optional)팀 로고 경로").optional(),
                                fieldWithPath("data[].ownerYn").type(JsonFieldType.STRING).description("조회한 유저가 팀의 소유주인지 여부"),
                                fieldWithPath("responseCode.code").type(JsonFieldType.STRING).description("응답결과 코드"),
                                fieldWithPath("responseCode.message").type(JsonFieldType.STRING).description("응답결과 메시지")
                        )
                ));

    }
    @DisplayName("팀 삭제")
    @Test
    void deleteTeam() throws Exception {

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

        String teamId = "16";
        given(sessionInterceptor.preHandle(any(), any(), any())).willReturn(true);

        ResultActions resultActions =  this.mockMvc.perform( RestDocumentationRequestBuilders.delete("/user/logout")
                .header("cookie", "SESSION=" + userSession.getSessionId())
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
        );

        resultActions
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.responseCode").exists())
                .andExpect(jsonPath("$.responseCode.code").exists())
                .andExpect(jsonPath("$.responseCode.message").exists())
                .andDo(document("user-logout",
                        Preprocessors.preprocessRequest(Preprocessors.prettyPrint()),
                        Preprocessors.preprocessResponse(Preprocessors.prettyPrint()),
                        responseFields(
                                fieldWithPath("responseCode.code").type(JsonFieldType.STRING).description("응답결과 코드"),
                                fieldWithPath("responseCode.message").type(JsonFieldType.STRING).description("응답결과 메시지")
                        )
                ));
    }
}