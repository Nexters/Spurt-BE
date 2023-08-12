package com.sirius.spurt.common.meta;

import com.sirius.spurt.common.template.CommonEnum;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public enum Category implements CommonEnum {
    ALL("전체"),
    MAJOR("직무지식"),
    PRACTICAL("직무경험"),
    COLLABORATION("협업경험"),
    PROSANDCONS("장단점"),
    CONFLICT("실패경험"),
    ETC("기타");

    @Getter private final String description;

    @Override
    public String getValue() {
        return name();
    }
}
