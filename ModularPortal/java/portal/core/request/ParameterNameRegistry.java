package portal.core.request;

public class ParameterNameRegistry extends GenericRegistry {

	private final static GenericRegistry registry = new ParameterNameRegistry();

	private ParameterNameRegistry() {
		super();
	}

	public static void register(ParameterableObject registrableObject) {
		registry.registerParameter(registrableObject.getClass().getName(),
				registrableObject.getParameterName());
	}

	public static String get(
			Class<? extends ParameterableObject> kclass) {
		return registry.get(kclass.getName());
	}

}
