package com.registerWithJWT.registerWithJWT.provider;

import java.util.Base64;

public class BasicAuthDecoder {
    private final String email;
    private final String password;

    public BasicAuthDecoder(final String base64EncodedCredentials) {
        final String[] base64Decoded = new String(Base64.getDecoder().decode(base64EncodedCredentials.split("Basic ")[1])).split(":");
        this.email = base64Decoded[0];
        this.password = base64Decoded[1];
    }

    public String getEmail() {
        return email;
    }

    public String getPassword() {
        return password;
    }

}
