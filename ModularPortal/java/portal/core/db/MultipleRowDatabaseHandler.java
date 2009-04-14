package portal.core.db;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;

public class MultipleRowDatabaseHandler<T> extends DatabaseRowHandler<T> {

	public MultipleRowDatabaseHandler() {
		super();
	}

	protected Collection<T> newCollection() {
		return new ArrayList<T>();
	}

	protected Object getNullEntry() {
		return Collections.EMPTY_LIST;
	}

}
