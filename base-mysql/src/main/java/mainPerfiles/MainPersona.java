package mainPerfiles;

import java.util.List;
import java.util.Scanner;
import java.sql.*;
import java.time.LocalDate;

import perfiles.Persona;
import baseDeDatos.PersonaJDBC;
import conexcion.Conexion;
/**
 * @author Maria Jesus Alvarez Vilca
 * En esta clase probaremos los metodos de insertar, actualizar, borrar y listar
 * y el apartado listar se guarda en un archivo datos.cvs
 */

public class MainPersona {

	@SuppressWarnings("static-access")
	public static void main(String[] args) {
		
		Scanner tk = new Scanner(System.in);
		PersonaJDBC personaJDBC = new PersonaJDBC();
		
		
		///Atributos en la tabla persona
		String nombre = " ";
		int id, numevoId, year, mes, dia;
		LocalDate fechaNacimiento = null;
		//indice bucles
		
		int op;
		
		//Creamos un objeto conexion, se va a compartir
		//para todos los queries que ejecutemos
		Connection conn = null;
		
		boolean inicio = false;
		
		System.out.print("Accediendo a la base de datos persona");
		while (inicio == false)
		{ 

			System.out.print("\nOperaciones a realizar en la tabla Persona");
			System.out.print("\n\t1 Insetar persona");
			System.out.print("\n\t2 Actualizar persona");
			System.out.print("\n\t3 Borrar persona");
			System.out.print("\n\t4 Listar persona");
			System.out.print("\n\t5 Salir");
			System.out.print("\nOp: ");
			
			op = tk.nextInt();
			
			switch(op)
			{
			case 1:
			{	
				
				System.out.print("\nIntroduce el codigo de la persona");
				id = tk.nextInt();
				
				tk.nextLine();
				
				System.out.print("\nIntroduce el nombre de la persona");
				nombre = tk.nextLine();
				
				System.out.println("\nIntroduce la fecha de nacimiento con formato: año, mes, dia");
				
				System.out.println("Para comenzar introduce el año:");
			    year = tk.nextInt();
			    
			    System.out.println("Para comenzar introduce el mes:");
			    mes = tk.nextInt();
			    
			    System.out.println("Para comenzar introduce el dia:");
			    dia = tk.nextInt();
			    
				fechaNacimiento = fechaNacimiento.of(year, mes, dia);
									
				try {
					conn = Conexion.getConnection();
					//Revisamos si la conexion esta en modo autocommit
					//por default es autocommit == true
					if (conn.getAutoCommit()) {
						conn.setAutoCommit(false);
					}
					
					personaJDBC.insert(id,nombre,fechaNacimiento);
					conn.commit();

				}catch (SQLException e) {
					//Hacemos rollback en caso de error
					try {
						System.out.println("Entramos al rollback");
						//Imprimimos la excepcion a la consola
						e.printStackTrace(System.out);
						//Hacemos rollback
						conn.rollback();
					} catch (SQLException e1) {
						e1.printStackTrace(System.out);
					}
				}


				break;
			}
			
			case 2:
			{
				

				System.out.print("\n¿Que empleado desea actulizar?");
				numevoId = tk.nextInt();
				
				tk.nextLine();
				
				System.out.print("\nIntroduce el nuevo nombre de la persona");
				nombre = tk.nextLine();
				
				System.out.println("\nIntroduce la fecha de nacimiento con formato: año, mes, dia");
				
				System.out.println("Para comenzar introduce el año:");
			    year = tk.nextInt();
			    
			    System.out.println("Para comenzar introduce el mes:");
			    mes = tk.nextInt();
			    
			    System.out.println("Para comenzar introduce el dia:");
			    dia = tk.nextInt();
			    
				fechaNacimiento = fechaNacimiento.of(year, mes, dia);
		        
				
				try {
					conn = Conexion.getConnection();
					//Revisamos si la conexion esta en modo autocommit
					//por default es autocommit == true
					if (conn.getAutoCommit()) {
						conn.setAutoCommit(false);
					}
					
					 personaJDBC.update(numevoId,nombre,fechaNacimiento);
					conn.commit();

				}catch (SQLException e) {
					//Hacemos rollback en caso de error
					try {
						System.out.println("Entramos al rollback");
						//Imprimimos la excepcion a la consola
						e.printStackTrace(System.out);
						//Hacemos rollback
						conn.rollback();
					} catch (SQLException e1) {
						e1.printStackTrace(System.out);
					}
				}
				
				break;
			}
			
			case 3:
			{
				System.out.print("\nEscribe el codigo de la persona que desea eliminar");
				id = tk.nextInt();
				
				try {
					conn = Conexion.getConnection();
					//Revisamos si la conexion esta en modo autocommit
					//por default es autocommit == true
					if (conn.getAutoCommit()) {
						conn.setAutoCommit(false);
					}
					
					personaJDBC.delete(id);
					conn.commit();

				}catch (SQLException e) {
					//Hacemos rollback en caso de error
					try {
						System.out.println("Entramos al rollback");
						//Imprimimos la excepcion a la consola
						e.printStackTrace(System.out);
						//Hacemos rollback
						conn.rollback();
					} catch (SQLException e1) {
						e1.printStackTrace(System.out);
					}
				}
				
				break;
			}
			
			case 4:
			{	
				try {
					
					conn = Conexion.getConnection();
					//Revisamos si la conexion esta en modo autocommit
					//por default es autocommit == true
					if (conn.getAutoCommit()) {
						conn.setAutoCommit(false);
					}
					
					List<Persona> personas =personaJDBC.select();
					
					for (Persona persona : personas) {
						System.out.print(persona);
						System.out.println("");
					}
					
					conn.commit();
				}catch (SQLException e) {
					//Hacemos rollback en caso de error
					try {
						System.out.println("Entramos al rollback");
						//Imprimimos la excepcion a la consola
						e.printStackTrace(System.out);
						//Hacemos rollback
						conn.rollback();
					} catch (SQLException e1) {
						e1.printStackTrace(System.out);
					}
				}
				break;
			}
			
			case 5:
			{	
				
				System.out.print("Saliendo del sistema");
				inicio = true;
				break;
			}
				
			default:
			{
				System.out.println("Ingresar opcion valida");
				break;
			}
				
		}
			
	}
		
		tk.close();
}

}
