package portal.core;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.Locale;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import portal.personalization.User;

public final class ModuleContext {

	private final HttpServletRequest _request;

	private final HttpServletResponse _response;

	private User _user;

	private Page _page;

	private final Locale _locale;

	ModuleContext(HttpServletRequest request, HttpServletResponse response) {
		_request = request;
		_response = response;
		_locale = Locale.US;
	}

	public HttpServletRequest getRequest() {
		return _request;
	}

	public HttpServletResponse getResponse() {
		return _response;
	}

	public User getUser() {
		if (null == _user) {
			_user = (User) getRequest().getAttribute("user");
		}

		return _user;
	}

	public Page getPage() {
		if (null == _page) {
			_page = Page.get(getRequest());
		}

		return _page;
	}

	public Locale getLocale() {
		return _locale;
	}

	public PrintWriter getOutputWriter() {
		PrintWriter outputWriter;
		try {
			outputWriter = getResponse().getWriter();
		} catch (IOException ioe) {
			throw new RuntimeException(ioe);
		}

		return outputWriter;
	}

}
