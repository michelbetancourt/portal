package portal.core.orm;

import portal.core.db.Table;

public interface IEntity extends IKeyableObject, IPersistableObject {

	public Table getTable();

}
