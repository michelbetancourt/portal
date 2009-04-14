package portal.core.request;

import java.util.HashMap;
import java.util.Map;

public abstract class GenericRegistry {

	private Map<String, String> _registry;

	protected GenericRegistry() {
		_registry = new HashMap<String, String>();
	}

	protected Map<String, String> getRegistry() {
		return _registry;
	}

	protected void registerParameter(String key, String value) {
		if (getRegistry().containsKey(key)) {
			throw new UnsupportedOperationException(
					"This object is not unique, " + key);
		}

		getRegistry().put(key, value);
	}

	protected String get(String key) {
		return getRegistry().get(key);
	}

}
