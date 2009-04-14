package portal.core.caching;

public class ExpireableValue<T> {

	private final long _expirationTime;
	private final T _theCachedValue;

	public ExpireableValue(T cacheableValue, long expirationTime) {
		_theCachedValue = cacheableValue;
		_expirationTime = expirationTime;
	}

	public boolean isExpired() {
		return _expirationTime <= System.currentTimeMillis();
	}

	public T getCachedValue() {
		return _theCachedValue;
	}
}
