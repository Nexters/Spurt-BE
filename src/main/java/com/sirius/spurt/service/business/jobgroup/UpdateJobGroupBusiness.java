package com.sirius.spurt.service.business.jobgroup;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.sirius.spurt.common.meta.JobGroup;
import com.sirius.spurt.common.template.Business;
import com.sirius.spurt.service.business.jobgroup.UpdateJobGroupBusiness.Dto;
import com.sirius.spurt.service.business.jobgroup.UpdateJobGroupBusiness.Result;
import com.sirius.spurt.store.provider.jobgroup.JobGroupProvider;
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
public class UpdateJobGroupBusiness implements Business<Dto, Result> {
    private final JobGroupProvider jobGroupProvider;

    @Override
    public Result execute(Dto input) {
        jobGroupProvider.updateJobGroup(input.getUserId(), input.getEmail(), input.getJobGroup());

        return new UpdateJobGroupBusiness.Result();
    }

    @JsonIgnoreProperties
    @Data
    @Validated
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    public static class Dto implements Business.Dto, Serializable {
        /** Userid 프론트 전달 x */
        private String userId;

        private String email;
        /** 직군 */
        private JobGroup jobGroup;
    }

    @JsonIgnoreProperties
    public static class Result implements Business.Result, Serializable {}
}
