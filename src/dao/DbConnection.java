package dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DbConnection {

	static String bd = "biblioteca";
	static String parametros = "?useSSL=false&serverTimezone=UTC";
	// static String parametros="?useSSL=false"; //DEPENDE DE LA VERSION DEL DRIVER

	static String user = "root";
	static String password = "1234";

	static String url = "jdbc:mysql://localhost:3306/" + bd + parametros;

	Connection conn = null;

	public DbConnection() {

		// OBTENEMOS EL DRIVER PARA MYSQL
		try {
			Class.forName("com.mysql.cj.jdbc.Driver");

			// OBTENEMOS UNA CONEXION CON LOS PARAMETROS ESPECIFICADOS ANTERIORMENTE
			conn = DriverManager.getConnection(url, user, password);

			// SI CONN NO ES NULO, SIGNIFICA QUE PUDIMOS CONECTARNOS
			if (conn != null) {

				System.out.println("Connecting database [" + conn + "] OK");

			}

		} catch (ClassNotFoundException e) { // EXCEPCION OCURRIDA POR NO ENCONTRAR EL DRIVER

			System.out.println("Excepcion driver: " + e.getMessage());

		} catch (SQLException e) { // EXCEPCION OCURRIDA POR LA CONEXION

			System.err.println("Excepcion conexion: " + e.getMessage());

		}

	}

	public Connection getConnection() {

		return conn;

	}

	public void disconnect() {

		System.out.println("Closing database: [" + conn + "] OK");

		if (conn != null) {
			try {
				conn.close();
			} catch (SQLException e) {

				System.out.println(e);
			}
		}

	}

}

