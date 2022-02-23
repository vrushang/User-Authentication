package com.registerWithJWT.registerWithJWT.provider;

import com.registerWithJWT.registerWithJWT.exception.RestErrorCode;
import com.registerWithJWT.registerWithJWT.exception.UnauthorizedException;

import static com.registerWithJWT.registerWithJWT.constants.ResourceConstants.BEARER_AUTH_PREFIX;

public class BearerAuthDecoder {

    private final String accessToken;

    public BearerAuthDecoder(final String bearerToken) {
        if (!bearerToken.startsWith(BEARER_AUTH_PREFIX)) {
            throw new UnauthorizedException(RestErrorCode.ATH_003);
        }

        final String[] bearerTokens = bearerToken.split(BEARER_AUTH_PREFIX);
        if (bearerTokens.length != 2) {
            throw new UnauthorizedException(RestErrorCode.ATH_004);
        }
        this.accessToken = bearerTokens[1];
    }

    public String getAccessToken() {
        return accessToken;
    }
}
