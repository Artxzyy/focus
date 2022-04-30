package dao;

import model.Student;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
public class StudentDAO {
	private Connection conexao;
	
	public StudentDAO() {
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
	
	public boolean add(Student student) {
		boolean status = false;
		try {  
			Statement st = conexao.createStatement();
			st.executeUpdate("INSERT INTO student (school_id, professor_id, id, first_name, surname, login, password) "
					       + "VALUES (" + student.getSchool_id() + ", "
					       + student.getProfessor_id() + ", "
					       +(Student.getMaxId() + 1)+ ", '"
					       + student.getFirst_name() + "', '"  
					       + student.getSurname() + "', '"
					       + student.getLogin() + "', '"
					       + student.getPassword() + "');");
			st.close();
			status = true;
		} catch (SQLException u) {  
			throw new RuntimeException(u);
		}
		return status;
	}
	
	public Student[] getStudents() {
		Student[] students = null;
		try {
			conectar();
			Statement st = conexao.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE,ResultSet.CONCUR_READ_ONLY);
			ResultSet rs = st.executeQuery("SELECT * FROM student");
	         if(rs.next()){
	             rs.last();
	             students = new Student[rs.getRow()];
	             rs.beforeFirst();
	             for(int i = 0; rs.next(); i++) {
	                students[i] = new Student(rs.getInt("school_id"), rs.getInt("professor_id"), rs.getInt("id"), rs.getString("first_name"), rs.getString("surname"), rs.getString("login"), rs.getString("password"));
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