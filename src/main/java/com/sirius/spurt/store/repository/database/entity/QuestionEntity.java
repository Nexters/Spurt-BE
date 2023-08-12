package com.sirius.spurt.store.repository.database.entity;

import com.sirius.spurt.common.meta.JobGroup;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "QUESTION")
public class QuestionEntity extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long questionId;

    @Column(nullable = false)
    private String userId;

    @Column(nullable = false, columnDefinition = "VARCHAR(30) CHARACTER SET UTF8")
    private String subject;

    @Column(nullable = false, columnDefinition = "VARCHAR(1000) CHARACTER SET UTF8")
    private String mainText;

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private JobGroup jobGroup;

    @Column private Boolean pinIndicator;

    @ManyToOne
    @JoinColumn(name = "experienceId")
    private ExperienceEntity experienceEntity;

    // 키워드 테이블 조인
    @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true)
    @JoinColumn(name = "questionId")
    private List<KeyWordEntity> KeyWordEntityList;

    // 카테고리 테이블 조인
    @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true)
    @JoinColumn(name = "questionId")
    private List<CategoryEntity> categoryEntityList;
}
