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
import com.sirius.spurt.service.business.question.PutPinQuestionBusiness;
import com.sirius.spurt.service.business.question.PutQuestionBusiness;
import com.sirius.spurt.service.business.question.RandomQuestionBusiness;
import com.sirius.spurt.service.business.question.RetrieveQuestionBusiness;
import com.sirius.spurt.service.business.question.SaveQuestionBusiness;
import com.sirius.spurt.service.controller.question.QuestionController;
import com.sirius.spurt.test.CategoryTest;
import com.sirius.spurt.test.KeyWordTest;
import com.sirius.spurt.test.QuestionTest;
import java.util.List;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;

@WebMvcTest(controllers = {QuestionController.class})
public class QuestionControllerTest extends BaseMvcTest
        implements QuestionTest, KeyWordTest, CategoryTest {
    @MockBean private RetrieveQuestionBusiness retrieveQuestionBusiness;
    @MockBean private SaveQuestionBusiness saveQuestionBusiness;
    @MockBean private PutQuestionBusiness putQuestionBusiness;
    @MockBean private GetQuestionBusiness getQuestionBusiness;
    @MockBean private DeleteQuestionBusiness deleteQuestionBusiness;
    @MockBean private RandomQuestionBusiness randomQuestionBusiness;
    @MockBean private PutPinQuestionBusiness putPinQuestionBusiness;

    @Test
    void 질문_핀_수정() throws Exception {
        PutPinQuestionBusiness.Dto dto =
                PutPinQuestionBusiness.Dto.builder().questionId("2").pinIndicator(true).build();
        when(putPinQuestionBusiness.execute(any())).thenReturn(new PutPinQuestionBusiness.Result());
        this.mockMvc
                .perform(
                        put("/v1/question/pin")
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(objectMapper.writeValueAsString(dto)))
                .andExpect(status().isOk())
                .andDo(print());
    }

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
                .perform(
                        get("/v1/question/random")
                                .param("category", "CONFLICT")
                                .contentType(MediaType.APPLICATION_JSON))
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
                        .questionId(TEST_QUESTION_ID)
                        .subject("제목")
                        .jobGroup(JobGroup.DEVELOPER)
                        .mainText("본문")
                        .createTime("2023-08-13 01:39:21")
                        .userId(TEST_USER_ID)
                        .pinIndicator(Boolean.FALSE)
                        .categoryList(List.of(Category.CONFLICT))
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
                        .categoryList(List.of(Category.CONFLICT))
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
                        .categoryList(List.of(Category.CONFLICT))
                        .keyWordList(List.of("testKeyword"))
                        .build();
        SaveQuestionBusiness.Result result =
                SaveQuestionBusiness.Result.builder().questionId(TEST_QUESTION_ID).build();
        when(saveQuestionBusiness.execute(any())).thenReturn(result);
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
                                                .questionId(TEST_QUESTION_ID)
                                                .subject(TEST_QUESTION_SUBJECT)
                                                .mainText(TEST_QUESTION_MAIN_TEXT)
                                                .pinIndicator(Boolean.TRUE)
                                                .keyWordList(List.of(TEST_KEY_WORD_VALUE))
                                                .categoryList(List.of(TEST_CATEGORY))
                                                .jobGroup(TEST_JOB_GROUP)
                                                .createTime(TEST_CREATE_TIME)
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
                                .param("jobGroup", TEST_JOB_GROUP.name())
                                .param("category", TEST_CATEGORY.name()))
                .andExpect(status().isOk())
                .andDo(print());
    }
}
