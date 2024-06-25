package com.sirius.spurt;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.sirius.spurt.common.meta.Category;
import com.sirius.spurt.common.resolver.user.LoginUser;
import com.sirius.spurt.service.business.experience.DeleteExperienceBusiness;
import com.sirius.spurt.service.business.experience.GetAllExperienceBusiness;
import com.sirius.spurt.service.business.experience.GetExperienceBusiness;
import com.sirius.spurt.service.business.experience.SaveExperienceBusiness;
import com.sirius.spurt.service.business.experience.UpdateExperienceBusiness;
import com.sirius.spurt.service.controller.experience.ExperienceController;
import com.sirius.spurt.test.ExperienceTest;
import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;

@WebMvcTest(controllers = {ExperienceController.class})
public class ExperienceControllerTest extends BaseMvcTest implements ExperienceTest {
    @MockBean private SaveExperienceBusiness saveExperienceBusiness;
    @MockBean private UpdateExperienceBusiness updateExperienceBusiness;
    @MockBean private DeleteExperienceBusiness deleteExperienceBusiness;
    @MockBean private GetAllExperienceBusiness getAllExperienceBusiness;
    @MockBean private GetExperienceBusiness getExperienceBusiness;

    private LoginUser loginUser;

    @BeforeEach
    void setUp() {
        loginUser = LoginUser.builder().userId(TEST_USER_ID).email(TEST_EMAIL).build();
    }

    @Test
    void 본인_경험_저장() throws Exception {
        SaveExperienceBusiness.Dto dto =
                SaveExperienceBusiness.Dto.builder()
                        .title("title")
                        .content("content")
                        .startDate("2023-07")
                        .endDate("2023-08")
                        .link("link")
                        .build();
        SaveExperienceBusiness.Result result =
                SaveExperienceBusiness.Result.builder().experienceId(TEST_EXPERIENCE_ID).build();
        when(saveExperienceBusiness.execute(any())).thenReturn(result);
        this.mockMvc
                .perform(
                        post("/v1/experience")
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(objectMapper.writeValueAsString(dto))
                                .with(
                                        request -> {
                                            request.setAttribute("loginUser", loginUser);
                                            return request;
                                        }))
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
                        .build();
        when(updateExperienceBusiness.execute(any())).thenReturn(new UpdateExperienceBusiness.Result());
        this.mockMvc
                .perform(
                        put("/v1/experience")
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(objectMapper.writeValueAsString(dto))
                                .with(
                                        request -> {
                                            request.setAttribute("loginUser", loginUser);
                                            return request;
                                        }))
                .andExpect(status().isOk())
                .andDo(print());
    }

    @Test
    void 본인_경험_삭제() throws Exception {
        DeleteExperienceBusiness.Dto dto =
                DeleteExperienceBusiness.Dto.builder().experienceId(1L).build();
        when(deleteExperienceBusiness.execute(any())).thenReturn(new DeleteExperienceBusiness.Result());
        this.mockMvc
                .perform(
                        delete("/v1/experience")
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(objectMapper.writeValueAsString(dto))
                                .with(
                                        request -> {
                                            request.setAttribute("loginUser", loginUser);
                                            return request;
                                        }))
                .andExpect(status().isOk())
                .andDo(print());
    }

    @Test
    void 본인_경험_전체_조회() throws Exception {
        GetAllExperienceBusiness.Result.Experience.QuestionList.Question question =
                GetAllExperienceBusiness.Result.Experience.QuestionList.Question.builder()
                        .questionId(1L)
                        .subject("질문제목1")
                        .mainText("질문내용1")
                        .pinIndicator(Boolean.FALSE)
                        .categoryList(List.of(Category.PRACTICAL))
                        .keyWordList(List.of("keyWord1", "keyWord2"))
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
                GetAllExperienceBusiness.Result.builder()
                        .experienceList(List.of(experience))
                        .totalCount(1)
                        .build();
        when(getAllExperienceBusiness.execute(any())).thenReturn(result);
        this.mockMvc
                .perform(
                        get("/v1/experience")
                                .with(
                                        request -> {
                                            request.setAttribute("loginUser", loginUser);
                                            return request;
                                        }))
                .andExpect(status().isOk())
                .andDo(print());
    }

    @Test
    void 본인_경험_단건_조회() throws Exception {
        GetExperienceBusiness.Result.QuestionList.Question question =
                GetExperienceBusiness.Result.QuestionList.Question.builder()
                        .questionId(1L)
                        .subject("질문제목1")
                        .mainText("질문내용1")
                        .pinIndicator(Boolean.FALSE)
                        .categoryList(List.of(Category.PRACTICAL))
                        .keyWordList(List.of("keyWord1", "keyWord2"))
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
        when(getExperienceBusiness.execute(any())).thenReturn(result);
        this.mockMvc
                .perform(
                        get("/v1/experience/1")
                                .with(
                                        request -> {
                                            request.setAttribute("loginUser", loginUser);
                                            return request;
                                        }))
                .andExpect(status().isOk())
                .andDo(print());
    }
}
