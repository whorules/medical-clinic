package ru.korovko.clinic.common;

import lombok.experimental.UtilityClass;

import javax.servlet.http.Cookie;

@UtilityClass
public class CookieUtils {

    public Cookie createCookie(String cookieName, String cookieValue) {
        Cookie cookie = new Cookie(cookieName, cookieValue);
        cookie.setPath("/");
        return cookie;
    }

    public Cookie deleteCookie(String cookieName) {
        Cookie cookie = new Cookie(cookieName, null);
        cookie.setPath("/");
        cookie.setMaxAge(0);
        return cookie;
    }
}
