package common;

import jakarta.servlet.http.Cookie;

public class CookieController {
    public static Cookie makeCookie(String key, String value,String path,int age) {
        Cookie cookie = new Cookie(key, value);
        cookie.setPath(path);
        cookie.setMaxAge(age);
        return cookie;
    }
}
