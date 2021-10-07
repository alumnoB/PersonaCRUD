package baseDeDatos;

import java.sql.*;
import java.sql.Date;
import java.time.LocalDate;
import java.util.*;

import conexcion.Conexion;
import perfiles.Persona;


public class PersonaJDBC {
	
	private final String SQL_INSERT = "INSERT INTO persona (id,nombre,fechaNacimiento) VALUES(?,?,?)";
	private final String SQL_UPDATE = "UPDATE persona SET nombre=?, fechaNacimiento=? WHERE id=?";
	private final String SQL_DELETE = "DELETE FROM persona WHERE id=?";
	private final String SQL_SELECT = "SELECT * FROM persona ORDER BY id";
	
	
	public int insert(int id, String nombre, LocalDate fechaNacimiento) 
	{
		Connection conn = null;
		PreparedStatement stmt = null;
		int rows = 0; //registros afectados
		
		
		Date date = Date.valueOf(fechaNacimiento);
	      
		
		try {
			conn = Conexion.getConnection();
			stmt = conn.prepareStatement(SQL_INSERT);
			int index = 1;//contador de columnas
			stmt.setInt(index++, id);
			stmt.setString(index++, nombre);//param 1 => ?
			stmt.setDate(index++,date);
			System.out.println("Ejecutando query:" + SQL_INSERT);
			rows = stmt.executeUpdate();//no. registros afectados
			System.out.println("Registros afectados:" + rows);
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			Conexion.close(stmt);
			Conexion.close(conn);
		}
		
		return rows;
	}
	
	public int update(int id, String nombre, LocalDate fechaNacimiento) 
	{
		Connection conn = null;
		PreparedStatement stmt = null;
		int rows = 0;
		
		Date date = Date.valueOf(fechaNacimiento);
		
		try {
			conn = Conexion.getConnection();
			System.out.println("Ejecutando query:" + SQL_UPDATE);
			stmt = conn.prepareStatement(SQL_UPDATE);
			int index = 1;
			stmt.setString(index++, nombre);
			stmt.setDate(index++, date);
			stmt.setInt(index, id);
			rows = stmt.executeUpdate();
			System.out.println("Registros actualizados:" + rows);
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			Conexion.close(stmt);
			Conexion.close(conn);
		}
		
		return rows;
	}
	
	public int delete(int id) {
		Connection conn = null;
		PreparedStatement stmt = null;
		int rows = 0;
		
		try {
			conn = Conexion.getConnection();
			System.out.println("Ejecutando query:" + SQL_DELETE);
			stmt = conn.prepareStatement(SQL_DELETE);
			stmt.setInt(1, id);
			rows = stmt.executeUpdate();
			System.out.println("Registros eliminados:" + rows);
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			Conexion.close(stmt);
			Conexion.close(conn);
		}
		
		return rows;
	}
	
	
	public List<Persona> select() {
		Connection conn = null;
		PreparedStatement stmt = null;
		ResultSet rs = null;
		Persona persona = null;
		List<Persona> personas = new ArrayList<Persona>();
		
		
		try {
			conn = Conexion.getConnection();
			stmt = conn.prepareStatement(SQL_SELECT);
			rs = stmt.executeQuery();
			while (rs.next()) {
				int id = rs.getInt(1);
				String nombre = rs.getString(2);
				Date date = rs.getDate(3);
				persona = new Persona();
				persona.setId(id);
				persona.setNombre(nombre);
				persona.setFechaNacimiento(date.toLocalDate());
				personas.add(persona);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			Conexion.close(rs);
			Conexion.close(stmt);
			Conexion.close(conn);
		}
			return personas;
	}
}


