package com.sirius.spurt.store.provider.experience.vo;

import com.sirius.spurt.common.meta.Category;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class CategoryVo {
    private Long categoryId;

    private Long questionId;

    private Category category;
}
