package com.lvh.identity_service.controller;

import com.lvh.identity_service.dto.request.ApiResponse;
import com.lvh.identity_service.dto.request.AuthenticationRequest;
import com.lvh.identity_service.dto.request.IntrospectRequest;
import com.lvh.identity_service.dto.response.AuthenticationResponse;
import com.lvh.identity_service.dto.response.IntrospectResponse;
import com.lvh.identity_service.exception.ErrorCode;
import com.lvh.identity_service.service.AuthenticationService;
import com.nimbusds.jose.JOSEException;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.text.ParseException;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class AuthenticationController {

    AuthenticationService authenticationService;
    @PostMapping("/token")
    public ApiResponse<AuthenticationResponse> authenticate(@RequestBody AuthenticationRequest request){
        var res = authenticationService.authenticate(request);
        return ApiResponse.<AuthenticationResponse>builder()
                .code(ErrorCode.AUTHENTICATE_INVALID.getCode())
                .result(res)
                .build();
    }

    @PostMapping("/introspect")
    public ApiResponse<IntrospectResponse> authenticate(@RequestBody IntrospectRequest request) throws ParseException, JOSEException {
        var res = authenticationService.introspect(request);
        return ApiResponse.<IntrospectResponse>builder()
                .code(ErrorCode.AUTHENTICATE_INVALID.getCode())
                .result(res)
                .build();
    }
}
