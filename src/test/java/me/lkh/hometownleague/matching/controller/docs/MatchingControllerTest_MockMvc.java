package me.lkh.hometownleague.matching.controller.docs;

import com.fasterxml.jackson.databind.ObjectMapper;
import me.lkh.hometownleague.common.exception.ErrorCode;
import me.lkh.hometownleague.common.interceptor.SessionInterceptor;
import me.lkh.hometownleague.common.response.CommonResponse;
import me.lkh.hometownleague.common.util.SessionUtil;
import me.lkh.hometownleague.matching.controller.MatchingController;
import me.lkh.hometownleague.matching.service.MatchingService;
import me.lkh.hometownleague.session.domain.UserSession;
import me.lkh.hometownleague.session.service.SessionService;
import me.lkh.hometownleague.team.domain.TeamPlayLocation;
import me.lkh.hometownleague.team.domain.TeamPlayTime;
import me.lkh.hometownleague.team.domain.request.MakeJoinAcceptRequest;
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
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.document;
import static org.springframework.restdocs.payload.PayloadDocumentation.*;
import static org.springframework.restdocs.payload.PayloadDocumentation.fieldWithPath;
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
        // 세션 관련 End ======================================================================

        Map<String, Object> requestBody = new HashMap<>();
        requestBody.put("teamId", 16);
        String requestContent = objectMapper.writeValueAsString(requestBody);
        String responseContent = objectMapper.writeValueAsString(CommonResponse.withEmptyData(ErrorCode.SUCCESS));

        ResultActions resultActions =  this.mockMvc.perform( RestDocumentationRequestBuilders.post("/matching/request")
                .header("cookie", "SESSION=" + userSession.getSessionId())
                .content(requestContent)
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
}
