package com.sirius.spurt.service.business.category;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.sirius.spurt.common.meta.Category;
import com.sirius.spurt.common.template.Business;
import com.sirius.spurt.service.business.category.GetAllCategoryBusiness.Dto;
import com.sirius.spurt.service.business.category.GetAllCategoryBusiness.Result;
import java.io.Serializable;
import java.util.List;
import lombok.Builder;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class GetAllCategoryBusiness implements Business<Dto, Result> {

    @Override
    public Result execute(Dto input) {
        return Result.builder().categoryList(List.of(Category.values())).build();
    }

    public static class Dto implements Business.Dto, Serializable {}

    @Data
    @JsonIgnoreProperties
    @Builder
    public static class Result implements Business.Result, Serializable {
        List<Category> categoryList;
    }
}
