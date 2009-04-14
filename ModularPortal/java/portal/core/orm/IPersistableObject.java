package portal.core.orm;

import portal.core.db.WhereClause;

public interface IPersistableObject {

	public void saveRecord();

	public void setWhereClause(WhereClause whereClause);
}
