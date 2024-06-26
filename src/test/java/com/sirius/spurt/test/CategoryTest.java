package com.sirius.spurt.test;

import com.sirius.spurt.common.meta.Category;
import com.sirius.spurt.store.repository.database.entity.CategoryEntity;

public interface CategoryTest extends QuestionTest {
    Long TEST_CATEGORY_ID = 1L;
    Long TEST_ANOTHER_CATEGORY_ID = 2L;
    Category TEST_CATEGORY = Category.MAJOR;
    Category TEST_ANOTHER_CATEGORY = Category.ALL;

    CategoryEntity TEST_CATEGORY_ENTITY =
            CategoryEntity.builder()
                    .categoryId(TEST_CATEGORY_ID)
                    .questionId(TEST_QUESTION_ID)
                    .category(TEST_CATEGORY)
                    .build();

    CategoryEntity TEST_ANOTHER_CATEGORY_ENTITY =
            CategoryEntity.builder()
                    .categoryId(TEST_ANOTHER_CATEGORY_ID)
                    .questionId(TEST_ANOTHER_QUESTION_ID)
                    .category(TEST_CATEGORY)
                    .build();
}
