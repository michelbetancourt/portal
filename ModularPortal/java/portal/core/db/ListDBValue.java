package portal.core.db;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.Collection;

public class ListDBValue<T> extends DBValue {

	private final SQLCondition _condtion;

	private final Collection<T> _values;

	ListDBValue(String columnName, Collection<T> values) {
		super(columnName, (T) null);
		_condtion = SQLCondition.IN;
		_values = values;
	}

	SQLCondition getCondition() {
		return _condtion;
	}

	@Override
	void prepareValue(int currentIndex, PreparedStatement preparedStatement)
			throws SQLException {
		for (T someValue : _values) {
			if (someValue instanceof Integer) {
				preparedStatement.setInt(currentIndex, ((Integer) someValue)
						.intValue());
			} else if (someValue instanceof String) {
				preparedStatement.setString(currentIndex, (String) someValue);
			} else {
				throw new RuntimeException("Unsupported type : "
						+ someValue.getClass());
			}

		}
	}

}
