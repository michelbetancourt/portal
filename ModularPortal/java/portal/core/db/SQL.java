package portal.core.db;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.sql.DataSource;

public final class SQL {

	private final static DataSource dataSource;
	static {
		try {
			Context initContext = new InitialContext();
			Context envContext = (Context) initContext.lookup("java:/comp/env");
			dataSource = (DataSource) envContext.lookup("jdbc/database");
			if (null == dataSource) {
				throw new ExceptionInInitializerError("Datasource not found!");
			}
		} catch (NamingException e) {
			throw new ExceptionInInitializerError(e);
		}
	}

	private PreparedStatement _preparedStatement;

	private Connection _connection;

	private ResultSet _results;

	public SQL() {
		_preparedStatement = null;
		_connection = null;
	}

	private Connection getConnection() throws SQLException {
		if (null == _connection) {
			_connection = dataSource.getConnection();
		}
		return _connection;
	}

	private PreparedStatement getPreparedStatement() {
		return _preparedStatement;
	}

	ResultSet getResults() throws SQLException {
		if (null == _results) {
			_results = getPreparedStatement().getResultSet();
		}

		return _results;
	}

	void setPreparedQuery(Query query) throws SQLException {
		setQuery(query);
		query.traverseValues(getPreparedStatementDelegate());
	}

	public void setObject(int index, Object someObject) throws SQLException {
		getPreparedStatement().setObject(index, someObject);
	}

	public void setInteger(int index, Integer someInteger) throws SQLException {
		getPreparedStatement().setInt(index, someInteger);
	}

	void setQuery(String queryString) throws SQLException {
		if (null == getPreparedStatement()) {
			_preparedStatement = getConnection().prepareStatement(queryString);
		}
	}

	void setQuery(Query query) throws SQLException {
		query.buildQueryString();
		setQuery(query.getQueryString().toString());
	}

	public ResultSet executeQuery() throws SQLException {
		return getPreparedStatement().executeQuery();
	}

	public int executeUpdate() throws SQLException {
		return getPreparedStatement().executeUpdate();
	}

	public int executeSave(InsertQuery insertQuery, WhereClause whereClause)
			throws SQLException {
		UpdateQuery updateQuery = new UpdateQuery(insertQuery.getTable());
		updateQuery.addAll(insertQuery.getValues());
		insertQuery.addAll(whereClause.getValues());
		setPreparedQuery(insertQuery);
		int affectedRows;
		try {
			affectedRows = executeUpdate();
		} catch (SQLException e) {
			// primary key violation? ... so update instead.
			updateQuery.setWhereClause(whereClause);
			setPreparedQuery(updateQuery);
			affectedRows = executeUpdate();
		}
		return affectedRows;
	}

	public int executeSave(UpdateQuery query) throws SQLException {
		setPreparedQuery(query);
		int affectedRows;
		affectedRows = executeUpdate();
		if (affectedRows < 1) {
			setPreparedQuery(query.convertToInsertQuery());
			affectedRows = executeUpdate();
		}
		return affectedRows;
	}

	public void close() {

		if (null != _results) {
			try {
				_results.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}

		if (null != _preparedStatement) {
			try {
				_preparedStatement.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}

		if (null != _connection) {
			try {
				_connection.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}

	}

	private QueryDelegate getPreparedStatementDelegate() {
		return new QueryDelegate() {

			@Override
			void accept(DBValue databaseValue) {
				try {
					databaseValue.prepareValue(getCurrentIndex(),
							getPreparedStatement());
				} catch (SQLException e) {
					throw new RuntimeException(e);
				}
			}

		};
	}
}
