package com.sirius.spurt;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.sirius.spurt.common.meta.JobGroup;
import com.sirius.spurt.common.meta.LoginUser;
import com.sirius.spurt.service.business.jobgroup.SaveJobGroupBusiness;
import com.sirius.spurt.service.business.jobgroup.UpdateJobGroupBusiness;
import com.sirius.spurt.service.controller.jobgroup.JobGroupController;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;

@WebMvcTest(controllers = {JobGroupController.class})
public class JobGroupControllerTest extends BaseMvcTest {
    @MockBean private SaveJobGroupBusiness saveJobGroupBusiness;
    @MockBean private UpdateJobGroupBusiness updateJobGroupBusiness;

    @Test
    void 유저_직군_저장() throws Exception {
        LoginUser loginUser = new LoginUser("admin", "email");
        SaveJobGroupBusiness.Dto dto =
                SaveJobGroupBusiness.Dto.builder()
                        .loginUser(loginUser)
                        .jobGroup(JobGroup.DEVELOPER)
                        .build();
        when(saveJobGroupBusiness.execute(dto)).thenReturn(new SaveJobGroupBusiness.Result());
        this.mockMvc
                .perform(
                        post("/v1/jobgroup")
                                .requestAttr("userId", "admin")
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(objectMapper.writeValueAsString(dto)))
                .andExpect(status().isOk())
                .andDo(print());
    }

    @Test
    void 유저_직군_수정() throws Exception {
        LoginUser loginUser = new LoginUser("admin", "email");
        UpdateJobGroupBusiness.Dto dto =
                UpdateJobGroupBusiness.Dto.builder()
                        .loginUser(loginUser)
                        .jobGroup(JobGroup.DEVELOPER)
                        .build();
        when(updateJobGroupBusiness.execute(dto)).thenReturn(new UpdateJobGroupBusiness.Result());
        this.mockMvc
                .perform(
                        put("/v1/jobgroup")
                                .requestAttr("userId", "admin")
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(objectMapper.writeValueAsString(dto)))
                .andExpect(status().isOk())
                .andDo(print());
    }
}
