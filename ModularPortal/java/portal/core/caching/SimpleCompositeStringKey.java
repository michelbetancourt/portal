package portal.core.caching;

public class SimpleCompositeStringKey extends CacheReference<String> {

	public SimpleCompositeStringKey(Object keys[]) {
		super(generateKey(keys));
	}

	private static final String generateKey(Object keys[]) {
		StringBuilder buffer = new StringBuilder();
		int count = 0;
		for (Object someKey : keys) {
			buffer.append(someKey);
			if (count != keys.length - 1) {
				buffer.append(":");
			}
			count++;
		}
		return buffer.toString();
	}

	@Override
	public String toString() {
		return getKey();
	}

}
