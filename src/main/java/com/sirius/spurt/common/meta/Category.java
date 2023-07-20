package com.sirius.spurt.common.meta;

import com.sirius.spurt.common.template.CommonEnum;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public enum Category implements CommonEnum {
    COLLABORATION("협업경험"),
    PROSANDCONS("장점단점"),
    CONFLICT("갈등경험"),
    PRACTICAL("실무경험"),
    UNDERSTANDING("직무이해도"),
    EXPERIANCE("직무경험"),
    MAJOR("전공지식"),
    MOTVE("지원동기");

    @Getter private final String description;

    @Override
    public String getCode() {
        return name();
    }
}
