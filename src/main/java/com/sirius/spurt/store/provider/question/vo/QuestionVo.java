package com.sirius.spurt.store.provider.question.vo;

import com.sirius.spurt.common.meta.JobGroup;
import java.sql.Timestamp;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class QuestionVo {
    private Long questionId;

    private String userId;

    private String subject;

    private String mainText;

    private JobGroup jobGroup;

    private Boolean pinIndicator;

    private Timestamp createTimestamp;

    private List<KeyWordVo> keyWordList;

    private List<CategoryVo> categoryList;
}
