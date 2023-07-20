package com.sirius.spurt;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.sirius.spurt.service.business.question.GetQuestionBusiness;
import com.sirius.spurt.service.business.question.RetrieveQuestionBusiness;
import com.sirius.spurt.service.business.question.SaveQuestionBusiness;
import com.sirius.spurt.service.controller.question.QuestionController;
import java.sql.Timestamp;
import java.time.Instant;
import java.util.List;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;

@WebMvcTest(controllers = {QuestionController.class})
public class QuestionControllerTest extends BaceMvcTest {
    @MockBean private RetrieveQuestionBusiness retrieveQuestionBusiness;
    @MockBean private SaveQuestionBusiness saveQuestionBusiness;
    @MockBean private GetQuestionBusiness getQuestionBusiness;

    @Test
    void 질문_단건_조회() throws Exception {
        GetQuestionBusiness.Dto dto = GetQuestionBusiness.Dto.builder().questionId("1").build();
        GetQuestionBusiness.Result result =
                GetQuestionBusiness.Result.builder()
                        .subject("제목")
                        .category("category")
                        .jobGroup("jobgroup")
                        .mainText("본문")
                        .createTimestamp(Timestamp.from(Instant.now()))
                        .build();
        when(getQuestionBusiness.execute(any())).thenReturn(result);
        this.mockMvc.perform(get("/v1/question/1")).andExpect(status().isOk()).andDo(print());
    }

    @Test
    void 질문_저장() throws Exception {
        SaveQuestionBusiness.Dto dto =
                SaveQuestionBusiness.Dto.builder()
                        .subject("test 질문")
                        .mainText("test 내용")
                        .jobGroup("jobgroup")
                        .category("category")
                        .keyWord(List.of())
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
    void 질문_검색() throws Exception {

        RetrieveQuestionBusiness.Result result =
                RetrieveQuestionBusiness.Result.builder()
                        .question(
                                List.of(
                                        RetrieveQuestionBusiness.Result.Question.builder()
                                                .subject("제목")
                                                .category("category")
                                                .jobGroup("jobgroup")
                                                .mainText("본문")
                                                .createTimestamp(Timestamp.from(Instant.now()))
                                                .build()))
                        .meta(
                                RetrieveQuestionBusiness.Result.MetaData.builder()
                                        .totalCount(2)
                                        .pageableCount(1)
                                        .isEnd(true)
                                        .build())
                        .build();

        when(retrieveQuestionBusiness.execute(any())).thenReturn(result);
        this.mockMvc
                .perform(
                        get("/v1/question")
                                .param("subject", "제목")
                                .param("jobGroup", "1")
                                .param("category", "1"))
                .andExpect(status().isOk())
                .andDo(print());
    }
}
