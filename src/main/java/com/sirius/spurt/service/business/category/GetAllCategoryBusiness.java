package com.sirius.spurt.service.business.category;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.sirius.spurt.common.meta.Category;
import com.sirius.spurt.common.template.Business;
import com.sirius.spurt.common.template.NoParameterBusiness;
import com.sirius.spurt.service.business.category.GetAllCategoryBusiness.Result;
import com.sirius.spurt.store.provider.category.CategoryProvider;
import com.sirius.spurt.store.provider.category.vo.CategoryVo;
import java.io.Serializable;
import java.util.List;
import lombok.Builder;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@RequiredArgsConstructor
public class GetAllCategoryBusiness implements NoParameterBusiness<Result> {
    private final CategoryProvider categoryProvider;

    @Override
    public Result execute() {
        List<CategoryVo> categoryVoList = categoryProvider.getCategoryList();
        return Result.builder()
                .categoryList(categoryVoList.stream().map(CategoryVo::getCategory).toList())
                .build();
    }

    @Setter
    @Data
    @JsonIgnoreProperties
    @Builder
    public static class Result implements Business.Result, Serializable {
        List<Category> categoryList;
    }
}
