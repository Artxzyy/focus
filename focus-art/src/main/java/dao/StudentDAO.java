package dao;

import model.Student;
import model.Person;

import java.security.MessageDigest;
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
	public static String encryptPassword(final String base) {
		try{
			final MessageDigest digest = MessageDigest.getInstance("SHA-256");
			final byte[] hash = digest.digest(base.getBytes("UTF-8"));
			final StringBuilder hexString = new StringBuilder();
			for (int i = 0; i < hash.length; i++) {
				final String hex = Integer.toHexString(0xff & hash[i]);
				if(hex.length() == 1)
					hexString.append('0');
				hexString.append(hex);
			}
			return hexString.toString();
		} catch(Exception ex){
			throw new RuntimeException(ex);
		}
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
	
	public boolean add(Person student, int prof_id) {
		boolean status = false;
		try {  
			Statement st = conexao.createStatement();
			st.executeUpdate("INSERT INTO person (school_id, id, first_name, surname, email, login, password) "
					       + "VALUES (" + student.getSchool_id() + ", "
					       +(student.getId())+ ", '"
					       + student.getFirst_name() + "', '"
					       + student.getSurname() + "', '"
					       + student.getEmail() + "', '"
					       + student.getLogin() + "', '"
					       + student.getPassword() + "');");
			st.executeUpdate("INSERT INTO student VALUES ("+ student.getId() +", "+ prof_id +")");
			st.close();
			status = true;
		} catch (SQLException u) {  
			throw new RuntimeException(u);
		}
		return status;
	}
	public boolean update(Person student, int id) {
		boolean status = false;
		try {  
			Statement st = conexao.createStatement();
			st.executeUpdate("UPDATE person SET login = '"+ 
			student.getLogin() +"', password = '"+ student.getPassword() +"' WHERE id = "+ id);
			st.close();
			status = true;
		} catch (SQLException u) {  
			throw new RuntimeException(u);
		}
		return status;
	}
	
	public Person[] getStudents() {
		Person[] students = null;
		try {
			conectar();
			Statement st = conexao.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE,ResultSet.CONCUR_READ_ONLY);
			ResultSet rs = st.executeQuery("SELECT * FROM student INNER JOIN person ON student.person_id"
					+ " = person.id");
	         if(rs.next()){
	             rs.last();
	             students = new Person[rs.getRow()];
	             rs.beforeFirst();
	             for(int i = 0; rs.next(); i++) {
	                students[i] = new Person(rs.getInt("school_id"), rs.getInt("person_id"), rs.getString("first_name"), rs.getString("surname"), rs.getString("email"), rs.getString("login"), rs.getString("password"));
	             }
	          }
	          close();
	          st.close();
		} catch (Exception e) {
			System.err.println("ERROR: "+ e.getMessage());
		}
		return students;
	}
	public Person get_by_id(int id) {
		Person person = null;
		try {
			conectar();
			Statement st = conexao.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE,ResultSet.CONCUR_READ_ONLY);
			ResultSet rs = st.executeQuery("SELECT school_id, id, first_name, surname, email, login, password FROM person WHERE id = "+ id);
			if(rs.first()) person = new Person(rs.getInt("school_id"), rs.getInt("id"), rs.getString("first_name"), rs.getString("surname"), rs.getString("email"), rs.getString("login"), rs.getString("password"));
			else throw new Exception("Something went wrong.");
		}catch(Exception e) {
			System.err.println("ERROR: "+ e);
		}
		return person;
	}
	public int get_by_email(String email) {
		conectar();
		int result = -1;
		try {
			conectar();
			Statement st = conexao.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE,ResultSet.CONCUR_READ_ONLY);
			ResultSet rs = st.executeQuery("SELECT school_id, id, first_name, surname, email, login, password FROM person WHERE email = '"+ email +"'");
			if(rs.first()) {result = rs.getInt("id");}
			else throw new Exception("Something went wrong.");
		}catch(Exception e) {
			System.err.println("ERROR: "+ e.getMessage());
		}
		close();
		return result;
	}
}