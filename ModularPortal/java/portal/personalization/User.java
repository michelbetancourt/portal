package portal.personalization;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import portal.core.db.SystemTables;
import portal.core.db.Table;
import portal.core.orm.IdentifiableEntityAbstract;
import portal.core.request.CookieMonster;
import portal.core.request.CookieNameRegistry;
import portal.core.request.CookieableObject;

public final class User extends IdentifiableEntityAbstract<Integer> implements
		CookieableObject {

	static {
		CookieNameRegistry.register(new User(-1));
	}

	protected User(Integer id) {
		super(id);
	}

	@Override
	public String getCookieName() {
		return "user_id";
	}

	@Override
	public String getCookieValue() {
		return String.valueOf(getID());
	}

	@Override
	public Cookie toCookie() {
		return CookieMonster.generateCookie(this);
	}

	public void store(HttpServletRequest request, HttpServletResponse response) {
		response.addCookie(toCookie());
		request.setAttribute(getClass().getSimpleName(), this);
	}

	public static User get(Cookie[] cookies) {
		if (null == cookies) {
			return null;
		}
		User currentUser = null;
		for (int i = 0; i < cookies.length; i++) {
			Cookie someCookie = cookies[i];
			if (CookieNameRegistry.get(User.class).equals(someCookie.getName())) {
				currentUser = new User(Integer.valueOf(someCookie.getValue()));
			}
		}

		return currentUser;
	}

	@Override
	public Table getTable() {
		return SystemTables.USER_MASTER;
	}

	@Override
	protected String getIdColumnName() {
		return "user_id";
	}

}
