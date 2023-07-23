package com.sirius.spurt.store.provider.category;

import com.sirius.spurt.store.provider.category.vo.CategoryVo;
import java.util.List;

public interface CategoryProvider {
    List<CategoryVo> getCategoryList();
}
