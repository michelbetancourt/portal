package portal.core.html.form;

public enum FormMethod {

	GET("GET"), POST("POST");

	private final String _method;

	private FormMethod(String method) {
		_method = method;
	}

	String getMethod() {
		return _method;
	}

}
