package model;

public class Activity {
	public static final float STARTING_DIFFICULTY = 2.5f; // 0.0 - 5.0
	public static final boolean CORRECT_ANSWER = true;
	public static final boolean WRONG_ANSWER = false;
	public static int count = 20;
	public static int max_id;
	private int professor_id;
	private int id;
	private String title;
	private String subject;
	private String theme;
	private String statement;
	private float difficulty;
	private int qtt_answers;
	private int qtt_wrong_answers;
	public Activity() {
		this(0, 0, "", "", "", "", 2.5f, 0, 0);
	}
	public Activity(int professor_id, int id, String title, String subject, String theme, String statement, float difficulty, int qtt_answers, int qtt_wrong_answers) {
		this.professor_id = professor_id;
		this.id = id;
		this.title = title;
		this.subject = subject;
		this.theme = theme;
		this.statement = statement;
		this.difficulty = difficulty;
		this.qtt_answers = qtt_answers; this.qtt_wrong_answers = qtt_wrong_answers;
	}
	public Activity(int professor_id, String title, String subject, String theme, String statement) {
		this.professor_id = professor_id;
		this.id = count++;
		this.title = title;
		this.subject = subject;
		this.theme = theme;
		this.statement = statement;
		this.qtt_answers = this.qtt_wrong_answers = 0;
		max_id = this.id;
	}
	@Override
	public String toString() {
		return "Activity [professor_id=" + professor_id + ", id=" + id + ", title=" + title + ", subject=" + subject
				+ ", theme=" + theme + ", statement=" + statement + "]";
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
	public int getQttAnswers() {
		return qtt_answers;
	}
	public void setQttAnswers(int qtt_answers) {
		this.qtt_answers = qtt_answers;
	}
	public float getDifficulty() {
		return difficulty;
	}
	public void setDifficulty(float difficulty) {
		this.difficulty = difficulty;
	}
	public void updateDifficulty(boolean answer) {
		if(answer == WRONG_ANSWER) {
			this.qtt_wrong_answers++;
		}
		this.qtt_answers++;
		if(this.qtt_wrong_answers > 0) {
			this.difficulty = ((float)this.qtt_wrong_answers / (float)this.qtt_answers) * 5;
		}else {
			this.difficulty = 0.0f;
		}
	}
	public int getQttWrongAnswers() {
		return this.qtt_wrong_answers;
	}
	public void setQttWrongAnswers(int qtt_wrong_answers) {
		this.qtt_wrong_answers = qtt_wrong_answers;
	}
}
