package portal.core.db;

public class WhereClause extends Query {

	public WhereClause(StringBuilder query) {
		super(query);
	}

	public WhereClause() {
		this(new StringBuilder(" WHERE "));
	}

	@Override
	void buildQueryString() {
		traverseValues(new QueryDelegate() {

			@Override
			void accept(DBValue databaseValue) {
				WhereClauseValue whereClauseValue = (WhereClauseValue) databaseValue;
				if (SQLCondition.IN == whereClauseValue.getCondition()) {

				} else {
					getQueryString().append(databaseValue.getColumnName());
					appendEqualsDelimiter(databaseValue, getQueryString());
					appendColumnDataMarker(databaseValue, getQueryString());
				}

			}

		});
	}

	@Override
	public void setObject(DBValue databaseValue) {
		setObject(SQLCondition.AND, databaseValue);
	}

	public void setObject(SQLCondition theSQLCondition, DBValue databaseValue) {
		super.setObject(new WhereClauseValue(theSQLCondition, databaseValue));
	}

	public void setObject(SQLCondition theSQLCondition, String columnName,
			Object value) {
		setObject(theSQLCondition, new DBValue(columnName, value));
	}

}
