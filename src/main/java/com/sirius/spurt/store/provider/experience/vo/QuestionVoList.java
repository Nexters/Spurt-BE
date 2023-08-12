package com.sirius.spurt.store.provider.experience.vo;

import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class QuestionVoList {
    private List<QuestionVo> questionVoList;
    private Integer totalCount;
}
