package dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class PersonDAO {
private Connection conexao;
	
	public PersonDAO() {
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
}
