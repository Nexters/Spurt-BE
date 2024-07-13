package com.sirius.spurt.common.meta;

public enum ResultCode {
    SUCCESS(0, "정상 처리 되었습니다"),
    SYSTEM_ERROR(1000, "알 수 없는 에러가 발생했습니다."),
    AUTHENTICATION_FAILED(2000, "인증에 실패했습니다."),
    UNKNOWN_SOCIAL(2001, "알 수 없는 소셜입니다."),
    NOT_QUESTION_OWNER(3000, "질문이 존재하지 않거나 작성자가 아닙니다."),
    NOT_EXIST_USER(3001, "존재하지 않는 유저입니다."),
    NOT_EXPERIENCE_OWNER(3002, "경험이 존재하지 않거나 작성자가 아닙니다."),
    TIME_FORMAT_ERROR(4000, "시간 형식이 맞지 않습니다."),
    NOT_ALL_CATEGORY(5000, "ALL category는 저장할 수 없습니다."),
    MISSING_CATEGORY(5001, "category는 최소 1개 이상 입력하셔야 합니다."),
    NO_CONTENT(6000, "조회 결과가 없습니다."),
    QUESTION_THREE_SECONDS(7000, "질문은 3초에 1번만 작성이 가능합니다."),
    EXPERIENCE_THREE_SECONDS(7001, "경험은 3초에 1번만 작성이 가능합니다.");

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
