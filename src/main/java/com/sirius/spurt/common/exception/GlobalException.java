package com.sirius.spurt.common.exception;

import com.sirius.spurt.common.meta.ResultCode;
import lombok.Getter;

@Getter
public class GlobalException extends RuntimeException {
    private ResultCode resultCode;

    public GlobalException(ResultCode resultCode) {
        super(resultCode.getMessage());
        this.resultCode = resultCode;
    }
}
