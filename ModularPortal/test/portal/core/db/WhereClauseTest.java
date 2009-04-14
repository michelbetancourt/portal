package portal.core.db;

import java.util.HashMap;
import java.util.Map;

import junit.framework.Assert;

import org.junit.Test;

public class WhereClauseTest {

	@Test
	public void testBuildQueryString() {
		WhereClause whereClause = new WhereClause();
		whereClause.buildQueryString();
		Assert.assertEquals("WHERE", whereClause.getQueryString().toString()
				.trim());
	}

	private static final Map<String, Object> setObjectInputDataMap = new HashMap<String, Object>();

	static {
		setObjectInputDataMap.put("someColumn", 1979);
		setObjectInputDataMap.put(
				"sjdlfjdsklfjdslfdZXBCVFERT%&#(Q%*$)%#$()%($)#RU@_!)!#",
				"2(@*#*$(#NZZNXCMV<SALDFJKPL@qp$@(#$()@#u(ipjdlasd");
	}

	@Test
	public void testSetObject() {
		WhereClause whereClause = new WhereClause();
		Map<String, Object> inputData = getObjectInputDataCopy();
		Assert.assertTrue(inputData.size() > 0);
		for (String columnName : inputData.keySet()) {
			whereClause.setObject(createDBValue(columnName, inputData
					.get(columnName)));
		}

		Assert.assertTrue(whereClause.getValues().size() > 0);
		for (DBValue someDBValue : whereClause.getValues()) {
			Assert.assertNotNull(someDBValue.getColumnName());
			Assert.assertNotNull(someDBValue.getValue());
			Assert.assertNotNull(inputData.remove(someDBValue.getColumnName()));
			Assert.assertTrue(someDBValue instanceof WhereClauseValue);
			WhereClauseValue whereValue = (WhereClauseValue) someDBValue;
			Assert.assertEquals(SQLCondition.AND, whereValue.getCondition());
		}

	}

	private static final Object[][] setStringInputDataArray = new Object[][] {
	/* 0 */new Object[] { SQLCondition.AND, "ColumnFirst", "@#*($)#@" },
	/* 1 */new Object[] { SQLCondition.OR, "otherColumn", "someValue" },
	/* 0 */new Object[] { SQLCondition.AND, "integerColumnFirst", 29304329 },
	/* 1 */new Object[] { SQLCondition.OR, "otherIntegerColumn", -943848923 } };

	@Test
	public void testSetString() {
		WhereClause whereClause = new WhereClause();
		for (Object[] innerArray : setStringInputDataArray) {
			DBValue databaseValue = createDBValue(innerArray);
			whereClause.setObject((SQLCondition) innerArray[0], databaseValue);
		}

		assertWhereClauseOnArray(whereClause, setStringInputDataArray);
	}

	private void assertWhereClauseOnArray(WhereClause whereClause,
			Object[][] inputData) {
		Assert.assertTrue(whereClause.getValues().size() > 0);
		int currentPosition = 0;
		for (DBValue someDBValue : whereClause.getValues()) {
			Assert.assertNotNull(someDBValue.getColumnName());
			Assert.assertNotNull(someDBValue.getValue());
			Assert.assertTrue(someDBValue instanceof WhereClauseValue);
			WhereClauseValue whereValue = (WhereClauseValue) someDBValue;
			Assert.assertEquals(inputData[currentPosition][0], whereValue
					.getCondition());
			Assert.assertEquals(inputData[currentPosition][1], whereValue
					.getColumnName());
			Assert.assertEquals(inputData[currentPosition][2], whereValue
					.getValue());
			currentPosition++;
		}
	}

	private Map<String, Object> getObjectInputDataCopy() {
		Map<String, Object> inputData = new HashMap<String, Object>();
		inputData.putAll(setObjectInputDataMap);
		return inputData;
	}

	private DBValue createDBValue(Object[] dataInputArray) {
		return createDBValue(dataInputArray[1], dataInputArray[2]);
	}

	private DBValue createDBValue(Object columnName, Object value) {
		return new DBValue((String) columnName, value);
	}

}
