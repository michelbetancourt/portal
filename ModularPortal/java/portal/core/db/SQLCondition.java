package portal.core.db;

public enum SQLCondition {

	AND(" AND "), OR(" OR "), IN(" IN ");

	private final String _conditionString;

	private SQLCondition(String conditionString) {
		_conditionString = conditionString;
	}

	String getCondition() {
		return _conditionString;
	}

}
