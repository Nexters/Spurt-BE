package com.sirius.spurt.service.controller.jobgroup;

import com.sirius.spurt.service.business.jobgroup.SaveJobGroupBusiness;
import com.sirius.spurt.service.controller.RestResponse;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/v1/jobgroup")
@RequiredArgsConstructor
@Slf4j
public class JobGroupController {
    private final SaveJobGroupBusiness saveJobGroupBusiness;

    /**
     * @param dto
     * @return
     * @title 유저 아이디, 직군 저장 api
     */
    @PostMapping
    public RestResponse<SaveJobGroupBusiness.Result> saveJobGroup(
            HttpServletRequest request, @RequestBody SaveJobGroupBusiness.Dto dto) {
        dto.setUserId(request.getAttribute("userId").toString());
        return RestResponse.success(saveJobGroupBusiness.execute(dto));
    }
}
