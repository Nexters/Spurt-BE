package com.sirius.spurt.store.provider.question.vo;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class KeyWordVo {
    private Long keyWordId;

    private Long questionId;

    private String keyWord;
}
