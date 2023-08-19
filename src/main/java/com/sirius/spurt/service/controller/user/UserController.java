package com.sirius.spurt.service.controller.user;

import com.sirius.spurt.common.resolver.user.LoginUser;
import com.sirius.spurt.service.business.user.CheckUserExistsBusiness;
import com.sirius.spurt.service.business.user.CheckUserHasPinedBusiness;
import com.sirius.spurt.service.business.user.CheckUserHasPostedBusiness;
import com.sirius.spurt.service.business.user.UserInfoBusiness;
import com.sirius.spurt.service.controller.RestResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
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

    /**
     * @return
     * @title 유저 존재 여부 확인 api
     */
    @GetMapping("/exist")
    public RestResponse<CheckUserExistsBusiness.Result> checkUserExists(LoginUser loginUser) {
        return RestResponse.success(
                checkUserExistsBusiness.execute(
                        CheckUserExistsBusiness.Dto.builder().userId(loginUser.getUserId()).build()));
    }

    /**
     * @return
     * @title 유저 최초 핀 고정 확인 api
     */
    @GetMapping("/pin")
    public RestResponse<CheckUserHasPinedBusiness.Result> checkUserHasPined(LoginUser loginUser) {
        return RestResponse.success(
                checkUserHasPinedBusiness.execute(
                        CheckUserHasPinedBusiness.Dto.builder().userId(loginUser.getUserId()).build()));
    }

    /**
     * @return
     * @title 유저 질문&답변 작성 이력 확인 api
     */
    @GetMapping("/posting")
    public RestResponse<CheckUserHasPostedBusiness.Result> checkUserHasPosted(LoginUser loginUser) {
        return RestResponse.success(
                checkUserHasPostedBusiness.execute(
                        CheckUserHasPostedBusiness.Dto.builder().userId(loginUser.getUserId()).build()));
    }

    /**
     * @return
     * @title 유저 정보 조회
     */
    @GetMapping("/info")
    public RestResponse<UserInfoBusiness.Result> getUserInfo(LoginUser loginUser) {
        return RestResponse.success(
                userInfoBusiness.execute(
                        UserInfoBusiness.Dto.builder().userId(loginUser.getUserId()).build()));
    }
}
