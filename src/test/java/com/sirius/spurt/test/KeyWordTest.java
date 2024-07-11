package com.sirius.spurt.test;

import com.sirius.spurt.store.repository.database.entity.KeyWordEntity;

public interface KeyWordTest extends QuestionTest {
    Long TEST_KEY_WORD_ID = 1L;
    String TEST_KEY_WORD_VALUE = "keyWord";

    Long TEST_ANOTHER_KEY_WORD_ID = 2L;
    String TEST_ANOTHER_KEY_WORD_VALUE = "anotherKeyWord";

    KeyWordEntity TEST_KEY_WORD =
            KeyWordEntity.builder()
                    .keyWordId(TEST_KEY_WORD_ID)
                    .questionId(TEST_QUESTION_ID)
                    .keyWord(TEST_KEY_WORD_VALUE)
                    .build();

    KeyWordEntity TEST_ANOTHER_KEY_WORD =
            KeyWordEntity.builder()
                    .keyWordId(TEST_ANOTHER_KEY_WORD_ID)
                    .questionId(TEST_QUESTION_ID)
                    .keyWord(TEST_ANOTHER_KEY_WORD_VALUE)
                    .build();
}
