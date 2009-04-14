package portal.core.html.form;

import java.util.ArrayList;
import java.util.List;

import portal.core.ModuleContext;
import portal.core.html.BaseHtmlContent;

public class HtmlForm extends BaseHtmlContent {

	@SuppressWarnings("unchecked")
	private final List _internalOptions = new ArrayList();

	@SuppressWarnings("unchecked")
	private final List _formElements = new ArrayList();

	@SuppressWarnings("unchecked")
	public HtmlForm(Class containerClass, String id, String name,
			FormMethod method) {
		super();

		_internalOptions.add(id);
		_internalOptions.add(name);
		_internalOptions.add(method.getMethod());
	}

	@SuppressWarnings("unchecked")
	@Override
	protected List getInternalOptions() {
		return _internalOptions;
	}

	@SuppressWarnings("unchecked")
	public void addFormElement(String element) {
		_formElements.add(element + "<br>");
	}

	@Override
	protected String getTemplateName() {
		return "form.html";
	}

	@Override
	public String toHtml(ModuleContext information, Object[] options) {

		return super.toHtml(information, options);
	}
}
