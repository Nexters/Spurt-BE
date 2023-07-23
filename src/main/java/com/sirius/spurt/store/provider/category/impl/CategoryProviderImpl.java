package com.sirius.spurt.store.provider.category.impl;

import com.sirius.spurt.store.provider.category.CategoryProvider;
import com.sirius.spurt.store.provider.category.vo.CategoryVo;
import com.sirius.spurt.store.repository.database.entity.CategoryEntity;
import com.sirius.spurt.store.repository.database.repository.CategoryRepository;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CategoryProviderImpl implements CategoryProvider {
    private final CategoryRepository categoryRepository;

    @Override
    public List<CategoryVo> getCategoryList() {
        return CategoryProviderImplMapper.INSTANCE.toCategoryVoList(categoryRepository.findAll());
    }

    @Mapper
    public interface CategoryProviderImplMapper {
        CategoryProviderImplMapper INSTANCE = Mappers.getMapper(CategoryProviderImplMapper.class);

        List<CategoryVo> toCategoryVoList(List<CategoryEntity> categoryEntityList);
    }
}
