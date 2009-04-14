package portal.core.db;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Collection;

public class QueryMultipleRows<T> extends QuerySingleRow {

	public QueryMultipleRows(String theQuery, boolean supportsCaching,
			MultipleRowDatabaseHandler<T> multipleRowDatabaseHandler) {
		super(theQuery, supportsCaching, multipleRowDatabaseHandler);
	}

	@SuppressWarnings("unchecked")
	public Collection<T> getCollection(Object keys[]) {
		return (Collection<T>) super.get(keys);
	}

	@SuppressWarnings("unchecked")
	public Collection<T> getCollection(Object theKey) {
		return (Collection<T>) super.get(theKey);
	}

	@SuppressWarnings("unchecked")
	@Override
	protected Object getDatabaseRecord(ResultSet resultSet) throws SQLException {

		Collection<T> results = ((MultipleRowDatabaseHandler<T>) getDatabaseHandler())
				.newCollection();
		do {
			DatabaseRecord databaseRecord = (DatabaseRecord) super
					.getDatabaseRecord(resultSet);
			T recordObject = (T) databaseRecord.getAttachedObject();
			if (null != recordObject) {
				results.add(recordObject);
			}
		} while (resultSet.next());

		return results;
	}

	/**
	 * Do not intercept the current database record since its always some sort
	 * of Collection
	 */
	@Override
	protected final Object interceptDatabaseRecordObject(Object theRecord) {
		return theRecord;
	}

	@Override
	protected Object getObjectByColumnName(String columnName, Object[] keys) {
		throw new UnsupportedOperationException("Implementation required for "
				+ getClass().getName());
	}

	@Override
	protected Object getObjectByColumnName(String columnName, Object theKey) {
		throw new UnsupportedOperationException("Implementation required for "
				+ getClass().getName());
	}

}
