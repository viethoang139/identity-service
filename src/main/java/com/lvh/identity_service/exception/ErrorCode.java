package com.lvh.identity_service.exception;

public enum ErrorCode {
    KEY_INVALID(1000, "Invalid message key"),
    USER_EXISTED(1001, "User existed"),
    NOT_FOUND_USER(1002, "User not found"),
    USERNAME_INVALID(1003,"Username must be at least 3 characters"),
    PASSWORD_INVALID(1004, "Password must be at least 8 characters"),
    AUTHENTICATE_INVALID(1005,"Wrong username or password"),
    UNAUTHENTICATED(1006, "Unauthenticated"),
    UNCATEGORIZED_EXCEPTION(9999, "Uncategorized error"),
    ;

    ErrorCode(int code, String message) {
        this.code = code;
        this.message = message;
    }

    private int code;
    private String message;

    public int getCode() {
        return code;
    }

    public String getMessage() {
        return message;
    }
}
