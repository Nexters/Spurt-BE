package com.sirius.spurt.service.controller.experience;

import com.sirius.spurt.common.resolver.user.LoginUser;
import com.sirius.spurt.service.business.experience.DeleteExperienceBusiness;
import com.sirius.spurt.service.business.experience.SaveExperienceBusiness;
import com.sirius.spurt.service.business.experience.UpdateExperienceBusiness;
import com.sirius.spurt.service.controller.RestResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.DeleteMapping;
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

    /**
     * @param dto
     * @return
     * @title 나의 경험 저장 api
     */
    @PostMapping
    public RestResponse<SaveExperienceBusiness.Result> saveExperience(
            LoginUser loginUser, @RequestBody SaveExperienceBusiness.Dto dto) {
        dto.setUserId(loginUser.getUserId());
        return RestResponse.success(saveExperienceBusiness.execute(dto));
    }

    /**
     * @param dto
     * @return
     * @title 나의 경험 수정 api
     */
    @PutMapping
    public RestResponse<UpdateExperienceBusiness.Result> updateExperience(
            LoginUser loginUser, @RequestBody UpdateExperienceBusiness.Dto dto) {
        dto.setUserId(loginUser.getUserId());
        return RestResponse.success(updateExperienceBusiness.execute(dto));
    }

    /**
     * @param dto
     * @return
     * @title 나의 경험 삭제 api
     */
    @DeleteMapping
    public RestResponse<DeleteExperienceBusiness.Result> deleteExperience(
            LoginUser loginUser, @RequestBody DeleteExperienceBusiness.Dto dto) {
        dto.setUserId(loginUser.getUserId());
        return RestResponse.success(deleteExperienceBusiness.execute(dto));
    }
}
