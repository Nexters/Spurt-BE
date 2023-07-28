package com.sirius.spurt.store.repository.database.custom.impl;

import static com.sirius.spurt.store.repository.database.entity.QCategoryEntity.categoryEntity;
import static com.sirius.spurt.store.repository.database.entity.QQuestionEntity.questionEntity;

import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQueryFactory;
import com.sirius.spurt.common.meta.Category;
import com.sirius.spurt.common.meta.JobGroup;
import com.sirius.spurt.store.repository.database.custom.QuestionRepositoryCustom;
import com.sirius.spurt.store.repository.database.entity.QuestionEntity;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Repository;
import org.springframework.util.StringUtils;

@Repository
@RequiredArgsConstructor
public class QuestionRepositoryImpl implements QuestionRepositoryCustom {
    private final JPAQueryFactory jpaQueryFactory;

    @Override
    public Page<QuestionEntity> searchQuestion(
            final String subject,
            final JobGroup jobGroup,
            final Category category,
            final Boolean pinIndicator,
            final Boolean myQuestionIndicator,
            final String userId,
            final PageRequest pageRequest) {

        List<QuestionEntity> questionEntityList =
                jpaQueryFactory
                        .selectFrom(questionEntity)
                        .leftJoin(questionEntity.categoryEntityList, categoryEntity)
                        .where(
                                containSubject(subject),
                                eqJobGroup(jobGroup),
                                eqPinIndicator(pinIndicator),
                                eqUserId(myQuestionIndicator, userId),
                                eqCategory(category))
                        .offset(pageRequest.getOffset())
                        .limit(pageRequest.getPageSize())
                        .orderBy(questionEntity.createTimestamp.desc())
                        .fetch();

        int count =
                jpaQueryFactory
                        .selectFrom(questionEntity)
                        .leftJoin(questionEntity.categoryEntityList, categoryEntity)
                        .where(
                                containSubject(subject),
                                eqJobGroup(jobGroup),
                                eqPinIndicator(pinIndicator),
                                eqUserId(myQuestionIndicator, userId),
                                eqCategory(category))
                        .fetch()
                        .size();

        return new PageImpl<>(questionEntityList, pageRequest, count);
        //        return jpaQueryFactory
        //                .selectFrom(questionEntity)
        //                .leftJoin(questionEntity.categoryEntityList, categoryEntity)
        //                .where(
        //                        containSubject(subject),
        //                        eqJobGroup(jobGroup),
        //                        eqPinIndicator(pinIndicator),
        //                        eqUserId(myQuestionIndicator, userId),
        //                        eqCategory(category))
        //                .orderBy(questionEntity.createTimestamp.desc())
        //                .fetch();
    }

    private BooleanExpression containSubject(String subject) {
        if (!StringUtils.hasText(subject)) return null;
        return questionEntity.subject.contains(subject);
    }

    private BooleanExpression eqJobGroup(JobGroup jobGroup) {
        if (jobGroup == null) return null;
        return questionEntity.jobGroup.eq(jobGroup);
    }

    private BooleanExpression eqPinIndicator(Boolean pinIndicator) {
        if (pinIndicator == null) return null;
        return questionEntity.pinIndicator.eq(pinIndicator);
    }

    private BooleanExpression eqUserId(Boolean myQuestionIndicator, String userId) {
        if (myQuestionIndicator == false) return null;
        return questionEntity.userId.eq(userId);
    }
    //
    private BooleanExpression eqCategory(Category category) {
        if (category == null) return null;
        return categoryEntity.category.eq(category);
    }
}
