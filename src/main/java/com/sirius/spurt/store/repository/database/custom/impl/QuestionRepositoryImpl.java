package com.sirius.spurt.store.repository.database.custom.impl;

import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.core.types.dsl.Expressions;
import com.querydsl.jpa.impl.JPAQueryFactory;
import com.sirius.spurt.common.meta.Category;
import com.sirius.spurt.common.meta.JobGroup;
import com.sirius.spurt.store.repository.database.custom.QuestionRepositoryCustom;
import com.sirius.spurt.store.repository.database.entity.QCategoryEntity;
import com.sirius.spurt.store.repository.database.entity.QQuestionEntity;
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
    public List<QuestionEntity> RandomQuestion(
            final JobGroup jobGroup, final String userId, final Integer count, final Category category) {
        return jpaQueryFactory
                .selectFrom(QQuestionEntity.questionEntity)
                .leftJoin(QQuestionEntity.questionEntity.categoryEntityList, QCategoryEntity.categoryEntity)
                .distinct()
                .where(eqJobGroup(jobGroup), neUserId(userId), eqCategory(category), notExperience())
                .limit(count)
                .orderBy(Expressions.numberTemplate(Double.class, "function('rand')").asc())
                .fetch();
    }

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
                        .selectFrom(QQuestionEntity.questionEntity)
                        .leftJoin(
                                QQuestionEntity.questionEntity.categoryEntityList, QCategoryEntity.categoryEntity)
                        .distinct()
                        .where(
                                containSubject(subject),
                                eqJobGroup(jobGroup),
                                eqPinIndicator(pinIndicator),
                                eqUserId(myQuestionIndicator, userId),
                                eqCategory(category))
                        .offset(pageRequest.getOffset())
                        .limit(pageRequest.getPageSize())
                        .orderBy(
                                QQuestionEntity.questionEntity.pinIndicator.desc(),
                                QQuestionEntity.questionEntity.pinUpdatedTime.desc())
                        .fetch();

        int count =
                jpaQueryFactory
                        .selectFrom(QQuestionEntity.questionEntity)
                        .leftJoin(
                                QQuestionEntity.questionEntity.categoryEntityList, QCategoryEntity.categoryEntity)
                        .distinct()
                        .where(
                                containSubject(subject),
                                eqJobGroup(jobGroup),
                                eqPinIndicator(pinIndicator),
                                eqUserId(myQuestionIndicator, userId),
                                eqCategory(category))
                        .fetch()
                        .size();

        return new PageImpl<>(questionEntityList, pageRequest, count);
    }

    private BooleanExpression notExperience() {
        return QQuestionEntity.questionEntity.experienceId.isNull();
    }

    private BooleanExpression containSubject(String subject) {
        if (!StringUtils.hasText(subject)) return null;
        return QQuestionEntity.questionEntity.subject.contains(subject);
    }

    private BooleanExpression eqJobGroup(JobGroup jobGroup) {
        if (jobGroup == null) return null;
        return QQuestionEntity.questionEntity.jobGroup.eq(jobGroup);
    }

    private BooleanExpression eqPinIndicator(Boolean pinIndicator) {
        if (pinIndicator == null) return null;
        return QQuestionEntity.questionEntity.pinIndicator.eq(pinIndicator);
    }

    private BooleanExpression eqUserId(Boolean myQuestionIndicator, String userId) {
        if (myQuestionIndicator == false) return null;
        return QQuestionEntity.questionEntity.userId.eq(userId);
    }

    private BooleanExpression neUserId(String userId) {
        if (!StringUtils.hasText(userId)) return null;
        return QQuestionEntity.questionEntity.userId.ne(userId);
    }

    private BooleanExpression eqCategory(Category category) {
        if (category == null) return null;
        return QCategoryEntity.categoryEntity.category.eq(category);
    }
}
