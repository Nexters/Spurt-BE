package com.sirius.spurt.service.business.experience;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.sirius.spurt.common.template.Business;
import com.sirius.spurt.service.business.experience.SaveExperienceBusiness.Dto;
import com.sirius.spurt.service.business.experience.SaveExperienceBusiness.Result;
import com.sirius.spurt.store.provider.experience.ExperienceProvider;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import java.io.Serializable;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.validation.annotation.Validated;

@Component
@RequiredArgsConstructor
public class SaveExperienceBusiness implements Business<Dto, Result> {
    private final ExperienceProvider experienceProvider;

    @Override
    public Result execute(Dto input) {
        experienceProvider.saveExperience(
                input.getTitle(),
                input.getContent(),
                input.getStartDate(),
                input.getEndDate(),
                input.getLink(),
                input.getUserId());
        return new Result();
    }

    @JsonIgnoreProperties
    @Data
    @Validated
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    public static class Dto implements Business.Dto, Serializable {
        /** 제목 (최대 30자) */
        @Size(max = 30)
        @NotBlank
        private String title;
        /** 본문 (최대 1000자) */
        @Size(max = 1000)
        @NotBlank
        private String content;
        /** 시작 날짜 */
        private String startDate;
        /** 종료 날짜 */
        private String endDate;
        /** 관련 링크 */
        private String link;
        /** userId 프론트 전달 x */
        private String userId;
    }

    @JsonIgnoreProperties
    public static class Result implements Business.Result, Serializable {}
}
