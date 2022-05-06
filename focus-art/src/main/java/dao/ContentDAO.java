package dao;

import model.Content;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class ContentDAO extends DAO {
	public ContentDAO() {super(); conectar();}	
	public void finalize() {close();}
	
	public boolean insert(Content content) {
		boolean status = false;
		
		try {
			String sql = "INSERT INTO content (professor_id, id, title, subject, theme, text) "
					   + "VALUES ('" + content.getProfessor_id() + "', '" 
					   + (getMaxContentID() + 1) + "', '"
					   + content.getTitle() + "', '"
					   + content.getSubject() + "', '"
					   + content.getTheme() + "', '"
					   + content.getText() + "');";
			PreparedStatement st = conexao.prepareStatement(sql);
			st.executeUpdate();
			st.close();
			status = true;
		} catch (SQLException u) {
			throw new RuntimeException(u);
		}
		
		return status;
	}
	
	public Content get(int id) {
		Content content = null;
		try {
			conectar();
			Statement st = conexao.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE,ResultSet.CONCUR_READ_ONLY);
			ResultSet rs = st.executeQuery("SELECT * FROM content WHERE id = "+ id);
			if(rs.first()) content = new Content(rs.getInt("professor_id"), rs.getInt("id"), rs.getString("title"), rs.getString("subject"), rs.getString("theme"), rs.getString("text"));
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
			ResultSet rs = st.executeQuery("SELECT * FROM content INNER JOIN person ON content.professor_id"
					+ " = person.id");
	         if(rs.next()){
	             rs.last();
	             content = new Content[rs.getRow()];
	             rs.beforeFirst();
	             for(int i = 0; rs.next(); i++) {
	            	 content[i] = new Content(rs.getInt("professor_id"), rs.getInt("id"), rs.getString("title"), rs.getString("subject"), rs.getString("theme"), rs.getString("text"));
	             }
	          }
	          close();
	          st.close();
		} catch (Exception e) {
			System.err.println("ERROR: "+ e.getMessage());
		}
		return content;
	}
	
	public Content[] getAllContents() {
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
	            	 content[i] = new Content(rs.getInt("professor_id"), rs.getInt("id"), rs.getString("title"), rs.getString("subject"), rs.getString("theme"), rs.getString("text"));
	             }
	          }
	          close();
	          st.close();
		} catch (Exception e) {
			System.err.println("ERROR: "+ e.getMessage());
		}
		return content;
	}
	
	public boolean update(Content content) {
		boolean status = false;
		try {
			conectar();
			String sql = "UPDATE content SET professor_id ='" + content.getProfessor_id() + "'," + "title= '" + content.getTitle() + "', "
					   + "subject = '" + content.getSubject() + "', " 
					   + "theme = '" + content.getTheme() + "', "
					   + "text = '" + content.getText() + "' WHERE id = " 
					   + content.getId();
			PreparedStatement st = conexao.prepareStatement(sql);
			st.executeUpdate();
			status = true;
			st.close();
		} catch (SQLException u) {
			throw new RuntimeException(u);
		}
		return status;
	}
	
	public boolean delete(int id) {
		boolean status = false;
		try {
			conectar();
			Statement st = conexao.createStatement();
			st.executeUpdate("DELETE FROM content WHERE id = " + id);
			st.close();
			status = true;
		} catch (SQLException u) {
			throw new RuntimeException(u);
		}
		return status;
	}
	
	public int getMaxContentID() {
		int maxID = 0;
		
		try {
			Statement st = conexao.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
			String sql = "SELECT MAX(id) AS \"id\" FROM content;";
			ResultSet rs = st.executeQuery(sql);
			if(rs.next()) maxID = rs.getInt("id");
			st.close();
		} catch (Exception e) {
			System.err.println(e.getMessage());
		}
		
		return maxID;
	}

	public String checkUserType(int userID) {
		String tipo = "";
		try {
			conectar();
			Statement st_student = conexao.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE,ResultSet.CONCUR_READ_ONLY);
			ResultSet rs_student = st_student.executeQuery("SELECT person_id FROM student WHERE person_id = "+ userID);
			if(rs_student.first()) {
				tipo = "aluno";
				st_student.close();
			}
			else if (!rs_student.first()){
				Statement st_teacher = conexao.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE,ResultSet.CONCUR_READ_ONLY);
				ResultSet rs_teacher = st_teacher.executeQuery("SELECT person_id FROM professor WHERE person_id = "+ userID);
				if(rs_teacher.first()) {
					tipo = "professor";
				}
				st_teacher.close();
			}
		}catch(Exception e) {
			System.err.println("ERROR:"+ e.getMessage());
		}
		return tipo;
	}

}