package com.sirius.spurt.service.controller.experience;

import com.sirius.spurt.common.auth.PrincipalDetails;
import com.sirius.spurt.service.business.experience.DeleteExperienceBusiness;
import com.sirius.spurt.service.business.experience.GetAllExperienceBusiness;
import com.sirius.spurt.service.business.experience.GetExperienceBusiness;
import com.sirius.spurt.service.business.experience.SaveExperienceBusiness;
import com.sirius.spurt.service.business.experience.UpdateExperienceBusiness;
import com.sirius.spurt.service.controller.RestResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/v1/experience")
@RequiredArgsConstructor
public class ExperienceController {
    private final SaveExperienceBusiness saveExperienceBusiness;
    private final UpdateExperienceBusiness updateExperienceBusiness;
    private final DeleteExperienceBusiness deleteExperienceBusiness;
    private final GetAllExperienceBusiness getAllExperienceBusiness;
    private final GetExperienceBusiness getExperienceBusiness;

    /**
     * @param dto
     * @return
     * @title 나의 경험 저장 api
     */
    @PostMapping
    public RestResponse<SaveExperienceBusiness.Result> saveExperience(
            @AuthenticationPrincipal PrincipalDetails principalDetails,
            @RequestBody SaveExperienceBusiness.Dto dto) {
        dto.setUserId(principalDetails.getUserEntity().getUserId());
        return RestResponse.success(saveExperienceBusiness.execute(dto));
    }

    /**
     * @param dto
     * @return
     * @title 나의 경험 수정 api
     */
    @PutMapping
    public RestResponse<UpdateExperienceBusiness.Result> updateExperience(
            @AuthenticationPrincipal PrincipalDetails principalDetails,
            @RequestBody UpdateExperienceBusiness.Dto dto) {
        dto.setUserId(principalDetails.getUserEntity().getUserId());
        return RestResponse.success(updateExperienceBusiness.execute(dto));
    }

    /**
     * @param dto
     * @return
     * @title 나의 경험 삭제 api
     */
    @DeleteMapping
    public RestResponse<DeleteExperienceBusiness.Result> deleteExperience(
            @AuthenticationPrincipal PrincipalDetails principalDetails,
            @RequestBody DeleteExperienceBusiness.Dto dto) {
        dto.setUserId(principalDetails.getUserEntity().getUserId());
        return RestResponse.success(deleteExperienceBusiness.execute(dto));
    }

    /**
     * @param
     * @return
     * @title 나의 모든 경험 조회 api
     */
    @GetMapping
    public RestResponse<GetAllExperienceBusiness.Result> getAllExperience(
            @AuthenticationPrincipal PrincipalDetails principalDetails) {
        GetAllExperienceBusiness.Dto dto =
                GetAllExperienceBusiness.Dto.builder()
                        .userId(principalDetails.getUserEntity().getUserId())
                        .build();
        return RestResponse.success(getAllExperienceBusiness.execute(dto));
    }

    /**
     * @param experienceId
     * @return
     * @title 나의 경험 단건 조회 api
     */
    @GetMapping("/{experienceId}")
    public RestResponse<GetExperienceBusiness.Result> getExperience(
            @AuthenticationPrincipal PrincipalDetails principalDetails,
            @PathVariable("experienceId") Long experienceId) {
        GetExperienceBusiness.Dto dto =
                GetExperienceBusiness.Dto.builder()
                        .userId(principalDetails.getUserEntity().getUserId())
                        .experienceId(experienceId)
                        .build();
        return RestResponse.success(getExperienceBusiness.execute(dto));
    }
}
