package com.sirius.spurt.store.provider.experience.vo;

import com.sirius.spurt.common.meta.JobGroup;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
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
    private List<KeyWordVo> keyWordVoList;
    private List<CategoryVo> categoryVoList;
}
