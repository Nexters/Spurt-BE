package com.sirius.spurt.service.flow;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.sirius.spurt.common.template.Flow;
import com.sirius.spurt.service.flow.SampleFlow.Dto;
import com.sirius.spurt.service.flow.SampleFlow.Result;
import java.io.Serializable;
import lombok.Builder;
import lombok.Data;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class SampleFlow implements Flow<Dto, Result> {

    @Override
    public Result execute(Dto input) {
        log.info("Strart sample flow");
        return Result.builder().test1("1").test2("2").test3("3").build();
    }

    @JsonIgnoreProperties
    @Setter
    @Builder
    public static class Dto implements Flow.Dto, Serializable {
        private String testDto;
        private String testPath;
    }

    @Setter
    @Data
    @JsonIgnoreProperties
    @Builder
    public static class Result implements Flow.Result, Serializable {
        /** test 로 출력하는 test1 */
        private String test1;
        /** test 로 출력하는 test2 */
        private String test2;
        /** test 로 출력하는 test3 */
        private String test3;
    }
}
