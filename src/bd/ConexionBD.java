package bd;

import java.io.IOException;
import java.sql.SQLException;
import java.util.Properties;

public class ConexionBD {
	public static JDBCTemplate conectar() {
		JDBCTemplate q = null;
		Properties properties = new Properties();
		try {
			properties.load(JDBCTemplate.class
					.getResourceAsStream("bd.properties"));
			Configuration config = new MySQLConfiguration(
					properties.getProperty("database.host"),
					properties.getProperty("database.port"),
					properties.getProperty("database.sid"));			
			q = new JDBCTemplate(config,
					properties.getProperty("database.user"),
					properties.getProperty("database.password"));
			q.connect();
			System.out.println("Conectado a " + q);

		} catch (IOException e) {
			System.out.println("Error: " + e.getMessage());
		} catch (InstantiationException e) {
			System.out.println("Error: " + e.getMessage());
		} catch (IllegalAccessException e) {
			System.out.println("Error: " + e.getMessage());
		} catch (ClassNotFoundException e) {
			System.out.println("Error: " + e.getMessage());
		} catch (SQLException e) {
			System.out.println("Error: " + e.getMessage());
		} 
		return q;
	}	
	public static void desconectar(JDBCTemplate ot) {
		if (ot != null) {
			ot.disconnect();
			System.out.println("Desconectado de " + ot);
		}
	}
}
