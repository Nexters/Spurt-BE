package com.sirius.spurt.service.business.category;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.sirius.spurt.common.meta.Category;
import com.sirius.spurt.common.template.Business;
import com.sirius.spurt.service.business.category.GetAllCategoryBusiness.Dto;
import com.sirius.spurt.service.business.category.GetAllCategoryBusiness.Result;
import com.sirius.spurt.service.business.category.GetAllCategoryBusiness.Result.ResultCategory;
import java.io.Serializable;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class GetAllCategoryBusiness implements Business<Dto, Result> {

    @Override
    public Result execute(Dto input) {
        return Result.builder()
                .categoryList(
                        GetAllCategoryBusinessMapper.INSTANCE.toResultCategoryList(List.of(Category.values())))
                .build();
    }

    public static class Dto implements Business.Dto, Serializable {}

    @Data
    @JsonIgnoreProperties
    @Builder
    public static class Result implements Business.Result, Serializable {
        List<ResultCategory> categoryList;

        @Data
        @JsonIgnoreProperties
        @Builder
        @AllArgsConstructor
        static class ResultCategory {
            String value;
            String description;
        }
    }

    @Mapper
    public interface GetAllCategoryBusinessMapper {
        GetAllCategoryBusinessMapper INSTANCE = Mappers.getMapper(GetAllCategoryBusinessMapper.class);

        default List<ResultCategory> map(List<Category> categoryList) {
            return categoryList.stream()
                    .map(category -> new ResultCategory(category.getValue(), category.getDescription()))
                    .toList();
        }

        List<ResultCategory> toResultCategoryList(List<Category> categoryList);
    }
}
