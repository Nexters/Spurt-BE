package com.sirius.spurt.common.meta;

import com.sirius.spurt.common.template.CommonEnum;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public enum JobGroup implements CommonEnum {
    DEVELOPER("개발자"),
    DESIGNER("디자이너"),
    MARKETER("마케팅"),
    ETC("기타");

    @Getter private final String description;

    @Override
    public String getValue() {
        return name();
    }
}
