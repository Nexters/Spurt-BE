package com.sirius.spurt.service.business.jobgroup;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.sirius.spurt.common.meta.JobGroup;
import com.sirius.spurt.common.template.Business;
import com.sirius.spurt.service.business.jobgroup.SaveJobGroupBusiness.Dto;
import com.sirius.spurt.service.business.jobgroup.SaveJobGroupBusiness.Result;
import com.sirius.spurt.store.provider.jobgroup.JobGroupProvider;
import java.io.Serializable;
import lombok.Builder;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.validation.annotation.Validated;

@Slf4j
@Component
@RequiredArgsConstructor
public class SaveJobGroupBusiness implements Business<Dto, Result> {
    private final JobGroupProvider jobGroupProvider;

    @Override
    public Result execute(Dto input) {
        log.info("Start SaveJobGroupBusiness");
        jobGroupProvider.saveJobGroup(input.getUserId(), input.getJobGroup());

        return null;
    }

    @JsonIgnoreProperties
    @Data
    @Validated
    @Builder
    public static class Dto implements Business.Dto, Serializable {
        private String userId;
        private JobGroup jobGroup;
    }

    public static class Result implements Business.Result, Serializable {}
}
