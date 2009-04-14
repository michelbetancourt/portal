package portal.core.request;

public final class Parameter {

	private final String _name;

	private final String _value;

	public Parameter(String name, String value) {
		_name = name;
		_value = value;
	}

	public Parameter(String name, Integer value) {
		this(name, String.valueOf(value));
	}

	public String getName() {
		return _name;
	}

	public String getValue() {
		return _value;
	}
}
