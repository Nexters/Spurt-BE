package com.sirius.spurt;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.sirius.spurt.common.meta.Category;
import com.sirius.spurt.service.business.experience.DeleteExperienceBusiness;
import com.sirius.spurt.service.business.experience.GetAllExperienceBusiness;
import com.sirius.spurt.service.business.experience.GetExperienceBusiness;
import com.sirius.spurt.service.business.experience.SaveExperienceBusiness;
import com.sirius.spurt.service.business.experience.UpdateExperienceBusiness;
import com.sirius.spurt.service.controller.experience.ExperienceController;
import java.util.List;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;

@WebMvcTest(controllers = {ExperienceController.class})
public class ExperienceControllerTest extends BaseMvcTest {
    @MockBean private SaveExperienceBusiness saveExperienceBusiness;
    @MockBean private UpdateExperienceBusiness updateExperienceBusiness;
    @MockBean private DeleteExperienceBusiness deleteExperienceBusiness;
    @MockBean private GetAllExperienceBusiness getAllExperienceBusiness;
    @MockBean private GetExperienceBusiness getExperienceBusiness;

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

    @Test
    void 본인_경험_전체_조회() throws Exception {
        GetAllExperienceBusiness.Dto dto =
                GetAllExperienceBusiness.Dto.builder().userId("admin").build();
        GetAllExperienceBusiness.Result.Experience.QuestionList.Question question =
                GetAllExperienceBusiness.Result.Experience.QuestionList.Question.builder()
                        .questionId(1L)
                        .subject("질문제목1")
                        .mainText("질문내용1")
                        .pinIndicator(false)
                        .categoryList(List.of(Category.EXPERIENCE))
                        .build();
        GetAllExperienceBusiness.Result.Experience.QuestionList questionList =
                GetAllExperienceBusiness.Result.Experience.QuestionList.builder()
                        .questionList(List.of(question))
                        .totalCount(1)
                        .build();
        GetAllExperienceBusiness.Result.Experience experience =
                GetAllExperienceBusiness.Result.Experience.builder()
                        .experienceId(1L)
                        .title("제목")
                        .content("내용")
                        .startDate("2023-07")
                        .endDate("2023-08")
                        .link("link")
                        .questionList(questionList)
                        .build();
        GetAllExperienceBusiness.Result result =
                GetAllExperienceBusiness.Result.builder().experienceList(List.of(experience)).build();
        when(getAllExperienceBusiness.execute(dto)).thenReturn(result);
        this.mockMvc.perform(get("/v1/experience")).andExpect(status().isOk()).andDo(print());
    }

    @Test
    void 본인_경험_단건_조회() throws Exception {
        GetExperienceBusiness.Dto dto =
                GetExperienceBusiness.Dto.builder().experienceId(1L).userId("admin").build();
        GetExperienceBusiness.Result.QuestionList.Question question =
                GetExperienceBusiness.Result.QuestionList.Question.builder()
                        .questionId(1L)
                        .subject("질문제목1")
                        .mainText("질문내용1")
                        .pinIndicator(false)
                        .categoryList(List.of(Category.EXPERIENCE))
                        .build();
        GetExperienceBusiness.Result.QuestionList questionList =
                GetExperienceBusiness.Result.QuestionList.builder()
                        .questionList(List.of(question))
                        .totalCount(1)
                        .build();
        GetExperienceBusiness.Result result =
                GetExperienceBusiness.Result.builder()
                        .experienceId(1L)
                        .title("제목")
                        .content("내용")
                        .startDate("2023-07")
                        .endDate("2023-08")
                        .link("link")
                        .questionList(questionList)
                        .build();
        when(getExperienceBusiness.execute(dto)).thenReturn(result);
        this.mockMvc.perform(get("/v1/experience/1")).andExpect(status().isOk()).andDo(print());
    }
}
