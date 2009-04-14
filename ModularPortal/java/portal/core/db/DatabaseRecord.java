package portal.core.db;

import java.util.HashMap;
import java.util.Map;

public final class DatabaseRecord {

	private Object _attachedObject;

	private final Map<String, Object> _objectsByColumnName;

	public DatabaseRecord() {
		_objectsByColumnName = new HashMap<String, Object>();
		_attachedObject = null;
	}

	public Object getObjectByColumnName(String columnName) {
		return _objectsByColumnName.get(columnName);
	}

	public Object getAttachedObject() {
		return _attachedObject;
	}

	void attachObject(Object associatedObject) {
		_attachedObject = associatedObject;
	}

	public void put(String columnName, Object someObject) {
		_objectsByColumnName.put(columnName, someObject);
	}

	/**
	 * Helper methods to access Record Objects
	 */
	public Integer getIntegerByColumnName(String columnName) {
		return (Integer) getObjectByColumnName(columnName);
	}

	public String getStringByColumnName(String columnName) {
		return (String) getObjectByColumnName(columnName);
	}

	public int getIntByColumnName(String columnName) {
		return getIntegerByColumnName(columnName).intValue();
	}

	public Boolean getBooleanByColumnName(String columnName) {
		return (Boolean) getObjectByColumnName(columnName);
	}

	public String toString() {
		return getClass().getName() + "[Attached Object:" + getAttachedObject()
				+ ", ObjectsByColumnName:" + _objectsByColumnName + "]";
	}

}
