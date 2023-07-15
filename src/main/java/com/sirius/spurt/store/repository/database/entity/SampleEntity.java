package com.sirius.spurt.store.repository.database.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "SAMPLE")
public class SampleEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long sampleId;

    @Column private String sampleKey;
}
