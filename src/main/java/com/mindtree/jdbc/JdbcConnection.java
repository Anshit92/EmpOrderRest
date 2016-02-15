package com.mindtree.jdbc;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class JdbcConnection {
  
	public static Connection connectionJdbc;
	
	static Connection makeSFObject() throws ClassNotFoundException, SQLException
	{
     Class.forName("com.mysql.jdbc.Driver");
      connectionJdbc = DriverManager.getConnection("jdbc:mysql://13.76.141.253:3306/employee_order_db?user=root&password=Welcome123");
     return connectionJdbc;
   }
	
	public static Connection getConnectionObject() throws ClassNotFoundException, SQLException
	{
		if(connectionJdbc == null){
			connectionJdbc = makeSFObject();
		}
		return connectionJdbc;
	}
	
	public static void closeConn(Connection conn)
	{
		if(conn!=null)
		{
			try {
				conn.close();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}
}