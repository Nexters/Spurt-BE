package com.sirius.spurt.store.provider.question;

import com.sirius.spurt.common.meta.Category;
import com.sirius.spurt.common.meta.JobGroup;
import java.util.List;

public interface QuestionProvider {
    void saveQuestion(
            final String subject,
            final String mainText,
            final List<String> keyWordList,
            final List<Category> categoryList,
            final JobGroup jobGroup,
            final String userId);
}
