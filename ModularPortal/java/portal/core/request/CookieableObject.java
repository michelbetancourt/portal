package portal.core.request;

import javax.servlet.http.Cookie;

public interface CookieableObject {

	public String getCookieName();

	public Cookie toCookie();

	public String getCookieValue();

}
