package portal.core.db;

import java.sql.ResultSet;
import java.sql.SQLException;

import portal.core.caching.CacheReference;
import portal.core.caching.ReapableCacher;
import portal.core.caching.SimpleCompositeStringKey;

public class QuerySingleRow {

	private final ReapableCacher<CacheReference<? extends Object>, Object> _theCache;

	private final String _query;

	@SuppressWarnings("unchecked")
	private final static DatabaseRowHandler defaultDatabaseHandler = new DatabaseRowHandler();

	@SuppressWarnings("unchecked")
	private final DatabaseRowHandler _databaseHandler;

	private final static Object CACHING_NULLED_OBJECT = new Object();

	@SuppressWarnings("unchecked")
	public QuerySingleRow(String query, boolean supportsCaching,
			DatabaseRowHandler databaseHandler) {
		_query = query;
		if (supportsCaching) {
			_theCache = new ReapableCacher<CacheReference<? extends Object>, Object>();
		} else {
			_theCache = null;
		}

		_databaseHandler = databaseHandler;

	}

	public QuerySingleRow(String query, boolean supportsCaching) {
		this(query, supportsCaching, defaultDatabaseHandler);
	}

	@SuppressWarnings("unchecked")
	protected final DatabaseRowHandler getDatabaseHandler() {
		return _databaseHandler;
	}

	private void cacheValue(Object[] keys, Object value) {
		if (null != _theCache) {
			_theCache.put(getReference(keys), value);
		}

	}

	protected CacheReference<? extends Object> getReference(Object[] keys) {
		return new SimpleCompositeStringKey(keys);
	}

	private Object getCachedValue(Object[] theKeys) {
		Object cachedObject;
		if (null != _theCache) {
			cachedObject = _theCache.get(getReference(theKeys));
		} else {
			cachedObject = null;
		}
		return cachedObject;
	}

	public Object get(Object[] keys) {
		Object value = getCachedValue(keys);

		if (null == value) {
			value = findObject(keys);
			cacheValue(keys, value);
		}

		if (CACHING_NULLED_OBJECT == value) {
			value = getDatabaseHandler().getNullEntry();
		}

		return value;
	}

	protected Object getObjectByColumnName(String columnName, Object theKeys[]) {
		Object theObject = getCachedValue(theKeys);
		DatabaseRecord theRecord;
		if (null == theObject) {
			theRecord = (DatabaseRecord) get(theKeys);
		} else {
			theRecord = (DatabaseRecord) theObject;
		}

		if (null != theRecord) {
			theObject = theRecord.getObjectByColumnName(columnName);
		}

		return theObject;
	}

	protected Object getObjectByColumnName(String columnName, Object theKey) {
		return getObjectByColumnName(columnName, new Object[] { theKey });
	}

	/**
	 * Helper method when key is just a single object
	 * 
	 * @param key
	 * @return
	 */
	public final Object get(Object key) {
		return get(new Object[] { key });
	}

	/**
	 * Implement this method to supply the SQL query
	 * 
	 * @return
	 */
	protected String getQuery() {
		return _query;
	}

	private Object findObject(Object[] keys) {

		Object value;
		SQL theSQL = new SQL();
		try {
			theSQL.setQuery(getQuery());
			getDatabaseHandler().configureSQL(theSQL, keys);
			ResultSet resultSet = theSQL.executeQuery();

			if (resultSet.next()) {
				value = getDatabaseRecord(resultSet);
				value = interceptDatabaseRecordObject(value);
			} else {
				value = CACHING_NULLED_OBJECT;
			}

		} catch (SQLException e) {
			throw new RuntimeException("Database failure while loading : "
					+ getClass() + " with ORMDatabaseHandler, "
					+ getDatabaseHandler().getClass().getName(), e);
		} finally {
			theSQL.close();
		}

		return value;
	}

	/**
	 * Override this method when you want to process the full ResultSet
	 * differently, e.g. when you have multiple rows
	 * 
	 * @param resultSet
	 * @return
	 * @throws SQLException
	 */
	protected Object getDatabaseRecord(ResultSet resultSet) throws SQLException {

		int columnCount = resultSet.getMetaData().getColumnCount();

		DatabaseRecord theRecord = new DatabaseRecord();
		if (columnCount > 1) {
			String columnName;
			for (int columnIndex = 1; columnIndex <= columnCount; columnIndex++) {
				columnName = resultSet.getMetaData()
						.getColumnLabel(columnIndex);
				theRecord.put(columnName, getDatabaseHandler()
						.getResultSetObject(columnName, resultSet));
			}
			Object builtObject = getDatabaseHandler().buildObject(theRecord);
			if (null != builtObject) {
				theRecord.attachObject(builtObject);
			}
		} else {
			theRecord.attachObject(getDatabaseHandler().getResultSetObject(
					resultSet.getMetaData().getColumnLabel(columnCount),
					resultSet));
		}

		return theRecord;
	}

	protected Object interceptDatabaseRecordObject(Object object) {
		DatabaseRecord theRecord = (DatabaseRecord) object;
		Object associatedObject = theRecord.getAttachedObject();
		Object theValue;
		if (null != associatedObject) {
			theValue = associatedObject;
		} else {
			theValue = theRecord;
		}

		return theValue;
	}

}