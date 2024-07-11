package com.sirius.spurt.common.config;

import com.querydsl.jpa.impl.JPAQueryFactory;
import com.sirius.spurt.store.repository.database.custom.impl.QuestionRepositoryImpl;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;

@TestConfiguration
public class QuerydslConfigTest {
    @PersistenceContext private EntityManager entityManager;

    @Bean
    public JPAQueryFactory jpaQueryFactory() {
        return new JPAQueryFactory(entityManager);
    }

    @Bean
    public QuestionRepositoryImpl questionRepositoryImpl() {
        return new QuestionRepositoryImpl(jpaQueryFactory());
    }
}
