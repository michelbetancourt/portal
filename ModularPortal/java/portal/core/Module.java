package portal.core;

import java.sql.SQLException;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

import portal.core.db.DatabaseRecord;
import portal.core.db.MultipleRowDatabaseHandler;
import portal.core.db.QueryMultipleRows;
import portal.core.db.SQL;
import portal.core.db.SystemTables;
import portal.core.db.Table;
import portal.core.exceptions.BadModuleException;
import portal.core.orm.IdentifiableNamedEntity;
import portal.personalization.Login;

public class Module extends IdentifiableNamedEntity<Integer> implements IModule {

	private final boolean _requiresLogin;

	private final Integer _displayOrder;

	private final IModule _theModule;

	@SuppressWarnings("unchecked")
	Module(Integer id, String className, Integer displayOrder,
			Boolean requiresLogin) {
		super(id);
		_requiresLogin = requiresLogin;
		_displayOrder = displayOrder;
		try {
			Class<IModule> moduleClass = (Class<IModule>) Class.forName(
					className).asSubclass(IModule.class);
			_theModule = moduleClass.newInstance();
		} catch (ClassCastException e) {
			throw new BadModuleException(e);
		} catch (InstantiationException e) {
			throw new BadModuleException(e);
		} catch (IllegalAccessException e) {
			throw new BadModuleException(e);
		} catch (ClassNotFoundException cnfe) {
			throw new BadModuleException(cnfe);
		}
	}

	Module(Module theModule) {
		super(theModule.getID());
		_requiresLogin = theModule.requiresLogin();
		_displayOrder = theModule.getDisplayOrder();
		_theModule = theModule.getModule();

	}

	private IModule getModule() {
		return _theModule;
	}

	private boolean requiresLogin() {
		return _requiresLogin;
	}

	boolean requiresLoginRedirect(ModuleContext context) {
		boolean requiresRedirect = false;
		if (requiresLogin() && !Login.isLoggedIn(context.getUser())) {
			if (!context.getPage().isLogginPage()) {
				requiresRedirect = true;
			}
		}

		return requiresRedirect;
	}

	boolean isAsyncWorker() {
		return false;
	}

	@Override
	public String getName(Locale locale) {
		return getModule().getClass().getName();
	}

	Integer getDisplayOrder() {
		return _displayOrder;
	}

	public void doModule(ModuleContext container) {
		getModule().doModule(container);
	}

	@Override
	public void saveRecord() {

	}

	@Override
	public Table getTable() {
		return SystemTables.MODULES;
	}

	private static final Map<String, Module> moduleSingletonCache = Collections
			.synchronizedMap(new HashMap<String, Module>());

	private static final QueryMultipleRows<Module> modulesByPage = new QueryMultipleRows<Module>(
			"SELECT * FROM core.MODULE_MASTER M INNER JOIN core.MODULE_PAGE_XREF X ON M.MODULE_ID = X.MODULE_ID WHERE X.PAGE_ID = ?",
			true, new MultipleRowDatabaseHandler<Module>() {

				@Override
				protected void configureSQL(SQL sql, Object[] keys)
						throws SQLException {
					Page theKey = (Page) keys[0];
					sql.setInteger(1, theKey.getID());
				}

				@Override
				protected Module buildObject(DatabaseRecord theRecord) {
					Boolean isAysncWorker = theRecord
							.getBooleanByColumnName("is_async");
					Boolean isSingleton = theRecord
							.getBooleanByColumnName("is_singleton");
					Integer moduleId = theRecord
							.getIntegerByColumnName("module_id");
					String moduleClass = theRecord
							.getStringByColumnName("module_class");
					Boolean requiresLogin = theRecord
							.getBooleanByColumnName("requires_login");
					Integer displayOrder = theRecord
							.getIntegerByColumnName("display_order");
					Module theModule;
					if (isSingleton) {
						theModule = moduleSingletonCache.get(moduleClass);
						if (null == theModule) {
							theModule = createModuleHelper(isAysncWorker,
									moduleId, moduleClass, displayOrder,
									requiresLogin);
							moduleSingletonCache.put(moduleClass, theModule);
						}
					} else {
						theModule = createModuleHelper(isAysncWorker, moduleId,
								moduleClass, displayOrder, requiresLogin);
					}

					return theModule;
				}

			});

	static Collection<Module> get(Page somePage) {
		return modulesByPage.getCollection(somePage);
	}

	private static final Module createModuleHelper(final Boolean isAysncWorker,
			final Integer moduleId, final String moduleClass,
			final Integer displayOrder, final Boolean requiresLogin) {
		Module theModule;

		theModule = new Module(moduleId, moduleClass, displayOrder,
				requiresLogin);
		if (isAysncWorker) {
			theModule = new AsyncModule(theModule);
		}

		return theModule;
	}

	@Override
	protected String getNameColumnName() {
		return null;
	}

	@Override
	protected String getIdColumnName() {
		return "module_id";
	}
}
