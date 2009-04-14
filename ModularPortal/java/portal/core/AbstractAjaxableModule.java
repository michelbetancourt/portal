package portal.core;

public abstract class AbstractAjaxableModule implements IModuleAjax {

	@Override
	public void doModule(ModuleContext context) {
		doModuleAjax(context);
	}

}
