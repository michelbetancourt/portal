package portal.core.orm;

import java.util.Locale;

public abstract class IdentifiableNamedEntity<T> extends
		IdentifiableEntityAbstract<T> implements INameableObject {

	private String _name;

	protected IdentifiableNamedEntity(T id, String name) {
		super(id);
		_name = name;
	}

	protected IdentifiableNamedEntity(T id) {
		this(id, null);
	}

	@Override
	// TODO Perform locale based lookup
	public String getName(Locale locale) {
		return _name;
	}

	protected abstract String getNameColumnName();

}
