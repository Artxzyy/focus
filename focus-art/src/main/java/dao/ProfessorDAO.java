package dao;

import model.Student;
import model.Person;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
public class ProfessorDAO {
	private Connection conexao;
	
	public ProfessorDAO() {
		conexao = null;
	}
	
	public boolean conectar() {
		String driverName = "org.postgresql.Driver";                    
		String serverName = "localhost";
		String mydatabase = "Focus";
		int port = 5432;
		String url = "jdbc:postgresql://" + serverName + ":" + port +"/" + mydatabase;
		String username = "ti2cc";
		String password = "ti@cc";
		boolean status = false;

		try {
			Class.forName(driverName);
			conexao = DriverManager.getConnection(url, username, password);
			status = (conexao == null);
			System.out.println("Connection with Postgresql was successfull.");
		} catch (ClassNotFoundException e) { 
			System.err.println("Connection with Postgresql was not successfull -- Driver not found-- " + e.getMessage());
		} catch (SQLException e) {
			System.err.println("Connection with Postgresql was not successfull -- " + e.getMessage());
		}
		return status;
	}
	
	public boolean close() {
		boolean status = false;
		
		try {
			conexao.close();
			status = true;
		} catch (SQLException e) {
			System.err.println(e.getMessage());
		}
		return status;
	}
	
	public boolean add(Person professor) {
		boolean status = false;
		try {  
			Statement st = conexao.createStatement();
			st.executeUpdate("INSERT INTO person (school_id, id, first_name, surname, login, password) "
					       + "VALUES (" + professor.getSchool_id() + ", "
					       +(Person.getMaxId() + 1)+ ", '"
					       + professor.getFirst_name() + "', '"  
					       + professor.getSurname() + "', '"
					       + professor.getLogin() + "', '"
					       + professor.getPassword() + "');");
			st.executeUpdate("INSERT INTO professor VALUES (" + professor.getId() + ");");
			st.close();
			status = true;
		} catch (SQLException u) {  
			throw new RuntimeException(u);
		}
		return status;
	}
	
	public Person[] getProfessors() {
		Person[] students = null;
		try {
			conectar();
			Statement st = conexao.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE,ResultSet.CONCUR_READ_ONLY);
			ResultSet rs = st.executeQuery("SELECT * FROM professor INNER JOIN person ON professor.person_id"
					+ " = person.id");
	         if(rs.next()){
	             rs.last();
	             students = new Person[rs.getRow()];
	             rs.beforeFirst();
	             for(int i = 0; rs.next(); i++) {
	                students[i] = new Person(rs.getInt("school_id"), rs.getInt("person_id"), rs.getString("first_name"), rs.getString("surname"), rs.getString("login"), rs.getString("password"));
	             }
	          }
	          close();
	          st.close();
		} catch (Exception e) {
			System.err.println("ERROR: "+ e.getMessage());
		}
		return students;
	}
}