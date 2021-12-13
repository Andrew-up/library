package com.netcracker.ageev.library.security;

public class SecutiryConstants {

    public static final String SIGN_UP_URLS ="/api/auth/**";
    public static final String IMAGE ="/api/image/**/**";
    public static final String SECRET_KEY ="secret";
    public static final String REFRESH_SECRET_KEY ="jwt_secret_key_refresh";
    public static final String TOKEN_PREFIX ="Bearer ";
    public static final String HEADER_STRING ="Authorization";
    public static final String CONTENT_TYPE ="Application/json";
    public static final long EXPIRATION_TIME = 900_000; //15 минут
    public static final long REFRESH_TOKEN_EXPIRATION_TIME = 86400000; //1 день
}