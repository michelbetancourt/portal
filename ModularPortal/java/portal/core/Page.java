package portal.core;

import javax.servlet.http.HttpServletRequest;

import portal.core.db.DatabaseRecord;
import portal.core.db.DatabaseRowHandler;
import portal.core.db.QuerySingleRow;
import portal.core.db.SystemTables;
import portal.core.db.Table;
import portal.core.orm.IdentifiableNamedEntity;
import portal.core.request.Parameter;
import portal.core.request.ParameterNameRegistry;
import portal.core.request.ParameterableObject;

public class Page extends IdentifiableNamedEntity<Integer> implements
		ParameterableObject {

	static {
		ParameterNameRegistry.register(new Page(-1, "does not matter"));
	}

	private Page(Integer id, String name) {
		super(id, name);
	}

	public static Page get(HttpServletRequest request) {
		Page currentPage = get(request.getParameter(ParameterNameRegistry
				.get(Page.class)));
		if (null == currentPage) {
			currentPage = mainPage;
		}
		return currentPage;
	}

	public boolean isLogginPage() {
		return equals(loginPage);
	}

	@Override
	public String getParameterName() {
		return "p";
	}

	@Override
	public Parameter toParameter() {
		return new Parameter(getParameterName(), getID());
	}

	@Override
	public Table getTable() {
		return SystemTables.PAGES;
	}

	public static Page get(String pageId) {

		Page thePage;
		try {
			thePage = get(Integer.valueOf(pageId));
		} catch (NumberFormatException e) {
			thePage = null;
		}
		return thePage;
	}

	private static QuerySingleRow pages = new QuerySingleRow(
			"SELECT * FROM core.page_master WHERE page_id = ?", true,
			new DatabaseRowHandler<Page>() {

				@Override
				protected Page buildObject(DatabaseRecord theRecord) {
					Integer pageId = theRecord
							.getIntegerByColumnName("page_id");
					String name = theRecord.getStringByColumnName("name");
					return new Page(pageId, name);
				}

			});

	public static Page get(Integer pageId) {
		return (Page) pages.get(pageId);
	}

	@Override
	protected String getNameColumnName() {
		return "name";
	}

	@Override
	protected String getIdColumnName() {
		return "page_id";
	}

	private static final Page loginPage = get(1);

	private static final Page mainPage = get(2);

}
