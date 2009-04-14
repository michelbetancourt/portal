package portal.core.db;

import java.sql.PreparedStatement;
import java.sql.SQLException;

/**
 * SELECT * FROM COBRAND_MASTER WHERE COBRAND_ID = 1 UPDATE COBRAND_MASTER SET
 * NAME = '' WHERE COBRAND_ID = 1 INSERT INTO COBRAND_MASTER (COBRAND_ID, NAME)
 * VALUES (1, '')
 * 
 * @author michel
 * 
 * @param <T>
 */

public class DBValue {

	private final String _columnName;

	private final Object _theValue;

	protected DBValue(DBValue dbValue) {
		this(dbValue.getColumnName(), dbValue.getValue());
	}

	public DBValue(String columnName, Object databaseValue) {
		_columnName = columnName;
		_theValue = databaseValue;
	}

	protected final String getColumnDelimiter() {
		return ",";
	}

	protected final String getEqualsDelimiter() {
		return "=";
	}

	protected final String getColumnDataMarker() {
		return "?";
	}

	protected final String getColumnName() {
		return _columnName;
	}

	void prepareValue(int currentIndex, PreparedStatement preparedStatement)
			throws SQLException {
		preparedStatement.setObject(currentIndex, getValue());
	}

	Object getValue() {
		return _theValue;
	}

	@Override
	public String toString() {
		return getColumnName() + ", " + super.toString();
	}

}
