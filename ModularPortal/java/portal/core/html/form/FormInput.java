package portal.core.html.form;

public class FormInput<T> {

	private final String _name;

	private final T _value;

	public FormInput(String name, T value) {
		_name = name;
		_value = value;
	}

	public String getName() {
		return _name;
	}

	public String getValue() {
		String theValue;
		if (null == _value) {
			theValue = "";
		} else {
			theValue = _value.toString();
		}
		return theValue;
	}
}
