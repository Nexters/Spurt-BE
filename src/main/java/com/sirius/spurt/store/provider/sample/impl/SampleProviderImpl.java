package com.sirius.spurt.store.provider.sample.impl;

import com.sirius.spurt.store.provider.sample.SampleProvider;
import com.sirius.spurt.store.provider.sample.vo.SampleVo;
import com.sirius.spurt.store.repository.database.entity.SampleEntity;
import com.sirius.spurt.store.repository.database.repository.SampleRepository;
import lombok.RequiredArgsConstructor;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class SampleProviderImpl implements SampleProvider {
    private final SampleRepository sampleRepository;

    @Override
    public SampleVo getSamepleData(Long id) {
        return SampleProviderImplMapper.INSTANCE.toSampleVo(sampleRepository.findBySampleId(id));
    }

    @Mapper
    public interface SampleProviderImplMapper {
        SampleProviderImplMapper INSTANCE = Mappers.getMapper(SampleProviderImplMapper.class);

        SampleVo toSampleVo(SampleEntity entity);
    }
}
