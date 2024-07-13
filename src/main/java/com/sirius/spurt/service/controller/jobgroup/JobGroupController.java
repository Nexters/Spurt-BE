package com.sirius.spurt.service.controller.jobgroup;

import com.sirius.spurt.common.auth.PrincipalDetails;
import com.sirius.spurt.service.business.jobgroup.SaveJobGroupBusiness;
import com.sirius.spurt.service.business.jobgroup.UpdateJobGroupBusiness;
import com.sirius.spurt.service.controller.RestResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/v1/jobgroup")
@RequiredArgsConstructor
@Slf4j
public class JobGroupController {
    private final SaveJobGroupBusiness saveJobGroupBusiness;
    private final UpdateJobGroupBusiness updateJobGroupBusiness;

    /**
     * @param dto
     * @return
     * @title 유저 아이디, 직군 저장 api
     */
    @PostMapping
    public RestResponse<SaveJobGroupBusiness.Result> saveJobGroup(
            @AuthenticationPrincipal PrincipalDetails principalDetails,
            @RequestBody SaveJobGroupBusiness.Dto dto) {
        dto.setUserId(principalDetails.getUserEntity().getUserId());
        dto.setEmail(principalDetails.getUserEntity().getEmail());
        return RestResponse.success(saveJobGroupBusiness.execute(dto));
    }

    /**
     * @param dto
     * @return
     * @title 유저 아이디, 직군 수정 api
     */
    @PutMapping
    public RestResponse<UpdateJobGroupBusiness.Result> updateJobGroup(
            @AuthenticationPrincipal PrincipalDetails principalDetails,
            @RequestBody UpdateJobGroupBusiness.Dto dto) {
        dto.setUserId(principalDetails.getUserEntity().getUserId());
        dto.setEmail(principalDetails.getUserEntity().getEmail());
        return RestResponse.success(updateJobGroupBusiness.execute(dto));
    }
}
