package com.sirius.spurt.service.business.question;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;

import com.sirius.spurt.service.business.question.PutPinQuestionBusiness.Dto;
import com.sirius.spurt.store.provider.question.QuestionProvider;
import com.sirius.spurt.test.QuestionTest;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class PutPinQuestionBusinessTest implements QuestionTest {
    @InjectMocks private PutPinQuestionBusiness putPinQuestionBusiness;

    @Mock private QuestionProvider questionProvider;

    @Test
    void 질문_핀_수정_테스트() {
        // given
        Dto dto =
                Dto.builder()
                        .questionId(String.valueOf(TEST_QUESTION_ID))
                        .userId(TEST_USER_ID)
                        .pinIndicator(Boolean.TRUE)
                        .build();

        // when
        putPinQuestionBusiness.execute(dto);

        // then
        verify(questionProvider).putPinQuestion(any(), any(), any());
    }
}
