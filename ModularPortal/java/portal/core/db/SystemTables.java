package portal.core.db;

public enum SystemTables implements Table {

	MODULES("core.module_master"), PAGES("core.page_master"), USER_MASTER(
			"core.user_master");

	private final String _tableName;

	private SystemTables(String tableName) {
		_tableName = tableName;
	}

	@Override
	public String getTableName() {
		return _tableName;
	}
}
