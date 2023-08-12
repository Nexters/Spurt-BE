package com.sirius.spurt.common.resolver.user;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class NonLoginUser {
    private String userId;
    private String email;
}
