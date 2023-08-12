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
import com.sirius.spurt.common.meta.JobGroup;
import com.sirius.spurt.service.business.question.DeleteQuestionBusiness;
import com.sirius.spurt.service.business.question.GetQuestionBusiness;
import com.sirius.spurt.service.business.question.PutQuestionBusiness;
import com.sirius.spurt.service.business.question.RandomQuestionBusiness;
import com.sirius.spurt.service.business.question.RetrieveQuestionBusiness;
import com.sirius.spurt.service.business.question.SaveQuestionBusiness;
import com.sirius.spurt.service.controller.question.QuestionController;
import java.util.List;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;

@WebMvcTest(controllers = {QuestionController.class})
public class QuestionControllerTest extends BaseMvcTest {
    @MockBean private RetrieveQuestionBusiness retrieveQuestionBusiness;
    @MockBean private SaveQuestionBusiness saveQuestionBusiness;
    @MockBean private PutQuestionBusiness putQuestionBusiness;
    @MockBean private GetQuestionBusiness getQuestionBusiness;
    @MockBean private DeleteQuestionBusiness deleteQuestionBusiness;
    @MockBean private RandomQuestionBusiness randomQuestionBusiness;

    @Test
    void 질문_랜덤_조회() throws Exception {
        RandomQuestionBusiness.Result result =
                RandomQuestionBusiness.Result.builder()
                        .questions(
                                List.of(
                                        RandomQuestionBusiness.Result.Question.builder()
                                                .jobGroup(JobGroup.DESIGNER)
                                                .subject("subject")
                                                .build()))
                        .build();
        when(randomQuestionBusiness.execute(any())).thenReturn(result);
        this.mockMvc
                .perform(get("/v1/question/random").contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andDo(print());
    }

    @Test
    void 질문_삭제() throws Exception {
        DeleteQuestionBusiness.Dto dto = DeleteQuestionBusiness.Dto.builder().questionId("1").build();
        when(deleteQuestionBusiness.execute(any())).thenReturn(new DeleteQuestionBusiness.Result());
        this.mockMvc
                .perform(
                        delete("/v1/question")
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(objectMapper.writeValueAsString(dto)))
                .andExpect(status().isOk())
                .andDo(print());
    }

    @Test
    void 질문_단건_조회() throws Exception {
        GetQuestionBusiness.Dto dto =
                GetQuestionBusiness.Dto.builder().questionId(Long.parseLong("1")).build();
        GetQuestionBusiness.Result result =
                GetQuestionBusiness.Result.builder()
                        .subject("제목")
                        .jobGroup(JobGroup.DEVELOPER)
                        .mainText("본문")
                        .createTime("2023-08-13 01:39:21")
                        .categoryList(List.of(Category.MOTVE))
                        .keyWordList(List.of("키워드"))
                        .build();
        when(getQuestionBusiness.execute(any())).thenReturn(result);
        this.mockMvc.perform(get("/v1/question/1")).andExpect(status().isOk()).andDo(print());
    }

    @Test
    void 질문_수정() throws Exception {
        PutQuestionBusiness.Dto dto =
                PutQuestionBusiness.Dto.builder()
                        .questionId("2")
                        .subject("test 질문")
                        .mainText("test 내용")
                        .jobGroup(JobGroup.DEVELOPER)
                        .categoryList(List.of(Category.MOTVE))
                        .keyWordList(List.of("testKeyword"))
                        .build();
        when(putQuestionBusiness.execute(any())).thenReturn(new PutQuestionBusiness.Result());
        this.mockMvc
                .perform(
                        put("/v1/question")
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(objectMapper.writeValueAsString(dto)))
                .andExpect(status().isOk())
                .andDo(print());
    }

    @Test
    void 질문_저장() throws Exception {
        SaveQuestionBusiness.Dto dto =
                SaveQuestionBusiness.Dto.builder()
                        .subject("test 질문")
                        .mainText("test 내용")
                        .jobGroup(JobGroup.DEVELOPER)
                        .categoryList(List.of(Category.MOTVE))
                        .keyWordList(List.of("testKeyword"))
                        .build();
        when(saveQuestionBusiness.execute(any())).thenReturn(new SaveQuestionBusiness.Result());
        this.mockMvc
                .perform(
                        post("/v1/question")
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(objectMapper.writeValueAsString(dto)))
                .andExpect(status().isOk())
                .andDo(print());
    }

    @Test
    void 질문_조회() throws Exception {

        RetrieveQuestionBusiness.Result result =
                RetrieveQuestionBusiness.Result.builder()
                        .questions(
                                List.of(
                                        RetrieveQuestionBusiness.Result.Question.builder()
                                                .subject("제목")
                                                .categoryList(List.of(Category.MOTVE))
                                                .jobGroup(JobGroup.DEVELOPER)
                                                .mainText("본문")
                                                .createTime("2023-08-13 01:39:21")
                                                .build()))
                        .meta(
                                RetrieveQuestionBusiness.Result.MetaData.builder()
                                        .totalCount(Long.parseLong("1"))
                                        .totalPage(1)
                                        .build())
                        .build();

        when(retrieveQuestionBusiness.execute(any())).thenReturn(result);
        this.mockMvc
                .perform(
                        get("/v1/question")
                                .param("subject", "제목")
                                .param("pinIndicator", "false")
                                .param("jobGroup", JobGroup.DEVELOPER.name())
                                .param("category", Category.MOTVE.name()))
                .andExpect(status().isOk())
                .andDo(print());
    }
}
