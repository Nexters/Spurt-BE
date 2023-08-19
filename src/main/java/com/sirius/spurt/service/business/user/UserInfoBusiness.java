package com.sirius.spurt.service.business.user;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.sirius.spurt.common.meta.JobGroup;
import com.sirius.spurt.common.template.Business;
import com.sirius.spurt.service.business.user.UserInfoBusiness.Dto;
import com.sirius.spurt.service.business.user.UserInfoBusiness.Result;
import com.sirius.spurt.store.provider.user.UserProvider;
import com.sirius.spurt.store.provider.user.vo.UserVo;
import java.io.Serializable;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;
import org.springframework.stereotype.Component;
import org.springframework.validation.annotation.Validated;

@Component
@RequiredArgsConstructor
public class UserInfoBusiness implements Business<Dto, Result> {
    private final UserProvider userProvider;

    @Override
    public Result execute(Dto input) {
        return UserInfoBusinessMapper.INSTANCE.toResult(userProvider.getUserInfo(input.getUserId()));
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
        private String userId;
        private JobGroup jobGroup;
    }

    @Mapper
    public interface UserInfoBusinessMapper {
        UserInfoBusiness.UserInfoBusinessMapper INSTANCE =
                Mappers.getMapper(UserInfoBusiness.UserInfoBusinessMapper.class);

        UserInfoBusiness.Result toResult(UserVo userVo);
    }
}
