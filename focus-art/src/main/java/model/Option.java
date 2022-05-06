package model;

public class Option {
	public static int count = 80;
	public static int max_id;
	private int school_id;
	private int professor_id;
	private int activity_id;
	private int id;
	private String text;
	private boolean is_correct;
	
	public Option() {
		this(0, 0, 0, "", false);
	}
	
	public Option(int school_id, int professor_id, int activity_id, String text, boolean is_correct){
		this.id = count++;
		this.school_id = school_id;
		this.professor_id = professor_id;
		this.activity_id = activity_id;
		this.text = text;
		this.is_correct = is_correct;
		max_id = this.id;
	}
	public Option(int activity_id, int id, String text, boolean is_correct){
		this.activity_id = activity_id;
		this.id = id;
		this.text = text;
		this.is_correct = is_correct;
	}
	public Option(int activity_id, String text, boolean is_correct){
		this.activity_id = activity_id;
		this.id = count++;
		this.text = text;
		this.is_correct = is_correct;
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

	public int getActivity_id() {
		return activity_id;
	}

	public void setActivity_id(int activity_id) {
		this.activity_id = activity_id;
	}

	public String getOption_text() {
		return text;
	}

	public void setOption_text(String text) {
		this.text = text;
	}

	public boolean isIs_correct() {
		return is_correct;
	}

	public void setIs_correct(boolean is_correct) {
		this.is_correct = is_correct;
	}

	public int getId() {
		return id;
	}
	
}
