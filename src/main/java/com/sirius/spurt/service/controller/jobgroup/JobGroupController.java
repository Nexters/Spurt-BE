package com.sirius.spurt.service.controller.jobgroup;

import com.sirius.spurt.common.meta.JobGroup;
import com.sirius.spurt.service.business.jobgroup.SaveJobGroupBusiness;
import com.sirius.spurt.service.controller.RestResponse;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/v1/jobgroup")
@RequiredArgsConstructor
@Slf4j
public class JobGroupController {
    private final SaveJobGroupBusiness saveJobGroupBusiness;

    /**
     * @param jobGroup 직군
     * @return
     * @title 유저 아이디, 직군 저장 api
     */
    @PostMapping
    public RestResponse<SaveJobGroupBusiness.Result> saveJobGroup(
            HttpServletRequest request, @RequestParam JobGroup jobGroup) {
        return RestResponse.success(
                saveJobGroupBusiness.execute(
                        SaveJobGroupBusiness.Dto.builder()
                                .userId(request.getAttribute("userId").toString())
                                .jobGroup(jobGroup)
                                .build()));
    }
}
