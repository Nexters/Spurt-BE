package com.sirius.spurt.service.business.question;

import static com.sirius.spurt.common.meta.JobGroup.DEVELOPER;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.sirius.spurt.common.meta.Category;
import com.sirius.spurt.common.meta.JobGroup;
import com.sirius.spurt.common.template.Business;
import com.sirius.spurt.service.business.question.RandomQuestionBusiness.Dto;
import com.sirius.spurt.service.business.question.RandomQuestionBusiness.Result;
import com.sirius.spurt.service.business.question.RandomQuestionBusiness.Result.Question;
import com.sirius.spurt.store.provider.question.QuestionProvider;
import com.sirius.spurt.store.provider.question.vo.QuestionVoList;
import com.sirius.spurt.store.provider.user.UserProvider;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;
import org.springframework.stereotype.Component;
import org.springframework.validation.annotation.Validated;

@Slf4j
@Component
@RequiredArgsConstructor
public class RandomQuestionBusiness implements Business<Dto, Result> {

    private final QuestionProvider questionProvider;
    private final UserProvider userProvider;

    //    @Override
    //    public Result execute(Dto input) {
    //        if (Category.ALL == input.getCategory()) {
    //            input.setCategory(null);
    //        }
    //
    //        // 비로그인 유저
    //        if (input.getUserId() == null) {
    //            return RandomQuestionBusinessMapper.INSTANCE.toResult(
    //                questionProvider.randomQuestion(null, null, input.getCount(),
    // input.getCategory()));
    //        }
    //
    //        final UserVo userVo = userProvider.getUserInfo(input.getUserId());
    //        JobGroup jobGroup = userVo.getJobGroup();
    //        return RandomQuestionBusinessMapper.INSTANCE.toResult(
    //            questionProvider.randomQuestion(
    //                jobGroup, input.getUserId(), input.getCount(), input.getCategory()));
    //    }

    @Override
    public Result execute(Dto input) {
        List<RandomQuestionBusiness.Result.Question> questionList = new ArrayList<>();
        questionList.add(
                Question.builder().subject("Spring의 특징에 대해 아는대로 말해주세요.").jobGroup(DEVELOPER).build());
        questionList.add(
                Question.builder()
                        .subject("javaScript에서 this 바인딩이 무엇인지 설명해주세요.")
                        .jobGroup(DEVELOPER)
                        .build());
        questionList.add(
                Question.builder()
                        .subject("Logger와 System.out.print의 차이를 설명해주세요.")
                        .jobGroup(DEVELOPER)
                        .build());
        questionList.add(
                Question.builder().subject("SQL과 NOSQL의 차이를 설명해주세요.").jobGroup(DEVELOPER).build());
        questionList.add(
                Question.builder()
                        .subject("Local Storage와 Session Storage의 차이점을 설명해주세요.")
                        .jobGroup(DEVELOPER)
                        .build());
        questionList.add(Question.builder().subject("DI와 IOC에 대해 설명해주세요.").jobGroup(DEVELOPER).build());
        questionList.add(
                Question.builder().subject("ContextAPI가 무엇인지 설명해주세요.").jobGroup(DEVELOPER).build());
        questionList.add(
                Question.builder()
                        .subject("typeScript의 type과 interface는 어떤 차이가 있는지 말해주세요.")
                        .jobGroup(DEVELOPER)
                        .build());
        questionList.add(
                Question.builder()
                        .subject("클래스형/함수형 컴포넌트의 lifeCycle을 설명해주세요.")
                        .jobGroup(DEVELOPER)
                        .build());
        questionList.add(
                Question.builder()
                        .subject("패키지매니저 npm과 yarn은 어떻게 다른지 설명해주세요.")
                        .jobGroup(DEVELOPER)
                        .build());
        questionList.add(
                Question.builder().subject("적응형과 반응형의 차이를 말해주세요.").jobGroup(DEVELOPER).build());
        questionList.add(
                Question.builder().subject("CSR과 SSR의 차이에 대해 설명해주세요.").jobGroup(DEVELOPER).build());

        Random random = new Random();
        int min = 0;
        int max = 11;

        List<RandomQuestionBusiness.Result.Question> questions = new ArrayList<>();
        boolean chk[] = new boolean[12];
        for (int i = 0; i < 4; i++) {
            int idx = -1;
            while (true) {
                idx = random.nextInt(max - min + 1) + min;
                if (!chk[idx]) break;
            }

            questions.add(questionList.get(idx));
            chk[idx] = true;
        }

        return Result.builder().questions(questions).build();
    }

    @JsonIgnoreProperties
    @Data
    @Validated
    @Builder
    public static class Dto implements Business.Dto, Serializable {

        /** userId (전달 필요 x) */
        private String userId;
        /** 전달 질문 개수 (기본값 4) */
        private Integer count;
        /** 카테고리 */
        private Category category;
    }

    @Setter
    @NoArgsConstructor
    @AllArgsConstructor
    @Data
    @JsonIgnoreProperties
    @Builder
    public static class Result implements Business.Result, Serializable {
        /** 질문 정보 */
        private List<RandomQuestionBusiness.Result.Question> questions;

        @Data
        @NoArgsConstructor
        @Builder
        @AllArgsConstructor
        public static class Question {
            /** 제목 */
            private String subject;
            /** 직군 */
            private JobGroup jobGroup;
        }
    }

    @Mapper
    public interface RandomQuestionBusinessMapper {
        RandomQuestionBusiness.RandomQuestionBusinessMapper INSTANCE =
                Mappers.getMapper(RandomQuestionBusiness.RandomQuestionBusinessMapper.class);

        RandomQuestionBusiness.Result toResult(QuestionVoList questions);
    }
}
