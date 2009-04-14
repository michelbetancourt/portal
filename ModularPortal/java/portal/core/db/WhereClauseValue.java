package portal.core.db;

public class WhereClauseValue extends DBValue {

	private final SQLCondition _condtion;

	WhereClauseValue(SQLCondition condition, DBValue databaseValue) {
		super(databaseValue);
		_condtion = condition;
	}

	SQLCondition getCondition() {
		return _condtion;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result
				+ ((_condtion == null) ? 0 : _condtion.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		WhereClauseValue other = (WhereClauseValue) obj;
		if (_condtion == null) {
			if (other._condtion != null)
				return false;
		} else if (!_condtion.equals(other._condtion))
			return false;
		return true;
	}

}
