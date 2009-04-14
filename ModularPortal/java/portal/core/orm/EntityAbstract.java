package portal.core.orm;

import java.sql.SQLException;
import java.util.Collection;

import portal.core.db.DBValue;
import portal.core.db.InsertQuery;
import portal.core.db.SQL;
import portal.core.db.Table;
import portal.core.db.WhereClause;
import portal.core.tools.StringTools;

public abstract class EntityAbstract implements IEntity {

	protected EntityAbstract() {
	}

	@Override
	public String toString() {
		return StringTools.toStringHelper(getClass(), this);
	}

	@Override
	public abstract Table getTable();

	@Override
	public void saveRecord() {
		Collection<DBValue> primaryKeyValues = getPrimaryKeyValues();
		SQL theSQL = new SQL();
		try {
			InsertQuery insertQuery = new InsertQuery(getTable());
			WhereClause whereClause = new WhereClause();
			setWhereClause(whereClause);
			insertQuery.addAll(primaryKeyValues);
			theSQL.executeSave(insertQuery, whereClause);
		} catch (SQLException e) {
			throw new RuntimeException(e);
		}

	}

	/**
	 * Override this method to set the whereClause values to intercept the
	 * saveRecord mehtod.
	 * 
	 */
	@Override
	public void setWhereClause(WhereClause whereClause) {
	}

}
