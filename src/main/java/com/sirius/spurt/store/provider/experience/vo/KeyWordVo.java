package com.sirius.spurt.store.provider.experience.vo;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class KeyWordVo {
    private Long keyWordId;

    private Long questionId;

    private String keyWord;
}
