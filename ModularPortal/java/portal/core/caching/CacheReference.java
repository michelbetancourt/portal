package portal.core.caching;

public class CacheReference<T> {

	private final T _theKey;

	public CacheReference(T theKey) {
		_theKey = theKey;
	}

	protected T getKey() {
		return _theKey;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((_theKey == null) ? 0 : _theKey.hashCode());
		return result;
	}

	@SuppressWarnings("unchecked")
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		CacheReference other = (CacheReference) obj;
		if (_theKey == null) {
			if (other._theKey != null)
				return false;
		} else if (!_theKey.equals(other._theKey))
			return false;
		return true;
	}

}
