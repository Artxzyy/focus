package dao;
// SELECT activity.title, activity.subject, activity.theme, activity.statement, 
// person.first_name, person.surname, option.text, option.is_correct FROM activity 
// INNER JOIN student ON activity.professor_id = student.professor_id AND 
// student.person_id = 7 INNER JOIN person ON person.id = student.professor_id INNER 
// JOIN option ON option.activity_id = activity.id
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import model.Activity;
import model.Option;

public class ActivityDAO {
	private Connection conexao;
	
	public ActivityDAO() {
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
	
	public Activity[] get_student_activities(int student_id) {
		Activity[] activities = null;
		try {
			conectar();
			Statement st = conexao.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE,ResultSet.CONCUR_READ_ONLY);
			ResultSet rs = st.executeQuery("SELECT activity.professor_id, activity.id, activity.title, activity.subject, activity.theme, "
					+ "activity.statement "
					+ "FROM activity INNER JOIN student ON activity."
					+ "professor_id = student.professor_id AND student.person_id = "+student_id);
			if(rs.next()) {
				rs.last();
				activities = new Activity[rs.getRow()];
				rs.beforeFirst();
				for(int i = 0; rs.next(); i++) {
					activities[i] = new Activity(rs.getInt("professor_id"), rs.getInt("id"), rs.getString("title"), rs.getString("subject"),
							rs.getString("theme"), rs.getString("statement"));
				}
			}
			close();
		}catch(Exception e) {
			System.err.println("ERROR: " + e);
		}
		return activities;
	}
	public Activity[] get_professor_activities(int professor_id) {
		Activity[] activities = null;
		try {
			conectar();
			Statement st = conexao.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE,ResultSet.CONCUR_READ_ONLY);
			ResultSet rs = st.executeQuery("SELECT activity.professor_id, activity.id, activity.title, activity.subject, activity.theme, "
					+ "activity.statement "
					+ "FROM activity WHERE activity."
					+ "professor_id = "+professor_id);
			if(rs.next()) {
				rs.last();
				activities = new Activity[rs.getRow()];
				rs.beforeFirst();
				for(int i = 0; rs.next(); i++) {
					activities[i] = new Activity(rs.getInt("professor_id"), rs.getInt("id"), rs.getString("title"), rs.getString("subject"),
							rs.getString("theme"), rs.getString("statement"));
				}
			}
			close();
		}catch(Exception e) {
			System.err.println("ERROR: " + e);
		}
		return activities;
	}
	public Option[] get_options(Activity[] activities) {
		Option[] options = null;
		try {
			conectar();
			String parcial_statement = "";
			for(int i = 1; i < activities.length; i++) {
				parcial_statement += " OR option.activity_id = "+activities[i].getId();
			}
			Statement st = conexao.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE,ResultSet.CONCUR_READ_ONLY);
			ResultSet rs = st.executeQuery("SELECT option.id, option.text, "
					+ "option.is_correct FROM option INNER JOIN activity ON option.activity_id"
					+ " = "+activities[0].getId() + parcial_statement);
			if(rs.next()) {
				rs.last();
				options = new Option[rs.getRow()];
				for(int i = 0; rs.next(); i++) {
					options[i] = new Option(rs.getInt("id"), rs.getString("text"), rs.getBoolean("is_correct"));
				}
			}
			close();
		}catch(Exception e) {
			System.err.println("ERROR: "+ e);
		}
		return options;
	}
	public Activity get_by_id(int id) {
		Activity a = null;
		try {
			conectar();
			Statement st = conexao.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE,ResultSet.CONCUR_READ_ONLY);
			ResultSet rs = st.executeQuery("SELECT professor_id, id, title, subject, theme, statement FROM activity WHERE id = "+id);
			if(rs.first()) a = new Activity(rs.getInt("professor_id"), rs.getInt("id"), rs.getString("title"), rs.getString("subject")
					, rs.getString("theme"), rs.getString("statement"));
			else throw new Exception("Something went wrong.");
			close();
		}catch(Exception e) {
			close();
			System.err.println("ERROR: "+ e);
		}
		return a;
	}
	public Option[] get_options_by_activity(Activity a) {
		Option[] options = null;
		try {
			conectar();
			Statement st = conexao.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE,ResultSet.CONCUR_READ_ONLY);
			ResultSet rs = st.executeQuery("SELECT option.id, option.text, option.is_correct FROM option"
					+ " WHERE option.activity_id = "+a.getId());
			if(rs.first()) {
				rs.last();
				options = new Option[rs.getRow()];
				rs.beforeFirst();
				for(int i = 0; rs.next(); i++) {
					options[i] = new Option(a.getId(), rs.getInt("id"), rs.getString("text"), rs.getBoolean("is_correct"));
				}
			}
			close();
		}catch(Exception e) {
			System.err.println("ERROR: "+ e);
		}
		return options;
	}
	public Option get_correct_option_by_activity_id(int id) {
		Option result = null;
		try {
			conectar();
			Statement st = conexao.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE,ResultSet.CONCUR_READ_ONLY);
			ResultSet rs = st.executeQuery("SELECT option.activity_id, option.id, option.text FROM option"
					+ " WHERE option.activity_id = "+ id +" AND option.is_correct = TRUE");
			rs.first();
			result = new Option(rs.getInt("activity_id"), rs.getInt("id"), rs.getString("text"), true);
		}catch(Exception e) {
			System.err.println("ERROR:"+ e.getMessage());
		}
		return result;
	}
	public boolean add(Activity activity, Option[] options) {
		boolean result = false;
		try {
			conectar();
			Statement st = conexao.createStatement();
			st.executeUpdate("INSERT INTO activity VALUES ("
					+ activity.getProfessor_id() + ", "
					+ activity.getId() + ", "
					+ "'" + activity.getTitle() + "', "
					+ "'" + activity.getSubject() + "', "
					+ "'" + activity.getTheme() + "', "
					+ "'" + activity.getStatement() + "');");
			for(Option i : options) {
				st.executeUpdate("INSERT INTO option VALUES ("
						+ activity.getId() + ", "
						+ i.getId() + ", "
						+ "'" +i.getOption_text() + "', "
						+ i.isIs_correct()+ ");");	
			}
			close();
		}catch(Exception e) {
			System.err.println("ERROR: "+ e);
		}
		return result;
	}
	public boolean delete(int id) {
		boolean result = false;
		try {
			conectar();
			Statement st = conexao.createStatement();
			st.executeUpdate("DELETE FROM option WHERE activity_id = "+id);
			st.executeUpdate("DELETE FROM activity WHERE id = "+id);
			close();
			result = true;
		}catch(Exception e) {
			System.err.println("ERROR: "+e.getMessage());
		}
		return result;
	}
	public boolean update(Activity a, int id) {
		boolean result = false;
		try {
			conectar();
			Statement st = conexao.createStatement();
			st.executeUpdate("UPDATE activity SET title = "
					+ "'" +a.getTitle() + "', subject = "
					+ "'" + a.getSubject() + "', theme = "
					+ "'" + a.getTheme() + "', statement = "
					+ "'" + a.getStatement() + "'"
							+ " WHERE id = "+id);
			result = true;
			close();
		}catch(Exception e) {
			System.err.println("ERROR: "+ e);
		}
		return result;
	}
}
