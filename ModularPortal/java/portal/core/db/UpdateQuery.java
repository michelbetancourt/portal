package portal.core.db;

public class UpdateQuery extends Query {

	private final Table _dbTable;

	private WhereClause _whereClause;

	UpdateQuery(Table dbTable) {
		super(getPartialQuery(dbTable));
		_whereClause = null;
		_dbTable = dbTable;
	}

	private final Table getDBTable() {
		return _dbTable;
	}

	WhereClause getWhereClause() {
		return _whereClause;
	}

	public void setWhereClause(WhereClause whereClause) {
		_whereClause = whereClause;
	}

	InsertQuery convertToInsertQuery() {
		final InsertQuery insertQuery = new InsertQuery(getDBTable());
		insertQuery.addAll(getValues());
		insertQuery.addAll(getWhereClause().getValues());

		return insertQuery;
	}

	@Override
	void buildQueryString() {
		traverseValues(new QueryDelegate() {

			@Override
			void accept(DBValue databaseValue) {
				getQuery().getQueryString().append(
						databaseValue.getColumnName());
				appendEqualsDelimiter(databaseValue);
				appendColumnDataMarker(databaseValue);
			}

		});

		getWhereClause().buildQueryString();

		getQueryString().append(getWhereClause().getQueryString());
	}

	private static StringBuilder getPartialQuery(Table databaseTable) {
		StringBuilder theQueryString = new StringBuilder("UPDATE ");
		theQueryString.append(databaseTable.getTableName());
		return theQueryString.append(" SET ");
	}

	public static final void main(String args[]) {
		UpdateQuery blah = new UpdateQuery(SystemTables.MODULES);
		blah.setObject("what", 1);
		blah.setObject("ajfslkdf", "sdjfaldsjflds");
		WhereClause wheresClause = new WhereClause();
		blah.setWhereClause(wheresClause);
		wheresClause.setObject("where1", 100);
		blah.buildQueryString();
		System.out.println(blah.getQueryString());
	}

}
