package portal.core.modules;

import java.io.IOException;

import portal.core.IModule;
import portal.core.ModuleContext;

public class Noop implements IModule {

	@Override
	public void doModule(ModuleContext container) {

		try {
			container.getResponse().getWriter().println("blah");
		} catch (IOException ioe) {
			throw new RuntimeException(ioe);
		}

	}

}
