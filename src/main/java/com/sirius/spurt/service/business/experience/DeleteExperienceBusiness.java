package com.sirius.spurt.service.business.experience;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.sirius.spurt.common.template.Business;
import com.sirius.spurt.service.business.experience.DeleteExperienceBusiness.Dto;
import com.sirius.spurt.service.business.experience.DeleteExperienceBusiness.Result;
import com.sirius.spurt.store.provider.experience.ExperienceProvider;
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
public class DeleteExperienceBusiness implements Business<Dto, Result> {
    private final ExperienceProvider experienceProvider;

    @Override
    public Result execute(Dto input) {
        experienceProvider.deleteExperience(input.getExperienceId(), input.getUserId());
        return new Result();
    }

    @JsonIgnoreProperties
    @Data
    @Validated
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    public static class Dto implements Business.Dto, Serializable {
        /** experienceId */
        private Long experienceId;
        /** userId 프론트 전달 x */
        private String userId;
    }

    @JsonIgnoreProperties
    public static class Result implements Business.Result, Serializable {}
}
