package com.sirius.spurt.store.provider.question.vo;

import com.sirius.spurt.common.meta.Category;
import lombok.Data;

@Data
public class CategoryVo {
    private Long categoryId;

    private Long questionId;

    private Category category;
}
