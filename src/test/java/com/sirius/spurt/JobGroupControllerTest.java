package com.sirius.spurt;

import static com.sirius.spurt.common.jwt.JwtUtils.ACCESS_TOKEN_NAME;
import static com.sirius.spurt.common.jwt.JwtUtils.TOKEN_TYPE;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.sirius.spurt.common.meta.JobGroup;
import com.sirius.spurt.service.business.jobgroup.SaveJobGroupBusiness;
import com.sirius.spurt.service.business.jobgroup.UpdateJobGroupBusiness;
import com.sirius.spurt.service.controller.jobgroup.JobGroupController;
import com.sirius.spurt.test.TokenTest;
import jakarta.servlet.http.Cookie;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;

@WebMvcTest(controllers = {JobGroupController.class})
public class JobGroupControllerTest extends BaseMvcTest implements TokenTest {
    @MockBean private SaveJobGroupBusiness saveJobGroupBusiness;
    @MockBean private UpdateJobGroupBusiness updateJobGroupBusiness;

    @Test
    void 유저_직군_저장() throws Exception {
        SaveJobGroupBusiness.Dto dto =
                SaveJobGroupBusiness.Dto.builder().jobGroup(JobGroup.DEVELOPER).build();
        when(saveJobGroupBusiness.execute(any())).thenReturn(new SaveJobGroupBusiness.Result());
        this.mockMvc
                .perform(
                        post("/v1/jobgroup")
                                .cookie(new Cookie(ACCESS_TOKEN_NAME, TOKEN_TYPE + TEST_TOKEN_VALUE))
                                .requestAttr("userId", "admin")
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(objectMapper.writeValueAsString(dto)))
                .andExpect(status().isOk())
                .andDo(print());
    }

    @Test
    void 유저_직군_수정() throws Exception {
        UpdateJobGroupBusiness.Dto dto =
                UpdateJobGroupBusiness.Dto.builder().jobGroup(JobGroup.DEVELOPER).build();
        when(updateJobGroupBusiness.execute(any())).thenReturn(new UpdateJobGroupBusiness.Result());
        this.mockMvc
                .perform(
                        put("/v1/jobgroup")
                                .requestAttr("userId", "admin")
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(objectMapper.writeValueAsString(dto))
                                .principal(this.mockPrincipal))
                .andExpect(status().isOk())
                .andDo(print());
    }
}
