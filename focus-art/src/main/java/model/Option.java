package model;

public class Option {
	public static int max_id = 1;
	private int school_id;
	private int professor_id;
	private int activity_id;
	private int id;
	private String option_text;
	private boolean is_correct;
	
	public Option() {
		this(0, 0, 0, "", false);
	}
	
	public Option(int school_id, int professor_id, int activity_id, String option_text, boolean is_correct){
		this.id = max_id++;
		this.school_id = school_id;
		this.professor_id = professor_id;
		this.activity_id = activity_id;
		this.option_text = option_text;
		this.is_correct = is_correct;
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
		return option_text;
	}

	public void setOption_text(String option_text) {
		this.option_text = option_text;
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
