package com.sirius.spurt.common.validator;

import static com.sirius.spurt.common.meta.ResultCode.AUTHENTICATION_FAILED;

import com.sirius.spurt.common.exception.GlobalException;

public class TokenValidator {
    public static void validateCookie(String cookie) {
        if (!isExistCookie(cookie)) {
            throw new GlobalException(AUTHENTICATION_FAILED);
        }
    }

    public static void validateUserId(String userId) {
        if (!isExistUserId(userId)) {
            throw new GlobalException(AUTHENTICATION_FAILED);
        }
    }

    private static boolean isExistCookie(String cookie) {
        return cookie != null;
    }

    private static boolean isExistUserId(String userId) {
        return userId != null;
    }
}
