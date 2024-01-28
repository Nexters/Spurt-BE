package com.sirius.spurt.common.validator;

import static com.sirius.spurt.common.meta.ResultCode.NOT_EXIST_USER;

import com.sirius.spurt.common.exception.GlobalException;
import com.sirius.spurt.store.repository.database.entity.UserEntity;

public class UserValidator {
    public static void validator(UserEntity userEntity) {
        if (!isExistUser(userEntity)) {
            throw new GlobalException(NOT_EXIST_USER);
        }
    }

    private static boolean isExistUser(UserEntity userEntity) {
        return userEntity != null;
    }
}
