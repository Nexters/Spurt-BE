package com.sirius.spurt.service.controller.user;

import com.sirius.spurt.service.business.user.CheckUserExistsBusiness;
import com.sirius.spurt.service.controller.RestResponse;
import jakarta.servlet.http.HttpServletRequest;
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

    /**
     * @return
     * @title 유저 존재 여부 확인 api
     */
    @GetMapping("/exist")
    public RestResponse<CheckUserExistsBusiness.Result> checkUserExists(HttpServletRequest request) {
        return RestResponse.success(
                checkUserExistsBusiness.execute(
                        CheckUserExistsBusiness.Dto.builder()
                                .userId(request.getAttribute("userId").toString())
                                .build()));
    }
}
