package portal.core.tools;

import portal.core.orm.IEntity;
import portal.core.orm.IKeyableObject;

public class StringTools {

	@SuppressWarnings("unchecked")
	public static String toStringHelper(Class someClass, IKeyableObject object) {
		return someClass.getSimpleName() + "{ " + object.getPrimaryKeyValues()
				+ " }";
	}

	@SuppressWarnings("unchecked")
	public static String toStringHelper(Class someClass, IEntity object) {
		return toStringHelper(someClass, (IKeyableObject) object) + " { "
				+ " }";
	}

	@SuppressWarnings("unchecked")
	public static String convertPackageToSlashString(Class someClass) {
		return someClass.getPackage().getName().replaceAll("[.]", "/");
	}

	public static boolean isEmpty(String theString) {
		return null == theString || theString.isEmpty();
	}
}
