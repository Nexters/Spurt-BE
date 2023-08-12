package com.sirius.spurt.common.meta;

public enum ResultCode {
    SUCCESS(0, "정상 처리 되었습니다"),
    SYSTEM_ERROR(1000, "알 수 없는 애러가 발생했습니다."),
    AUTHENTICATION_FAILED(2000, "인증에 실패했습니다."),
    NOT_QUESTION_OWNER(3000, "질문이 존재하지 않거나 작성자가 아닙니다."),
    NOT_EXIST_USER(3001, "존재하지 않는 유저입니다."),
    NOT_EXPERIENCE_OWNER(3002, "경험이 존재하지 않거나 작성자가 아닙니다."),
    TIME_FORMAT_ERROR(4000, "시간 형식이 맞지 않습니다."),
    NOT_ALL_CATEGORY(5000, "ALL category는 저장할 수 없습니다."),
    NO_CONTENT(6000, "조회 결과가 없습니다.");

    private Integer code;
    private String message;

    public String getMessage() {
        return message;
    }

    public Integer getCode() {
        return code;
    }

    ResultCode(Integer code, String errorMessage) {
        this.code = code;
        this.message = errorMessage;
    }
}
