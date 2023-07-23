package com.sirius.spurt.service.controller.category;

import com.sirius.spurt.service.business.category.GetAllCategoryBusiness;
import com.sirius.spurt.service.controller.RestResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/v1/category")
@RequiredArgsConstructor
public class CategoryController {
    private final GetAllCategoryBusiness getAllCategoryBusiness;

    /**
     * @return categoryList
     * @title 카테고리 전체 조회
     */
    @GetMapping
    public RestResponse<GetAllCategoryBusiness.Result> getAllCategory() {
        return RestResponse.success(getAllCategoryBusiness.execute(null));
    }
}
