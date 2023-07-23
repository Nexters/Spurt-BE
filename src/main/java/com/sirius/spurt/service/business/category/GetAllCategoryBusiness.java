package com.sirius.spurt.service.business.category;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.sirius.spurt.common.meta.Category;
import com.sirius.spurt.common.template.Business;
import com.sirius.spurt.service.business.category.GetAllCategoryBusiness.Dto;
import com.sirius.spurt.service.business.category.GetAllCategoryBusiness.Result;
import com.sirius.spurt.store.provider.category.CategoryProvider;
import com.sirius.spurt.store.provider.category.vo.CategoryVo;
import java.io.Serializable;
import java.util.List;
import lombok.Builder;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@RequiredArgsConstructor
public class GetAllCategoryBusiness implements Business<Dto, Result> {
    private final CategoryProvider categoryProvider;

    @Override
    public Result execute(Dto input) {
        return Result.builder()
                .categoryList(GetAllCategoryBusinessMapper.INSTANCE.toCategoryList(categoryProvider.getCategoryList()))
                .build();
    }

    public static class Dto implements Business.Dto, Serializable {}

    @Data
    @JsonIgnoreProperties
    @Builder
    public static class Result implements Business.Result, Serializable {
        List<Category> categoryList;
    }

    @Mapper
    public interface GetAllCategoryBusinessMapper {
        GetAllCategoryBusinessMapper INSTANCE = Mappers.getMapper(GetAllCategoryBusinessMapper.class);

        default Category map(CategoryVo categoryVo) {
            return categoryVo.getCategory();
        }
        List<Category> toCategoryList(List<CategoryVo> categoryVoList);
    }
}
