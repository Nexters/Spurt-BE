package com.sirius.spurt.service.controller;

import com.sirius.spurt.common.meta.ResultCode;
import java.io.Serializable;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class RestResponse<T> implements Serializable {
    private Integer code;
    private String message;
    private T data;

    public static <T> RestResponse<T> success(T data) {
        return RestResponse.<T>builder()
                .code(ResultCode.SUCCESS.getCode())
                .message(ResultCode.SUCCESS.getMessage())
                .data(data)
                .build();
    }

    public static <T> RestResponse<T> error(ResultCode resultCode) {
        return RestResponse.<T>builder()
                .code(resultCode.getCode())
                .message(resultCode.getMessage())
                .build();
    }
}
