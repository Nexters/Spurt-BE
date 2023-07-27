package com.sirius.spurt;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.sirius.spurt.common.meta.JobGroup;
import com.sirius.spurt.service.business.jobgroup.SaveJobGroupBusiness;
import com.sirius.spurt.service.controller.jobgroup.JobGroupController;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;

@WebMvcTest(controllers = {JobGroupController.class})
public class JobGroupControllerTest extends BaseMvcTest {
    @MockBean private SaveJobGroupBusiness saveJobGroupBusiness;

    @Test
    void 유저_직군_저장() throws Exception {
        SaveJobGroupBusiness.Dto dto =
                SaveJobGroupBusiness.Dto.builder().userId("admin").jobGroup(JobGroup.DEVELOPER).build();
        when(saveJobGroupBusiness.execute(dto)).thenReturn(null);
        this.mockMvc
                .perform(
                        post("/v1/jobgroup")
                                .requestAttr("userId", "admin")
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(objectMapper.writeValueAsString(dto)))
                .andExpect(status().isOk())
                .andDo(print());
    }
}
