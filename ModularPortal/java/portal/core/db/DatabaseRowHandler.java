package portal.core.db;

import java.sql.ResultSet;
import java.sql.SQLException;

public class DatabaseRowHandler<T> {

	public DatabaseRowHandler() {

	}

	protected Object getResultSetObject(String columnName, ResultSet resultSet)
			throws SQLException {
		return getObject(columnName, resultSet);
	}

	protected T buildObject(DatabaseRecord theRecord) {
		return null;
	}

	protected void configureSQL(SQL sql, Object[] keys) throws SQLException {
		int keyIndex = 1;
		for (Object someKey : keys) {
			sql.setObject(keyIndex++, someKey);
		}
	}

	protected Object getNullEntry() {
		return null;
	}

	protected final static Object getObject(String columnName,
			ResultSet resultSet) throws SQLException {
		return resultSet.getObject(columnName);
	}

	protected final static Integer getIntegerOrNull(String columnName,
			ResultSet resultSet) throws SQLException {
		Integer columnData = Integer.valueOf(resultSet.getInt(columnName));
		if (resultSet.wasNull()) {
			columnData = null;
		}
		return columnData;
	}

	protected final static Boolean getBooleanObject(String columnName,
			ResultSet resultSet) throws SQLException {
		return Boolean.valueOf(resultSet.getBoolean(columnName));
	}

}
