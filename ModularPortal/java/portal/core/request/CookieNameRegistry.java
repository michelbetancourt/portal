package portal.core.request;

public class CookieNameRegistry extends GenericRegistry {

	private final static GenericRegistry registry = new CookieNameRegistry();

	private CookieNameRegistry() {
		super();
	}

	public static void register(CookieableObject registrableObject) {
		registry.registerParameter(registrableObject.getClass().getName(),
				registrableObject.getCookieName());
	}

	public static String get(Class<? extends CookieableObject> kclass) {
		return registry.get(kclass.getName());
	}

}
