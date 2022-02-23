package com.registerWithJWT.registerWithJWT.provider.token;

public interface TokenProvider {

    /**
     * This method generates authentication token based on the provided {@link Token} specification.
     *
     * @param tokenSpec containing token meta information.
     * @return generated token in raw format.
     */
    String serialize(Token tokenSpec);

    /**
     * This method deserialize the generated authentication token back to {@link Token} specification.
     *
     * @param rawToken generated raw token.
     * @return {@link Token} specification containing meta information.
     */
    Token deserialize(String rawToken);
}
