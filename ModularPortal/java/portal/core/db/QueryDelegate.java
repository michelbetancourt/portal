package portal.core.db;

abstract class QueryDelegate {

	private int _totalCount;
	private int _currentIndex;

	private Query _query;

	public QueryDelegate() {
	}

	void initialize(Query query) {
		_query = query;
		_totalCount = query.getValues().size();
		_currentIndex = 1;
	}

	private int getTotalCount() {
		return _totalCount;
	}

	void incrementCurrentIndex() {
		_currentIndex++;
	}

	int getCurrentIndex() {
		return _currentIndex;
	}

	boolean isLastElement() {
		return getCurrentIndex() >= getTotalCount();
	}

	void appendColumnDelimiter(DBValue databaseValue) {
		appendColumnDelimiter(databaseValue, getQuery().getQueryString());
	}

	void appendColumnDelimiter(DBValue databaseValue, StringBuilder query) {
		if (!isLastElement()) {
			query.append(databaseValue.getColumnDelimiter());
		}
	}

	void appendEqualsDelimiter(DBValue databaseValue) {
		appendEqualsDelimiter(databaseValue, getQuery().getQueryString());
	}

	void appendEqualsDelimiter(DBValue databaseValue, StringBuilder query) {
		query.append(databaseValue.getEqualsDelimiter());
	}

	void appendColumnDataMarker(DBValue databaseValue) {
		appendColumnDataMarker(databaseValue, getQuery().getQueryString());
	}

	void appendColumnDataMarker(DBValue databaseValue, StringBuilder query) {
		query.append(databaseValue.getColumnDataMarker());
		appendColumnDelimiter(databaseValue);
	}

	Query getQuery() {
		return _query;
	}

	abstract void accept(DBValue databaseValue);

	void done() {
		// do nothing by default
	}

}
