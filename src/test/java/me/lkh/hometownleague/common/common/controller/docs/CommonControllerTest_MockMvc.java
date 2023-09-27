package me.lkh.hometownleague.common.common.controller.docs;

import com.fasterxml.jackson.databind.ObjectMapper;
import me.lkh.hometownleague.common.common.controller.CommonController;
import me.lkh.hometownleague.common.common.service.CommonService;
import me.lkh.hometownleague.common.interceptor.SessionInterceptor;
import me.lkh.hometownleague.common.response.CommonResponse;
import me.lkh.hometownleague.common.util.SessionUtil;
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
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.document;
import static org.springframework.restdocs.payload.PayloadDocumentation.fieldWithPath;
import static org.springframework.restdocs.payload.PayloadDocumentation.responseFields;
import static org.springframework.restdocs.request.RequestDocumentation.parameterWithName;
import static org.springframework.restdocs.request.RequestDocumentation.pathParameters;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@AutoConfigureRestDocs(uriHost="218.232.175.4", uriPort = 49156)
@AutoConfigureMockMvc
@WebMvcTest(CommonController.class)
class CommonControllerTest_MockMvc {

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
    private CommonService commonService;

    @MockBean
    private SessionInterceptor sessionInterceptor;

    @DisplayName("공통코드조회")
    @Test
    void selectCommonCode() throws Exception {

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

        List<Map<String, String>> responseList = new ArrayList<>();
        Map<String, String> tmpMap = new HashMap<>();
        tmpMap.put("code", "B");
        tmpMap.put("codeName", "성사");
        responseList.add(tmpMap);

        tmpMap = new HashMap<>();
        tmpMap.put("code", "E");
        tmpMap.put("codeName", "종료");
        responseList.add(tmpMap);

        tmpMap = new HashMap<>();
        tmpMap.put("code", "O");
        tmpMap.put("codeName", "한팀 수락");
        responseList.add(tmpMap);

        tmpMap = new HashMap<>();
        tmpMap.put("code", "W");
        tmpMap.put("codeName", "대기");
        responseList.add(tmpMap);

        given(commonService.selectCommonCode(any())).willReturn(responseList);

        String responseContent = objectMapper.writeValueAsString(new CommonResponse<>(responseList));

        ResultActions resultActions =  this.mockMvc.perform( RestDocumentationRequestBuilders.get("/common/code/{groupId}", "0011")
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
                .andDo(document("common-select-common-code",
                        Preprocessors.preprocessRequest(Preprocessors.prettyPrint()),
                        Preprocessors.preprocessResponse(Preprocessors.prettyPrint()),
                        pathParameters(
                                parameterWithName("groupId").description("조회할 공통코드의 그룹ID")
                        ),
                        responseFields(
                                fieldWithPath("data").type(JsonFieldType.ARRAY).description("공통코드 목록"),
                                fieldWithPath("data[].code").type(JsonFieldType.STRING).description("코드"),
                                fieldWithPath("data[].codeName").type(JsonFieldType.STRING).description("코드명"),
                                fieldWithPath("responseCode.code").type(JsonFieldType.STRING).description("응답결과 코드"),
                                fieldWithPath("responseCode.message").type(JsonFieldType.STRING).description("응답결과 메시지")
                        )
                ));
    }
}