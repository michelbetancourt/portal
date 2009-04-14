package portal.core.orm;

import java.util.Collection;

import portal.core.db.DBValue;

public interface IKeyableObject {

	public Collection<DBValue> getPrimaryKeyValues();
}
