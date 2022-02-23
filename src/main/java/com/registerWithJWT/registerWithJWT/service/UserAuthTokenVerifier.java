package com.registerWithJWT.registerWithJWT.service;

import com.registerWithJWT.registerWithJWT.entity.UserAuthToken;
import com.registerWithJWT.registerWithJWT.enums.UserAuthTokenStatus;
import com.registerWithJWT.registerWithJWT.util.DateTimeProvider;
import org.springframework.stereotype.Service;

import java.time.ZonedDateTime;

public class UserAuthTokenVerifier {
    private final UserAuthTokenStatus status;

    public UserAuthTokenVerifier(final UserAuthToken userAuthToken) {

        if (userAuthToken == null) {
            status = UserAuthTokenStatus.NOT_FOUND;
        } else if (isLoggedOut(userAuthToken)) {
            status = UserAuthTokenStatus.LOGGED_OUT;
        } else if (isExpired(userAuthToken)) {
            status = UserAuthTokenStatus.EXPIRED;
        } else {
            status = UserAuthTokenStatus.ACTIVE;
        }
    }

    public boolean isActive() {
        return UserAuthTokenStatus.ACTIVE == status;
    }

    public boolean hasExpired() {
        return UserAuthTokenStatus.EXPIRED == status;
    }

    public boolean hasLoggedOut() {
        return UserAuthTokenStatus.LOGGED_OUT == status;
    }

    public boolean isNotFound() {
        return UserAuthTokenStatus.NOT_FOUND == status;
    }

    public UserAuthTokenStatus getStatus() {
        return status;
    }

    private boolean isExpired(final UserAuthToken userAuthToken) {
        final ZonedDateTime now = DateTimeProvider.currentProgramTime();
        return userAuthToken != null && (userAuthToken.getExpiresAt().isBefore(now) || userAuthToken.getExpiresAt().isEqual(now));
    }

    private boolean isLoggedOut(final UserAuthToken userAuthToken) {
        return userAuthToken != null && userAuthToken.getLogoutAt() != null;
    }

}
