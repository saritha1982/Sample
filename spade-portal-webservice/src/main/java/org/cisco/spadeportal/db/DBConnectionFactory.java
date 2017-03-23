package org.cisco.spadeportal.db;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.sql.DataSource;


/**
 * @author sarbr
 *
 */
public class DBConnectionFactory {
	private static final Logger LOGGER = Logger.getLogger(DBConnectionFactory.class.getName());

	private DBConnectionFactory(){

	}
	/**
	 * Using Datasource getting the connection object
	 * @return Connection object
	 */
	public static Connection getDatabaseConnection() {
		Connection dbConnection = null;
		try {

			/*Context envContext = new InitialContext();
			DataSource ddatasource = (DataSource)envContext.lookup("java:/comp/env/jdbc/portaldb");
			if (ddatasource == null ) {
				throw new Exception("Data source not found!");
			}
			dbConnection=ddatasource.getConnection();*/

			Class.forName("com.mysql.jdbc.Driver");  
			dbConnection=DriverManager.getConnection("jdbc:mysql://localhost:3306/spadedev","root","password");  
			 			

			if(dbConnection!=null){
				LOGGER.log(Level.INFO,"DataBase Connected");
			}

		}
		/*catch (NamingException e) {
			LOGGER.log(Level.SEVERE, "NamingException occur", e);

		} 
*/
		catch (SQLException e) {
			LOGGER.log(Level.SEVERE, "SQLException occur", e);
		} 
		catch (Exception e) {
			LOGGER.log(Level.SEVERE, "Exception occur", e);
		}

		return dbConnection;
	}


	public static void releaseConnection(Statement stmt,ResultSet rs, Connection con){
		try {
			// close result set
			if (rs != null) {
				rs.close();
			}
			rs=null;
			// close statement
			if (stmt != null) {
				stmt.close();
			}
			stmt=null;
			if(con!=null){
				con.close();
			}
			con=null;
		} catch (SQLException e) {
			// Ignore the error
		}
	}





}
