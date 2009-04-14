package portal.core.db;

import java.util.ArrayList;
import java.util.Collection;

public class Query {

	private final StringBuilder _queryString;

	private final Collection<DBValue> _values;

	protected Query(Query query) {
		_queryString = query.getQueryString();
		_values = query.getValues();
	}

	public Query(StringBuilder queryString) {
		_queryString = queryString;
		_values = new ArrayList<DBValue>();
	}

	public Query() {
		this((StringBuilder) null);
	}

	public void addAll(Collection<DBValue> values) {
		_values.addAll(values);
	}

	/**
	 * The buildQueryString is for subclasses to override. This is called within
	 * the Facade to notify that the query builders to run and travse its
	 * values.
	 */
	void buildQueryString() {
	}

	StringBuilder getQueryString() {
		return _queryString;
	}

	Collection<DBValue> getValues() {
		return _values;
	}

	final void traverseValues(QueryDelegate delegate) {

		delegate.initialize(this);
		for (DBValue someDbValue : getValues()) {
			delegate.accept(someDbValue);
			delegate.incrementCurrentIndex();
		}
		delegate.done();
	}

	protected void setObject(DBValue databaseValue) {
		getValues().add(databaseValue);
	}

	protected void setObject(String columnName, Object value) {
		setObject(new DBValue(columnName, value));
	}

}
