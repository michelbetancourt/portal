package portal.core.caching;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.Observable;

import portal.core.tools.TimeTools;

public class ReapableCacher<K, V> extends Observable implements ICache<K, V> {

	static {
		try {
			Class.forName(ReaperThread.class.getName());
		} catch (Exception e) {
			throw new ExceptionInInitializerError(e);
		}
	}
	private final Map<K, ExpireableValue<V>> _theCache;

	private final Integer _expireableTimeSpanInSeconds;

	public ReapableCacher(Integer expirableTimeSpanInSeconds) {
		_theCache = Collections
				.synchronizedMap(new HashMap<K, ExpireableValue<V>>());
		_expireableTimeSpanInSeconds = expirableTimeSpanInSeconds;
		addObserver(ReaperThread.theReaperThread);
		setChanged();
		notifyObservers();
	}

	private static final Integer CACHE_LIMIT = TimeTools.ONE_HOUR_IN_SECONDS;

	public ReapableCacher() {
		this(CACHE_LIMIT);
	}

	@Override
	public V get(K key) {
		ExpireableValue<V> expireableValue = getExpireableValue(key);
		V theValue;
		if (null == expireableValue) {
			theValue = null;
		} else {
			theValue = expireableValue.getCachedValue();
		}
		return theValue;
	}

	public ExpireableValue<V> getExpireableValue(K key) {
		return _theCache.get(key);
	}

	@Override
	public void put(K key, V value) {
		_theCache.put(key, new ExpireableValue<V>(value, getExpirationSpan()));
	}

	private long getExpirationSpan() {
		return System.currentTimeMillis() + _expireableTimeSpanInSeconds
				* TimeTools.ONE_SECOND_IN_MILLIS;
	}

	public void reap() {
		Collection<K> toRemove = new ArrayList<K>();
		for (K someKey : _theCache.keySet()) {
			ExpireableValue<V> expireable = getExpireableValue(someKey);
			if (expireable.isExpired()) {
				Object value = get(someKey);
				toRemove.add(someKey);
				System.out.println("Removing, " + someKey + " :: " + value);
			}
		}
		for (K theKey : toRemove) {
			_theCache.remove(theKey);
		}

	}

	@Override
	public void flush() {
		_theCache.clear();
	}

	public static void main(String args[]) throws Exception {
		ReapableCacher<Object, Integer> cache = new ReapableCacher<Object, Integer>(
				11);
		cache.put(new Object(), 1);
		Thread.sleep(1000);
		cache.put(new Object(), 2);
		Thread.sleep(1000);
		cache.put(new Object(), 3);
		Thread.sleep(1000);
		cache.put(new Object(), 4);
		while (true) {
			System.out.println("Hanging out while we wait...");
			Thread.sleep(10 * 1000);
		}
	}

}
