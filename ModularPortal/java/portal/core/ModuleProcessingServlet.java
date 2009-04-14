package portal.core;

import java.io.IOException;
import java.util.Collection;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class ModuleProcessingServlet extends HttpServlet {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * The main portal processor.
	 */
	@Override
	protected void doGet(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {

		Page currentPage = Page.get(request);

		Collection<Module> visibleModules = Module.get(currentPage);

		for (Module someModule : visibleModules) {
			if (someModule.isAsyncWorker()) {
				AsyncModule theWorker = (AsyncModule) someModule;
				theWorker.setModuleContainer(new ModuleContext(request,
						new AsyncModuleResponse()));
				theWorker.start();
			}
		}
		ModuleContext mainContext = null;
		for (Module someModule : visibleModules) {
			if (someModule.requiresLoginRedirect(mainContext)) {
				// redirect and return;
				return;
			}
			if (null == mainContext) {
				mainContext = new ModuleContext(request, response);
			}
			someModule.doModule(mainContext);
		}

	}

}
