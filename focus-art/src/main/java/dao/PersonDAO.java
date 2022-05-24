package dao;

import java.security.MessageDigest;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import model.Activity;
import model.Person;

public class PersonDAO {
private Connection conexao;
	
	public PersonDAO() {
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
	
	public boolean is_professor(int id) {
		boolean result = false;
		try {
			conectar();
			Statement st = conexao.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE,ResultSet.CONCUR_READ_ONLY);
			ResultSet rs = st.executeQuery("SELECT * FROM professor WHERE person_id = "+id);
			if(rs.first()) result = true;
			close();
		}catch(Exception e) {
			System.err.println("ERROR: "+ e);
		}
		return result;
	}
	
	public Person get_by_id(int id) {
		Person person = null;
		try {
			conectar();
			Statement st = conexao.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE,ResultSet.CONCUR_READ_ONLY);
			ResultSet rs = st.executeQuery("SELECT school_id, id, first_name, surname FROM person WHERE id = "+ id);
			if(rs.first()) person = new Person(rs.getInt("school_id"), rs.getInt("id"), rs.getString("first_name"), rs.getString("surname"));
			else throw new Exception("Something went wrong.");
		}catch(Exception e) {
			System.err.println("ERROR: "+ e);
		}
		return person;
	}
	public Person professor_of(int id) {
		Person person = null;
		try {
			conectar();
			Statement st = conexao.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE,ResultSet.CONCUR_READ_ONLY);
			ResultSet rs = st.executeQuery("SELECT professor_id FROM student WHERE person_id = "+ id);
			if(rs.first()) person = get_by_id(rs.getInt("professor_id"));
			else throw new Exception("Something went wrong.");
		}catch(Exception e) {
			System.err.println("ERROR: "+ e);
		}
		return person;
	}
	public boolean update(Person p, int id) {
		boolean result = false;
		try {
			conectar();
			Statement st = conexao.createStatement();
			st.executeUpdate("UPDATE person SET school_id = "
					+ "" +p.getSchool_id() + ", id = "
					+ "" + p.getId() + ", first_name = "
					+ "'" + p.getFirst_name() + "', surname = "
					+ "'" + p.getSurname() + "', login = "
					+ "'" + p.getLogin() + "', password = "
					+ "'" + p.getPassword() + "'"
							+ " WHERE id = "+id);
			result = true;
			close();
		}catch(Exception e) {
			System.err.println("ERROR: "+ e);
			result = false;
		}
		return result;
	}
}
