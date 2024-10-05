package com.lvh.identity_service.exception;

import lombok.Getter;
import lombok.Setter;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;

@Getter
public enum ErrorCode {
    KEY_INVALID(1001, "Invalid message key", HttpStatus.BAD_REQUEST),
    USER_EXISTED(1002, "User existed", HttpStatus.BAD_REQUEST),
    NOT_FOUND_USER(1003, "User not found", HttpStatus.NOT_FOUND),
    USERNAME_INVALID(1004,"Username must be at least {min} characters", HttpStatus.BAD_REQUEST),
    PASSWORD_INVALID(1005, "Password must be at least {min} characters", HttpStatus.BAD_REQUEST),
    AUTHENTICATE_INVALID(1006,"Wrong username or password", HttpStatus.BAD_REQUEST),
    UNAUTHENTICATED(1007, "Unauthenticated", HttpStatus.UNAUTHORIZED),
    UNAUTHORIZED(1008,"You do not have permission", HttpStatus.FORBIDDEN),
    INVALID_DOB(1009,"Your age must be at least {min}",HttpStatus.BAD_REQUEST),
    UNCATEGORIZED_EXCEPTION(9999, "Uncategorized error", HttpStatus.INTERNAL_SERVER_ERROR),
    ;

    ErrorCode(int code, String message, HttpStatusCode httpStatusCode) {
        this.code = code;
        this.message = message;
        this.statusCode = httpStatusCode;
    }

    private int code;
    private String message;
    private HttpStatusCode statusCode;

}
