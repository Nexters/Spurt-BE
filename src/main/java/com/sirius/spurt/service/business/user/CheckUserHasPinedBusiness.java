package com.sirius.spurt.service.business.user;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.sirius.spurt.common.template.Business;
import com.sirius.spurt.service.business.user.CheckUserHasPinedBusiness.Dto;
import com.sirius.spurt.service.business.user.CheckUserHasPinedBusiness.Result;
import com.sirius.spurt.store.provider.user.UserProvider;
import java.io.Serializable;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.validation.annotation.Validated;

@Component
@RequiredArgsConstructor
public class CheckUserHasPinedBusiness implements Business<Dto, Result> {
    private final UserProvider userProvider;

    @Override
    public Result execute(Dto input) {
        return Result.builder().hasPined(userProvider.checkHasPined(input.getUserId())).build();
    }

    @JsonIgnoreProperties
    @Data
    @Validated
    @Builder
    public static class Dto implements Business.Dto, Serializable {
        private String userId;
    }

    @NoArgsConstructor
    @AllArgsConstructor
    @Data
    @JsonIgnoreProperties
    @Builder
    public static class Result implements Business.Result, Serializable {
        private boolean hasPined;
    }
}
