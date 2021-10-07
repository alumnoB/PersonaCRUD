
package conexcion;

import java.sql.*;

/**
 * @author Maria Jesus Alvarez Vilca
 * En esta clase realizaremos una conexion con la base de datos
 * necesario tener el driver de mysql
 */
public class Conexion {
	
	private static String JDBC_DRIVER = "com.mysql.cj.jdbc.Driver";
	private static String JDBC_URL = "jdbc:mysql://localhost:3306/entidadNegocio?serverTimezone=UTC";
	private static String JDBC_USER = "root";
	private static String JDBC_PASS = "";
	private static Driver driver = null;
	

	@SuppressWarnings("deprecation")
	public static synchronized Connection getConnection() throws SQLException {
		if (driver == null) {
			try {
				@SuppressWarnings("rawtypes")
				Class jdbcDriverClass = Class.forName(JDBC_DRIVER);
				driver = (Driver) jdbcDriverClass.newInstance();
				DriverManager.registerDriver(driver);
			} catch (Exception e) {
				System.out.println("Fallo en cargar el driver JDBC");
				e.printStackTrace();
			}
		}
		return DriverManager.getConnection(JDBC_URL, JDBC_USER, JDBC_PASS);
	}
	
	public static void close(ResultSet rs) 
	{
		try {
			if (rs != null) {
				rs.close();
			}
		} catch (SQLException sqle) {
			sqle.printStackTrace();
		}
	}
	
	public static void close(PreparedStatement stmt) 
	{
		try {
			if (stmt != null) {
				stmt.close();
			}
		} catch (SQLException sqle) {
			sqle.printStackTrace();
		}
	}
	
	//Cierre de la conexion
	public static void close(Connection conn) {
		try {
			if (conn != null) {
				conn.close();
			}
		} catch (SQLException sqle) {
			sqle.printStackTrace();
		}
	}
}
