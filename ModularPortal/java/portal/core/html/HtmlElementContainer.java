package portal.core.html;

import java.util.ArrayList;
import java.util.List;

public class HtmlElementContainer {

	private List<BaseHtmlContent> _containedObject = new ArrayList<BaseHtmlContent>();

	public HtmlElementContainer() {
	}

	public void addHtmlElement(BaseHtmlContent someElement) {
		_containedObject.add(someElement);
	}
}
