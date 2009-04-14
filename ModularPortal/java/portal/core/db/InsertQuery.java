package portal.core.db;

public class InsertQuery extends Query {

	private final Table _dbTable;

	public InsertQuery(Table databaseTable) {
		super(getPartialQuery(databaseTable));
		_dbTable = databaseTable;
	}

	protected Table getTable() {
		return _dbTable;
	}

	@Override
	void buildQueryString() {
		traverseValues(new QueryDelegate() {

			@Override
			void accept(DBValue databaseValue) {
				getQuery().getQueryString().append(
						databaseValue.getColumnName());
				appendColumnDelimiter(databaseValue);
			}

			@Override
			void done() {
				getQuery().getQueryString().append(") VALUES (");
			}

			@Override
			void initialize(Query query) {
				super.initialize(query);
				getQuery().getQueryString().append("(");
			}

		});

		traverseValues(new QueryDelegate() {

			@Override
			void accept(DBValue databaseValue) {
				appendColumnDataMarker(databaseValue);
			}

			@Override
			void done() {
				getQuery().getQueryString().append(")");
			}

		});
	}

	private static StringBuilder getPartialQuery(Table databaseTable) {
		StringBuilder theQueryString = new StringBuilder("INSERT INTO ");
		theQueryString.append(databaseTable.getTableName());
		return theQueryString.append(" ");
	}

	public static final void main(String args[]) {
		Query blah = new InsertQuery(SystemTables.MODULES);
		blah.setObject("what", 1);
		blah.setObject("ajfslkdf", "sdjfaldsjflds");
		blah.setObject("ajfslkdf", "sdjfaldsjflds");
		blah.setObject("ajfslkdf", "sdjfaldsjflds");
		blah.buildQueryString();
		System.out.println(blah.getQueryString());

		UpdateQuery updateQuery = new UpdateQuery(SystemTables.MODULES);
		updateQuery.setObject("what", 1);
		updateQuery.setObject("ajfslkdf", "sdjfaldsjflds");
		WhereClause wheresClause = new WhereClause();
		updateQuery.setWhereClause(wheresClause);
		wheresClause.setObject("where1", 100);
		wheresClause.setObject(SQLCondition.OR, "ORconditionColumn",
				"someValue");
		updateQuery.buildQueryString();
		System.out.println(updateQuery.getQueryString());
		blah = updateQuery.convertToInsertQuery();
		blah.buildQueryString();
		System.out.println(blah.getQueryString());
	}
}
