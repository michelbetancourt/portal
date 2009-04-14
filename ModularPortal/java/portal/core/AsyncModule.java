package portal.core;

import java.io.IOException;
import java.util.Locale;

public final class AsyncModule extends Module implements Runnable {

	private ModuleContext _moduleContainer;

	private final Module _theModule;

	private static final ThreadLocal<Thread> moduleThread = new ThreadLocal<Thread>();

	AsyncModule(Module theModule) {
		super(theModule);
		_moduleContainer = null;
		_theModule = theModule;
	}

	void start() {
		getModuleThread().start();
	}

	private Thread getModuleThread() {
		Thread currentThread = moduleThread.get();
		if (null == currentThread) {
			currentThread = new Thread(this);
			moduleThread.set(currentThread);
		}
		return currentThread;
	}

	private ModuleContext getAsyncModuleContainer() {
		return _moduleContainer;
	}

	void setModuleContainer(ModuleContext moduleContainer) {
		_moduleContainer = moduleContainer;
	}

	ModuleContext getModuleContainer() {
		return _moduleContainer;
	}

	@Override
	public void doModule(ModuleContext container) {
		try {
			getModuleThread().join();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		AsyncModuleResponse asyncResponse = (AsyncModuleResponse) getAsyncModuleContainer()
				.getResponse();
		asyncResponse.setCookies(container.getResponse());
		try {
			container.getResponse().getWriter().print(
					asyncResponse.getOutputBuffer().getBuffer().toString());
		} catch (IOException e) {
			e.printStackTrace();
		}
		moduleThread.set(null);
	}

	@Override
	boolean isAsyncWorker() {
		return true;
	}

	@Override
	boolean requiresLoginRedirect(ModuleContext context) {
		return _theModule.requiresLoginRedirect(context);
	}

	@Override
	public boolean equals(Object obj) {
		return _theModule.equals(obj);
	}

	@Override
	public String getName(Locale locale) {
		return _theModule.getName(locale);
	}

	@Override
	public int hashCode() {
		return _theModule.hashCode();
	}

	/**
	 * Perform the doModule() method asynchronously.
	 */
	@Override
	public void run() {
		_theModule.doModule(getAsyncModuleContainer());
	}
}
