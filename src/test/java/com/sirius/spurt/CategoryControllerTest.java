package com.sirius.spurt;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.sirius.spurt.service.controller.category.CategoryController;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;

@WebMvcTest(controllers = {CategoryController.class})
public class CategoryControllerTest extends BaseMvcTest {
    @MockBean private CategoryController categoryController;

    @Test
    void 카테고리_전체_조회_테스트() throws Exception {
        this.mockMvc
            .perform(get("/v1/category"))
            .andExpect(status().isOk())
            .andDo(print());
    }
}
