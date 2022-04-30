package model;

public class Content {
	public static int count = 1;
	public static int max_id;
	private int school_id;
	private int professor_id;
	private int id;
	private String title;
	private String subject;
	private String text;
	
	public Content() {
		this(0, 0, "", "", "");
	}
	public Content(int school_id, int professor_id, int id, String title, String subject, String text) {
		this.id = id;
		this.school_id = school_id;
		this.professor_id = professor_id;
		this.title = title;
		this.subject = subject;
		this.text = text;
	}
	public Content(int school_id, int professor_id, String title, String subject, String text) {
		this.id = count++;
		this.school_id = school_id;
		this.professor_id = professor_id;
		this.title = title;
		this.subject = subject;
		this.text = text;
		max_id = this.id;
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
	public String getSubject() {
		return subject;
	}
	public void setSubject(String subject) {
		this.subject = subject;
	}
	public String getText() {
		return text;
	}
	public void setText(String text) {
		this.text = text;
	}
	public static int getMax_id() {
		return max_id;
	}
	public int getId() {
		return id;
	}
	public Content clone() {
		Content copy = new Content(school_id, professor_id, id, title, subject, text);
		return copy;
	}
}
