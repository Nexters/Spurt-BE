package com.sirius.spurt.service.controller.sample;

import com.sirius.spurt.service.business.sample.TestGetBusiness;
import com.sirius.spurt.service.business.sample.TestGetBusiness.Dto;
import com.sirius.spurt.service.controller.RestResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/v1")
@RequiredArgsConstructor
@Slf4j
public class SampleController {

    private final TestGetBusiness sampleFlow;
    /**
     * @param sample
     * @param path
     * @return
     * @title samele get 호출 예제
     */
    @GetMapping("/sample/{path}")
    public RestResponse<TestGetBusiness.Result> testGet(
            @RequestParam(name = "sample") String sample, @PathVariable("path") String path) {
        TestGetBusiness.Dto dto = Dto.builder().testDto(sample).testPath(path).build();
        log.info("sample parm : {} path parm : {}", sample, path);
        return RestResponse.success(sampleFlow.execute(dto));
    }
}
