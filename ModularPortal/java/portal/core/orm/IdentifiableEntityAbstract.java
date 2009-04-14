package portal.core.orm;

import java.util.Collection;
import java.util.Collections;

import portal.core.db.DBValue;
import portal.core.db.QuerySingleRow;

public abstract class IdentifiableEntityAbstract<T> extends EntityAbstract
		implements IIdentifiableObject<T> {

	private final T _id;

	private final Collection<DBValue> _primaryKeyCollection;

	protected IdentifiableEntityAbstract(T id) {
		super();
		_id = id;
		_primaryKeyCollection = Collections.singletonList(new DBValue(
				"module_type_id", getID()));
	}

	@Override
	public T getID() {
		return _id;
	}

	@Override
	public Collection<DBValue> getPrimaryKeyValues() {
		return _primaryKeyCollection;
	}

	protected abstract String getIdColumnName();

	private final QuerySingleRow objects = new QuerySingleRow("SELECT * FROM "
			+ getTable().getTableName() + " WHERE " + getIdColumnName()
			+ " = ?", true);

	protected Object getObjectByColumnName(String columnName) {
		return objects.get(getID());
	}

}
