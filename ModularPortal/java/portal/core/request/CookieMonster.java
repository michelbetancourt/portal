package portal.core.request;

import javax.servlet.http.Cookie;

public class CookieMonster {

	public static Cookie generateCookie(CookieableObject o) {
		Cookie theCookie = new Cookie(o.getCookieName(), o.getCookieValue());
		return theCookie;
	}

}
