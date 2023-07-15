package com.sirius.spurt.service.business.sample;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.sirius.spurt.common.template.Business;
import com.sirius.spurt.service.business.sample.TestGetBusiness.Dto;
import com.sirius.spurt.service.business.sample.TestGetBusiness.Result;
import com.sirius.spurt.store.provider.sample.SampleProvider;
import com.sirius.spurt.store.provider.sample.vo.SampleVo;
import java.io.Serializable;
import lombok.Builder;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@RequiredArgsConstructor
public class TestGetBusiness implements Business<Dto, Result> {

    private final SampleProvider sampleProvider;

    @Override
    public Result execute(Dto input) {
        log.info("Strart sample flow");
        SampleVo sample = sampleProvider.getSamepleData(Long.valueOf(1));
        return Result.builder().sampleKey(sample.getSampleKey()).build();
    }

    @JsonIgnoreProperties
    @Setter
    @Builder
    public static class Dto implements Business.Dto, Serializable {
        private String testDto;
        private String testPath;
    }

    @Setter
    @Data
    @JsonIgnoreProperties
    @Builder
    public static class Result implements Business.Result, Serializable {
        /** Sample table samplekey */
        private String sampleKey;
    }
}
