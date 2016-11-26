package main.java.baseDeDatos;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;


public class ConexionBD {
	private Connection conector;
	private PreparedStatement sentencia;

	public ConexionBD() throws Exception {
			String ruta = "recursos/bdd";
			Class.forName("org.sqlite.JDBC");
			conector = DriverManager.getConnection("jdbc:sqlite:"+ruta);	
	}
	
	public void cargarDatosDeUsuario(String nombre, String datos) throws SQLException{
    	conector.setAutoCommit(false);
		sentencia = conector.prepareStatement("UPDATE Registros set Datos = ? WHERE Usuario = ?");
		sentencia.setString(1, datos);
		sentencia.setString(2, nombre);
		sentencia.executeUpdate();
		sentencia.close();
		conector.commit();
	}
	
	public String obtenerDatosDeUsuario(String nombre) throws SQLException{
		
		conector.setAutoCommit(false);
		
		PreparedStatement stmt = conector.prepareStatement("SELECT * FROM Registros WHERE Usuario = ?");
		stmt.setString(1, nombre);
		ResultSet rs = stmt.executeQuery();
		String cadena = rs.getString("Datos");
		rs.close();
		conector.commit();
		return cadena;
	}
	public void cargarUsuario(String nombre, String contrasena, String dato) throws SQLException {
		
		conector.setAutoCommit(false);
		sentencia = conector.prepareStatement("INSERT INTO Registros (Usuario, Contraseña, Datos) values (?,?,?);");
		sentencia.setString(1, nombre);
		sentencia.setString(2, contrasena);
		sentencia.setString(3, dato);
		sentencia.executeUpdate();
		sentencia.close();
		conector.commit();
	}

	public boolean consultarUsuario(String nombre, String contrasena) throws SQLException {

		conector.setAutoCommit(false);
	
		sentencia = conector.prepareStatement("Select Count(*) from Registros where Usuario like ? AND Contraseña=?");
	
		sentencia.setString(1, nombre);
		sentencia.setString(2, contrasena);
		ResultSet resultado = sentencia.executeQuery();
		boolean devolver = resultado.next() ? resultado.getInt(1) == 1 : false;
		sentencia.close();
		conector.commit();

		return devolver;
	}
	
	public boolean validarNombreUsuario(String nombre) throws Exception {
		conector.setAutoCommit(false);
		sentencia = conector.prepareStatement("Select Count(*) from Registros where Usuario like ?");
		sentencia.setString(1, nombre);
		ResultSet resultado = sentencia.executeQuery();
		boolean devolver = resultado.next() ? resultado.getInt(1) == 1 : false;
		sentencia.close();
		conector.commit();
		return devolver;

	}
}
