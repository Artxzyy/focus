package model;

public class Activity {
	public static int count = 13;
	public static int max_id;
	private int professor_id;
	private int id;
	private String title;
	private String subject;
	private String theme;
	private String statement;
	public Activity() {
		this(0, 0, "", "", "", "");
	}
	public Activity(int professor_id, int id, String title, String subject, String theme, String statement) {
		this.professor_id = professor_id;
		this.id = id;
		this.title = title;
		this.subject = subject;
		this.theme = theme;
		this.statement = statement;
	}
	public Activity(int professor_id, String title, String subject, String theme, String statement) {
		this.professor_id = professor_id;
		this.id = count++;
		this.title = title;
		this.subject = subject;
		this.theme = theme;
		this.statement = statement;
		max_id = this.id;
	}
	public static int getMax_id() {
		return max_id;
	}
	public int getProfessor_id() {
		return professor_id;
	}
	public int getId() {
		return id;
	}
	public String getTitle() {
		return title;
	}
	public String getSubject() {
		return subject;
	}
	public String getTheme() {
		return theme;
	}
	public String getStatement() {
		return statement;
	}
	public void setProfessor_id(int professor_id) {
		this.professor_id = professor_id;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public void setSubject(String subject) {
		this.subject = subject;
	}
	public void setTheme(String theme) {
		this.theme = theme;
	}
	public void setStatement(String statement) {
		this.statement = statement;
	}
	
	
}
