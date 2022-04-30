package dao;

import model.Content;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
public class ContentDAO {
	private Connection conexao;
	
	public ContentDAO() {
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
	public Content get(int id) {
		Content content = null;
		try {
			conectar();
			Statement st = conexao.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE,ResultSet.CONCUR_READ_ONLY);
			ResultSet rs = st.executeQuery("SELECT * FROM content WHERE id = "+ id);
			if(rs.first()) content = new Content(rs.getInt("school_id"), rs.getInt("professor_id"), rs.getInt("id"), rs.getString("title"), rs.getString("subject"), rs.getString("text"));
			close();
			st.close();
		}catch(Exception e) {
			System.err.println("ERROR:"+ e.getMessage());
		}
		return content;
	}
	public Content[] getContents() {
		Content[] content = null;
		try {
			conectar();
			Statement st = conexao.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE,ResultSet.CONCUR_READ_ONLY);
			ResultSet rs = st.executeQuery("SELECT * FROM content");
	         if(rs.next()){
	             rs.last();
	             content = new Content[rs.getRow()];
	             rs.beforeFirst();
	             for(int i = 0; rs.next(); i++) {
	            	 content[i] = new Content(rs.getInt("school_id"), rs.getInt("professor_id"), rs.getInt("id"), rs.getString("title"), rs.getString("subject"), rs.getString("text"));
	             }
	          }
	          close();
	          st.close();
		} catch (Exception e) {
			System.err.println("ERROR: "+ e.getMessage());
		}
		return content;
	}
}