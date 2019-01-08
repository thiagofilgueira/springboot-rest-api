package com.mysample.security;

/**
 * @author thiagofilgueira
 */
public class SecurityConstants {
    static final long EXPIRATIONTIME = 864_000_000; // 10 days
    static final String SECRET = "XxXSecretKeyToGenJWTsXxX";
    static final String TOKEN_PREFIX = "Bearer";
    static final String HEADER_STRING = "Authorization";
    static final String SUFFIX_NAME_ROLE_USER = "USER";
    static final String SUFFIX_NAME_ROLE_ADMIN = "ADMIN";
}