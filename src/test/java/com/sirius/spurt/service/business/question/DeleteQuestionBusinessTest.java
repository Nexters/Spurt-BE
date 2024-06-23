package com.sirius.spurt.service.business.question;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;

import com.sirius.spurt.service.business.question.DeleteQuestionBusiness.Dto;
import com.sirius.spurt.store.provider.question.QuestionProvider;
import com.sirius.spurt.test.QuestionTest;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class DeleteQuestionBusinessTest implements QuestionTest {
    @InjectMocks private DeleteQuestionBusiness deleteQuestionBusiness;

    @Mock private QuestionProvider questionProvider;

    @Test
    void 질문_삭제_테스트() {
        // given
        Dto dto =
                Dto.builder().questionId(String.valueOf(TEST_QUESTION_ID)).userId(TEST_USER_ID).build();

        // when
        deleteQuestionBusiness.execute(dto);

        // then
        verify(questionProvider).deleteQuestion(any(), any());
    }
}
