package model;

public class Activity {
	public static final String[] SUBJECTS_LIST = {"CHEMISTRY", "MATH", "PHYSICS"};
	
	public static int max_id = 1;
	private int school_id;
	private int professor_id;
	private int id;
	private String title;
	private String statement;
	private String subject;
	
	public Activity() {
		this(0, 0, "", "", "");
	}
	public Activity(int school_id, int professor_id, String title, String statement, String subject) {
		this.id = max_id++;
		this.school_id = school_id;
		this.professor_id = professor_id;
		this.title = title;
		this.statement = statement;
		this.subject = subject;
	}
	public int getSchool_id() {
		return school_id;
	}
	public void setSchool_id(int school_id) {
		this.school_id = school_id;
	}
	public int getProfessor_id() {
		return professor_id;
	}
	public void setProfessor_id(int professor_id) {
		this.professor_id = professor_id;
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public String getStatement() {
		return statement;
	}
	public void setStatement(String statement) {
		this.statement = statement;
	}
	public String getSubject() {
		return subject;
	}
	public void setSubject(String subject) {
		this.subject = subject;
	}
	public int getId() {
		return id;
	}
	
}
