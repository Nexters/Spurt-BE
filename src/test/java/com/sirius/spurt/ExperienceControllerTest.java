package com.sirius.spurt;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.sirius.spurt.service.business.experience.DeleteExperienceBusiness;
import com.sirius.spurt.service.business.experience.SaveExperienceBusiness;
import com.sirius.spurt.service.business.experience.UpdateExperienceBusiness;
import com.sirius.spurt.service.controller.experience.ExperienceController;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;

@WebMvcTest(controllers = {ExperienceController.class})
public class ExperienceControllerTest extends BaseMvcTest {
    @MockBean private SaveExperienceBusiness saveExperienceBusiness;
    @MockBean private UpdateExperienceBusiness updateExperienceBusiness;
    @MockBean private DeleteExperienceBusiness deleteExperienceBusiness;

    @Test
    void 본인_경험_저장() throws Exception {
        SaveExperienceBusiness.Dto dto =
                SaveExperienceBusiness.Dto.builder()
                        .title("title")
                        .content("content")
                        .startDate("2023-07")
                        .endDate("2023-08")
                        .link("link")
                        .userId("admin")
                        .build();
        when(saveExperienceBusiness.execute(dto)).thenReturn(new SaveExperienceBusiness.Result());
        this.mockMvc
                .perform(
                        post("/v1/experience")
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(objectMapper.writeValueAsString(dto)))
                .andExpect(status().isOk())
                .andDo(print());
    }

    @Test
    void 본인_경험_수정() throws Exception {
        UpdateExperienceBusiness.Dto dto =
                UpdateExperienceBusiness.Dto.builder()
                        .experienceId(1L)
                        .title("title")
                        .content("content")
                        .startDate("2023-07")
                        .endDate("2023-08")
                        .link("link")
                        .userId("admin")
                        .build();
        when(updateExperienceBusiness.execute(dto)).thenReturn(new UpdateExperienceBusiness.Result());
        this.mockMvc
                .perform(
                        put("/v1/experience")
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(objectMapper.writeValueAsString(dto)))
                .andExpect(status().isOk())
                .andDo(print());
    }

    @Test
    void 본인_경험_삭제() throws Exception {
        DeleteExperienceBusiness.Dto dto =
                DeleteExperienceBusiness.Dto.builder().experienceId(1L).userId("admin").build();
        when(deleteExperienceBusiness.execute(dto)).thenReturn(new DeleteExperienceBusiness.Result());
        this.mockMvc
                .perform(
                        delete("/v1/experience")
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(objectMapper.writeValueAsString(dto)))
                .andExpect(status().isOk())
                .andDo(print());
    }
}
