package me.lkh.hometownleague.rank.controller.docs;

import com.fasterxml.jackson.databind.ObjectMapper;
import me.lkh.hometownleague.common.interceptor.SessionInterceptor;
import me.lkh.hometownleague.common.response.CommonResponse;
import me.lkh.hometownleague.common.util.SessionUtil;
import me.lkh.hometownleague.rank.controller.RankController;
import me.lkh.hometownleague.rank.domain.Ranking;
import me.lkh.hometownleague.rank.service.RankService;
import me.lkh.hometownleague.session.domain.UserSession;
import me.lkh.hometownleague.session.service.SessionService;
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
@WebMvcTest(RankController.class)
public class RankControllerTest_MockMvc {

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
    private RankService rankService;

    @MockBean
    private SessionInterceptor sessionInterceptor;
    @DisplayName("상위 랭킹목록 조회")
    @Test
    void selectRankingTop() throws Exception {

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

        List<Ranking> responseList = new ArrayList<>();
        responseList.add(new Ranking(1, 387, "387.png", "Sunny Eleven", 1580, 13));
        responseList.add(new Ranking(2, 5, "5.png", "FC Seoul", 1576, 15));

        given(rankService.selectRankingList(any())).willReturn(responseList);

        String responseContent = objectMapper.writeValueAsString(new CommonResponse<>(responseList));

        ResultActions resultActions =  this.mockMvc.perform( RestDocumentationRequestBuilders.get("/rank/{numOfTeam}", 2)
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
                .andDo(document("rank-select-top-ranking",
                        Preprocessors.preprocessRequest(Preprocessors.prettyPrint()),
                        Preprocessors.preprocessResponse(Preprocessors.prettyPrint()),
                        pathParameters(
                                parameterWithName("numOfTeam").description("랭킹을 조회할 개수 (10 - Top 10")
                        ),
                        responseFields(
                                fieldWithPath("data").type(JsonFieldType.ARRAY).description("경기 결과 목록"),
                                fieldWithPath("data[].rank").type(JsonFieldType.NUMBER).description("랭킹"),
                                fieldWithPath("data[].id").type(JsonFieldType.NUMBER).description("팀ID"),
                                fieldWithPath("data[].name").type(JsonFieldType.STRING).description("팀명"),
                                fieldWithPath("data[].ciPath").type(JsonFieldType.STRING).description("팀 로고명"),
                                fieldWithPath("data[].rankScore").type(JsonFieldType.NUMBER).description("팀 점수"),
                                fieldWithPath("data[].playerCount").type(JsonFieldType.NUMBER).description("팀 소속선수 명수"),
                                fieldWithPath("responseCode.code").type(JsonFieldType.STRING).description("응답결과 코드"),
                                fieldWithPath("responseCode.message").type(JsonFieldType.STRING).description("응답결과 메시지")
                        )
                ));
    }
}
