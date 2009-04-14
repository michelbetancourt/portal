package portal.core.html;

import portal.core.ModuleContext;

public interface HtmlContent {

	public String toHtml(ModuleContext information, Object[] options);

}
