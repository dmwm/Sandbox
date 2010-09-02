/**
 * $Id: DBManagement.java,v 1.3 2009/09/23 19:23:17 yuyi Exp $
 *
 * A class that can create the database connections <code>java.sql.Connection</code> managed either 
 * by tomcat connection pooling or external source. It also has method to create a prepared statements 
 * <code>java.sql.PreparedStatement</code> that can be used to execute SQL queries. 
 * For connection pooling only one object of this class can be made  and thus it works as a singleton. 
 * The connection pooling that this class use is standard pooling provided by apache tomcat. 
 * For externally managing the connections a static method getConnection can be called which 
 * create a new connection every time it is called. 
 *
 **/


package cms.dbs.commons.db;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import javax.sql.DataSource;
import javax.naming.Context;
import javax.naming.InitialContext;

import cms.dbs.commons.db.PreparedStatementWrapper;

public class DBManagement {
	private static DBManagement instance = null;
	private static Boolean mutex = new Boolean(true);
	private DataSource ds = null;
	
	/**
	 * Constructs a DBManagement object . The constructor of this class is private and therefore 
	 * cannot be instantiated other than from this method. The reason for doing that is to make this class 
	 * as a singleton. Only one instance of this class will be used no matter how many time this method is called 
	 * to retrive the instance. If the instance is already there then that instance is returned otherwise a new 
	 * instance is created that would happen the first time it is called. This method is thread safe since it 
	 * uses semaphone to have mutual access.
	 * @throws Exception - Various types of exception can be thrown from this method related to 
	 * invalid data source name or invalid contex, etc.
	 */
	public static DBManagement getDBConnManInstance() throws Exception {
		if( instance != null) return instance;
		synchronized(mutex) {
			if( instance != null ) return instance;
			//"jdbc/dbs" is defined in context.xml file
			DBManagement dbm = new DBManagement("jdbc/dbs");
			instance = dbm;
		}
		return instance;
	}
	
	public static DBManagement getDBConnManInstance(String rootContext) throws Exception {
		if( instance != null) return instance;
		synchronized(mutex) {
			if( instance != null ) return instance;
			DBManagement dbm = new DBManagement(rootContext);
			instance = dbm;
		}
		return instance;
	}

	/**
	 * This is a private constructor of this class.To get an instance of this class 
	 * getDBConnManInstance method should be used. This constructor creates a datasource 
	 * object to be used for creating connection to the database. The datasource object is provided by the 
	 * Tomcat connection pooling interface. It catches a javax.naming.NoInitialContextException exception that would happen 
	 * only if this class is not invoked within the tomcat container i.e as a standalone application.
	 * @throws Exception - Various types of exception can be thrown from this method related to invalid data source name or 
	 * invalid contex, etc.
	 */
	private DBManagement(String rootContext) throws Exception {
		try {
			if ( (ds = (DataSource)(((Context)((new InitialContext()).lookup("java:/comp/env"))).lookup(rootContext))) == null ) {
				throw new SQLException("Datasource cound not be initialized. Connection pooling failed.");
			}
		} catch(javax.naming.NoInitialContextException e) {
			//System.out.println("This must be a standalone client");
		}
	}
	private DBManagement() throws Exception {
		try {
			if ( (ds = (DataSource)(((Context)((new InitialContext()).lookup("java:/comp/env"))).lookup("jdbc/dbs"))) == null ) {
				throw new SQLException("Datasource cound not be initialized. Connection pooling failed.");
			}
		} catch(javax.naming.NoInitialContextException e) {
			//System.out.println("This must be a standalone client");
		}
	}

	/**
	 * This method uses the <code>javax.sql.DataSource</code> intialized during the instantiation of this class, 
	 * to make the database connection. The way the connection pooling works is that if the connection is already 
	 * open then it return one free connection from the pool instead if openning a new one. The max number of 
	 * connections can be configured in the configuration file loacted in META-INF/context.xml file.
	 * @return A availble or a newly created <code>java.sql.Connection</code>
	 * @throws Exception - A database connection exception can be thrown if datasource does not have any available connection in the pool.
	 */
	public Connection getConnection() throws Exception {
		if(ds == null) return null;
		return ds.getConnection();
	}
	
	/**
	 * Attempts to establish a connection to the given database URL. This is a static method that creates a database 
	 * connection after loading the database driver . The user of this method has the responsiblity to close 
	 * the database connection after it is used and not needed anymore. It create a new connection every time this 
	 * method is called. This method is useful in running the application in standalone mode. When running the application 
	 * inside Tomcat do not use this method. Use connection pooling by creating the instance of this class and the public 
	 * non static getConnection method. 
	 * @param driver The diver of the database to be loaded before making a connection
	 * @param url a database url of the form jdbc:subprotocol:subname
	 * @param userId the database user on whose behalf the connection is being made
	 * @param password the user's password used to authorize the database user
	 * @return A newly created <code>java.sql.Connection</code>
	 * @throws Exception A database connection exception can be thrown if it either fails to load the database driver 
	 * or could not make a connection to the database.
	 */
	 //This is a static method so it doesnot need any DBManager instance to use it. 
	public static Connection getConnection(String driver, String url, String userId, String password) throws Exception {
		Class.forName(driver);
		return DriverManager.getConnection(url,userId,password);
	}
	
	/**
	 * Creates a <code>db.PreparedStatementWrapper</code> object that inherits from <code>java.sql.PreparedStatement</code> 
	 * for sending parameterized SQL statements to the database. <br>
	 * A SQL statement with or without IN parameters can be pre-compiled and stored in a PreparedStatement object. 
	 * This object can then be used to efficiently execute this statement multiple times. 
	 * @param conn a <code>java.sql.Connection</code> object that is still open.
	 * @param query an SQL statement in <code>java.lang.String</code> that may contain one or more '?' IN parameter placeholders.
	 * @return a new default PreparedStatement object containing the pre-compiled SQL statement.
	 * @throws SQLException if a database access error occurs.
	 */
	public static PreparedStatement getStatement(Connection conn, String query) throws SQLException {
		return (new PreparedStatementWrapper(conn.prepareStatement(query), conn, query));
	}

	public static PreparedStatement getStatementScrollable(Connection conn, String query) throws SQLException {
		return (new PreparedStatementWrapper(conn.prepareStatement(query, ResultSet.TYPE_SCROLL_SENSITIVE, 
		ResultSet.CONCUR_UPDATABLE), conn, query));
	}
}
