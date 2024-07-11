package com.sirius.spurt.service.business.category;

import static org.assertj.core.api.Assertions.assertThat;

import com.sirius.spurt.common.meta.Category;
import com.sirius.spurt.service.business.category.GetAllCategoryBusiness.Dto;
import com.sirius.spurt.service.business.category.GetAllCategoryBusiness.Result;
import com.sirius.spurt.service.business.category.GetAllCategoryBusiness.Result.ResultCategory;
import java.util.Arrays;
import java.util.List;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class GetAllCategoryBusinessTest {
    @InjectMocks private GetAllCategoryBusiness getAllCategoryBusiness;

    @Test
    void 카테고리_전체_조회_테스트() {
        // given

        // when
        Result result = getAllCategoryBusiness.execute(new Dto());

        // then
        List<String> categories =
                result.getCategoryList().stream().map(ResultCategory::getValue).toList();
        List<String> expectedCategories =
                Arrays.stream(Category.values()).map(Category::getValue).toList();
        assertThat(categories).containsAll(expectedCategories);
    }
}
