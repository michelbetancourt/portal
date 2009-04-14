package portal.core.caching;

import junit.framework.Assert;

import org.junit.Test;

import portal.core.caching.SimpleCompositeStringKey;

public class SimpleCompositeStringKeyTest {

	@Test
	public void testGetKey() {
		final SimpleCompositeStringKey compositeKey = new SimpleCompositeStringKey(
				new Object[] { 1, "sdjfld", -100 });
		Assert.assertTrue(!compositeKey.toString().endsWith(":"));
	}

}
