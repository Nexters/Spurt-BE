package com.sirius.spurt;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.sirius.spurt.common.meta.Category;
import com.sirius.spurt.service.business.category.GetAllCategoryBusiness;
import com.sirius.spurt.service.controller.category.CategoryController;
import java.util.ArrayList;
import java.util.List;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;

@WebMvcTest(controllers = {CategoryController.class})
public class CategoryControllerTest extends BaseMvcTest {
    @MockBean private GetAllCategoryBusiness getAllCategoryBusiness;

    @Test
    void 카테고리_전체_조회_테스트() throws Exception {
        List<Category> categoryList = new ArrayList<>();
        categoryList.add(Category.MAJOR);

        GetAllCategoryBusiness.Result result =
                GetAllCategoryBusiness.Result.builder().categoryList(categoryList).build();
        when(getAllCategoryBusiness.execute(null)).thenReturn(result);
        this.mockMvc.perform(get("/v1/category")).andExpect(status().isOk()).andDo(print());
    }
}
