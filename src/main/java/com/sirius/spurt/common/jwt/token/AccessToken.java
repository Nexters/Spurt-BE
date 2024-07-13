package com.sirius.spurt.common.jwt.token;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class AccessToken {
    private String token;
    private Long expireTime;
}
