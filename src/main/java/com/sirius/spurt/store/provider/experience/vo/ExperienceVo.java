package com.sirius.spurt.store.provider.experience.vo;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ExperienceVo {
    private Long experienceId;
    private String title;
    private String content;
    private String startDate;
    private String endDate;
    private String link;
    private QuestionVoList questionVoList;
}
