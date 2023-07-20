package com.sirius.spurt.common.meta;

import com.sirius.spurt.common.template.CommonEnum;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public enum JobGroup implements CommonEnum {
    FRONTEND("프론트개발자"),
    DESIGNER("디자이너"),
    BACKEND("백엔드개발자"),
    ETC("기타");

    @Getter private final String description;

    @Override
    public String getCode() {
        return name();
    }
}
