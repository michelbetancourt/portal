package portal.core.html;

import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import portal.core.ModuleContext;
import portal.core.tools.StringTools;
import portal.core.tools.TemplateTools;

public abstract class BaseHtmlContent implements HtmlContent {

	private final MessageFormat _theFormat;

	@SuppressWarnings("unchecked")
	public BaseHtmlContent() {
		_theFormat = TemplateTools.getTemplateMessageFormat(getClass(),
				getHtmlElementTemplateName());
	}

	@SuppressWarnings("unchecked")
	protected Object[] extendOptions(Object[] injectedOptions) {

		List extendedOptins = new ArrayList();
		extendedOptins.addAll(getInternalOptions());
		extendedOptins.addAll(Arrays.asList(injectedOptions));

		return extendedOptins.toArray();
	}

	protected String getTemplateName() {
		return null;
	}

	private String getHtmlElementTemplateName() {
		String templateName = getTemplateName();
		if (StringTools.isEmpty(templateName)) {
			templateName = "main";
		}
		return templateName;
	}

	@SuppressWarnings("unchecked")
	protected List getInternalOptions() {
		return Collections.EMPTY_LIST;
	}

	@SuppressWarnings("unchecked")
	protected void addInternalOption(Object option) {
		getInternalOptions().add(option);
	}

	public String toHtml(ModuleContext information, Object[] options) {
		return _theFormat.format(extendOptions(options));
	}

}
