package com.sirius.spurt.store.provider.category.vo;

import com.sirius.spurt.common.meta.Category;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CategoryVo {
    Category category;
}
