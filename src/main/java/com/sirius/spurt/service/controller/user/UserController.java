package com.sirius.spurt.service.controller.user;

import com.sirius.spurt.common.auth.PrincipalDetails;
import com.sirius.spurt.service.business.user.CheckUserExistsBusiness;
import com.sirius.spurt.service.business.user.CheckUserHasPinedBusiness;
import com.sirius.spurt.service.business.user.CheckUserHasPostedBusiness;
import com.sirius.spurt.service.business.user.DeleteUserBusiness;
import com.sirius.spurt.service.business.user.UserInfoBusiness;
import com.sirius.spurt.service.controller.RestResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/v1/user")
@RequiredArgsConstructor
@Slf4j
public class UserController {
    private final CheckUserExistsBusiness checkUserExistsBusiness;
    private final CheckUserHasPinedBusiness checkUserHasPinedBusiness;
    private final CheckUserHasPostedBusiness checkUserHasPostedBusiness;
    private final UserInfoBusiness userInfoBusiness;
    private final DeleteUserBusiness deleteUserBusiness;

    /**
     * @return
     * @title 유저 존재 여부 확인 api
     */
    @GetMapping("/exist")
    public RestResponse<CheckUserExistsBusiness.Result> checkUserExists(
            @AuthenticationPrincipal PrincipalDetails principalDetails) {
        return RestResponse.success(
                checkUserExistsBusiness.execute(
                        CheckUserExistsBusiness.Dto.builder()
                                .userId(principalDetails.getUserEntity().getUserId())
                                .build()));
    }

    /**
     * @return
     * @title 유저 최초 핀 고정 확인 api
     */
    @GetMapping("/pin")
    public RestResponse<CheckUserHasPinedBusiness.Result> checkUserHasPined(
            @AuthenticationPrincipal PrincipalDetails principalDetails) {
        return RestResponse.success(
                checkUserHasPinedBusiness.execute(
                        CheckUserHasPinedBusiness.Dto.builder()
                                .userId(principalDetails.getUserEntity().getUserId())
                                .build()));
    }

    /**
     * @return
     * @title 유저 질문&답변 작성 이력 확인 api
     */
    @GetMapping("/posting")
    public RestResponse<CheckUserHasPostedBusiness.Result> checkUserHasPosted(
            @AuthenticationPrincipal PrincipalDetails principalDetails) {
        return RestResponse.success(
                checkUserHasPostedBusiness.execute(
                        CheckUserHasPostedBusiness.Dto.builder()
                                .userId(principalDetails.getUserEntity().getUserId())
                                .build()));
    }

    /**
     * @return
     * @title 유저 정보 조회
     */
    @GetMapping("/info")
    public RestResponse<UserInfoBusiness.Result> getUserInfo(
            @AuthenticationPrincipal PrincipalDetails principalDetails) {
        return RestResponse.success(
                userInfoBusiness.execute(
                        UserInfoBusiness.Dto.builder()
                                .userId(principalDetails.getUserEntity().getUserId())
                                .build()));
    }

    /**
     * @return
     * @title 유저 삭제
     */
    @DeleteMapping
    public RestResponse<DeleteUserBusiness.Result> deleteUser(
            @AuthenticationPrincipal PrincipalDetails principalDetails) {
        return RestResponse.success(
                deleteUserBusiness.execute(
                        DeleteUserBusiness.Dto.builder()
                                .userId(principalDetails.getUserEntity().getUserId())
                                .build()));
    }
}
