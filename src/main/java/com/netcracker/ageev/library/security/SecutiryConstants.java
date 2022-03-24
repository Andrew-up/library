package com.netcracker.ageev.library.security;

public class SecutiryConstants {

    public static final String SIGN_UP_URLS ="/api/auth/**";
    public static final String IMAGE ="/api/image/book/**";
    public static final String AUTHORS_API ="/api/authors/**/**";
    public static final String BOOK_GENRES_API ="/api/genres/**/**";
    public static final String SECRET_KEY ="secret";
    public static final String REFRESH_SECRET_KEY ="jwt_secret_key_refresh";
    public static final String TOKEN_PREFIX ="Bearer ";
    public static final String HEADER_STRING ="Authorization";
    public static final String CONTENT_TYPE ="Application/json";
    public static final long EXPIRATION_TIME = 15000; //15 минут 900_000
    public static final long REFRESH_TOKEN_EXPIRATION_TIME = 86400000; //1 день
    public static final String GUEST_BOOKS ="/api/books/all";
    public static final String ALLBOOK_PAGE ="/api/books/AllBookByPage";
    public static final String SEARCH ="/api/books/search";
}
