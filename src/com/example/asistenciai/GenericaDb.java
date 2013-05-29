package com.example.asistenciai;

import java.sql.*;

public class GenericaDb {
	public String driver, url, ip, bd, usr, pass;
	public Connection conexion;

	public GenericaDb(String ip, String bd, String usr, String pass) {
		driver = "com.mysql.jdbc.Driver";
		this.bd = bd;
		this.usr = usr;
		this.pass = pass;
		url = new String("jdbc:mysql://" + ip + "/" + bd);
		try {
			Class.forName(driver).newInstance();
			conexion = DriverManager.getConnection(url, usr, pass);
			System.out.println("Conexion a Base de Datos " + bd + " Ok");
		} catch (Exception exc) {
			System.out.println("Error al tratar de abrir la base de Datos" + bd
					+ " : " + exc);
		}
	}

	public Connection getConexion() {
		return conexion;
	}

	public Connection CerrarConexion() throws SQLException {
		conexion.close();
		conexion = null;
		return conexion;
	}
	
	public void insertaDatos() {

		try {
			PreparedStatement stmt = conexion.prepareStatement("INSERT INTO t_seguridad VALUES (?,?,?,?)");

			stmt.setInt(1, 50);
			stmt.setString(2, "Key J");
			stmt.setString(3, "Key P");
			stmt.setString(4, "someemail@mail.com");

			int retorno = stmt.executeUpdate();

			System.out.println(retorno);
		} catch (SQLException sqle) {
			System.out.println("SQLState: " + sqle.getSQLState());
			System.out.println("SQLErrorCode: " + sqle.getErrorCode());
			sqle.printStackTrace();

		}

	}
} // fin de la clase
