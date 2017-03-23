/**
 * 
 */
package org.cisco.spadeportal.db;

import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.logging.Logger;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.sql.DataSource;

/**
 * @author sarbr
 *
 */
public class DatabaseListener implements ServletContextListener{
	private static final Logger LOGGER = Logger.getLogger(DatabaseListener.class.getName());

	  public void contextInitialized(ServletContextEvent sce) {
	        // On Application Startup, please…

	        // Usually I'll make a singleton in here, set up my pool, etc.
	    }

	    public void contextDestroyed(ServletContextEvent sce) {
	    	DataSource datasource =null;
	        // On Application Shutdown, please…
	    	 try {
	        // 1. Go fetch that DataSource
	        Context initContext = new InitialContext();
	        Context envContext  = (Context)initContext.lookup("java:/comp/env");
	        datasource = (DataSource)envContext.lookup("jdbc/portaldb");

	        // 2. Deregister Driver
	            java.sql.Driver mySqlDriver = DriverManager.getDriver("jdbc:mysql://sjc-dbdl-mysql5:3306/");
	            DriverManager.deregisterDriver(mySqlDriver);
	        } catch (SQLException ex) {
	        	LOGGER.info("Could not deregister driver:".concat(ex.getMessage()));
	        } catch (NamingException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} 

	        // 3. For added safety, remove the reference to dataSource for GC to enjoy.
	        datasource = null;
	    }

	}

