package com.sirius.spurt.service.business.user;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.sirius.spurt.common.template.Business;
import com.sirius.spurt.service.business.user.DeleteUserBusiness.Dto;
import com.sirius.spurt.service.business.user.DeleteUserBusiness.Result;
import com.sirius.spurt.store.provider.experience.ExperienceProvider;
import com.sirius.spurt.store.provider.question.QuestionProvider;
import com.sirius.spurt.store.provider.user.UserProvider;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Component
@RequiredArgsConstructor
public class DeleteUserBusiness implements Business<Dto, Result> {
    private final UserProvider userProvider;
    private final ExperienceProvider experienceProvider;
    private final QuestionProvider questionProvider;

    @Override
    @Transactional
    public Result execute(Dto input) {
        experienceProvider.deleteExperienceByUser(input.getUserId());
        questionProvider.deleteQuestionByUser(input.getUserId());
        userProvider.deleteUser(input.getUserId());
        return new Result();
    }

    @Setter
    @Getter
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    public static class Dto implements Business.Dto {
        private String userId;
    }

    @JsonIgnoreProperties
    public static class Result implements Business.Result {}
}
