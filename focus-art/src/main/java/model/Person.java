package model;

public class Person {
	public static int count = 20;
	public static int max_id = 21;
	private int school_id; // fk
	private int id; // pk
	private String first_name;
	private String surname;
	private String email;
	private String login;
	@Override
	public String toString() {
		return "Person [school_id=" + school_id + ", id=" + id + ", first_name=" + first_name + ", surname=" + surname
				+ ", email=" + email + ", login=" + login + ", password=" + password + ", qtt_answers=" + qtt_answers
				+ ", qtt_wrong_answers=" + qtt_wrong_answers + ", qtt_sum_answers=" + qtt_sum_answers
				+ ", qtt_correct_sum_answers=" + qtt_correct_sum_answers + ", qtt_sub_answers=" + qtt_sub_answers
				+ ", qtt_correct_sub_answers=" + qtt_correct_sub_answers + ", qtt_mul_answers=" + qtt_mul_answers
				+ ", qtt_correct_mul_answers=" + qtt_correct_mul_answers + ", qtt_div_answers=" + qtt_div_answers
				+ ", qtt_correct_div_answers=" + qtt_correct_div_answers + "]";
	}
	private String password;
	
	private int qtt_answers;
	private int qtt_wrong_answers;
	private int qtt_sum_answers;
	private int qtt_correct_sum_answers;
	private int qtt_sub_answers;
	private int qtt_correct_sub_answers;
	private int qtt_mul_answers;
	private int qtt_correct_mul_answers;
	private int qtt_div_answers;
	private int qtt_correct_div_answers;

	public int getQtt_answers() {
		return qtt_answers;
	}

	public int getQtt_wrong_answers() {
		return qtt_wrong_answers;
	}

	public int getQtt_sum_answers() {
		return qtt_sum_answers;
	}

	public int getQtt_correct_sum_answers() {
		return qtt_correct_sum_answers;
	}

	public int getQtt_sub_answers() {
		return qtt_sub_answers;
	}

	public int getQtt_correct_sub_answers() {
		return qtt_correct_sub_answers;
	}

	public int getQtt_mul_answers() {
		return qtt_mul_answers;
	}

	public int getQtt_correct_mul_answers() {
		return qtt_correct_mul_answers;
	}

	public int getQtt_div_answers() {
		return qtt_div_answers;
	}

	public int getQtt_correct_div_answers() {
		return qtt_correct_div_answers;
	}

	public void setQtt_answers(int qtt_answers) {
		this.qtt_answers = qtt_answers;
	}

	public void setQtt_wrong_answers(int qtt_wrong_answers) {
		this.qtt_wrong_answers = qtt_wrong_answers;
	}

	public void setQtt_sum_answers(int qtt_sum_answers) {
		this.qtt_sum_answers = qtt_sum_answers;
	}

	public void setQtt_correct_sum_answers(int qtt_correct_sum_answers) {
		this.qtt_correct_sum_answers = qtt_correct_sum_answers;
	}

	public void setQtt_sub_answers(int qtt_sub_answers) {
		this.qtt_sub_answers = qtt_sub_answers;
	}

	public void setQtt_correct_sub_answers(int qtt_correct_sub_answers) {
		this.qtt_correct_sub_answers = qtt_correct_sub_answers;
	}

	public void setQtt_mul_answers(int qtt_mul_answers) {
		this.qtt_mul_answers = qtt_mul_answers;
	}

	public void setQtt_correct_mul_answers(int qtt_correct_mul_answers) {
		this.qtt_correct_mul_answers = qtt_correct_mul_answers;
	}

	public void setQtt_div_answers(int qtt_div_answers) {
		this.qtt_div_answers = qtt_div_answers;
	}

	public void setQtt_correct_div_answers(int qtt_correct_div_answers) {
		this.qtt_correct_div_answers = qtt_correct_div_answers;
	}

	public Person() {
		this(0, "", "", "", "", "", 0, 0, 0, 0, 0, 0, 0, 0, 0, 0);
	}
	
	public Person(int school_id, String first_name, String surname,
			String email, String login, String password,
			int qtt_answers, int qtt_wrong_answers, int qtt_sum_answers, int qtt_correct_sum_answers,
			int qtt_sub_answers, int qtt_correct_sub_answers, int qtt_mul_answers,
			int qtt_correct_mul_answers, int qtt_div_answers, int qtt_correct_div_answers) {
		this.id = count++;
		this.school_id = school_id;
		this.first_name = first_name;
		this.surname = surname;
		try {
			if (login.length() < 3) throw new Exception("Login is too short.");
			this.login = login;
			this.email = email;
			this.password = password;
			max_id = this.id;
			this.qtt_answers = qtt_answers;
			this.qtt_wrong_answers = qtt_wrong_answers;
			this.qtt_sum_answers = qtt_sum_answers;
			this.qtt_correct_sum_answers = qtt_correct_sum_answers;
			this.qtt_sub_answers = qtt_sub_answers;
			this.qtt_correct_sub_answers = qtt_correct_sub_answers;
			this.qtt_mul_answers = qtt_mul_answers;
			this.qtt_correct_mul_answers = qtt_correct_mul_answers;
			this.qtt_div_answers = qtt_div_answers;
			this.qtt_correct_div_answers = qtt_correct_div_answers;
		}catch(Exception e) {
			System.err.println("ERROR: "+ e.getMessage());
		}
	}
	public Person(int school_id, int id, String first_name, String surname,
			String email, String login, String password,
			int qtt_answers, int qtt_wrong_answers, int qtt_sum_answers, int qtt_correct_sum_answers,
			int qtt_sub_answers, int qtt_correct_sub_answers, int qtt_mul_answers,
			int qtt_correct_mul_answers, int qtt_div_answers, int qtt_correct_div_answers) {
		this.id = id;
		this.school_id = school_id;
		this.first_name = first_name;
		this.surname = surname;
		try {
			if (login.length() < 3) throw new Exception("Login is too short.");
			this.login = login;
			this.email = email;
			this.password = password;
			max_id = this.id;
			this.qtt_answers = qtt_answers;
			this.qtt_wrong_answers = qtt_wrong_answers;
			this.qtt_sum_answers = qtt_sum_answers;
			this.qtt_correct_sum_answers = qtt_correct_sum_answers;
			this.qtt_sub_answers = qtt_sub_answers;
			this.qtt_correct_sub_answers = qtt_correct_sub_answers;
			this.qtt_mul_answers = qtt_mul_answers;
			this.qtt_correct_mul_answers = qtt_correct_mul_answers;
			this.qtt_div_answers = qtt_div_answers;
			this.qtt_correct_div_answers = qtt_correct_div_answers;
		}catch(Exception e) {
			System.err.println("ERROR: "+ e.getMessage());
		}
	}
	public Person(int school_id, int id, String first_name, String surname, String email, String login, String password) {
		this.id = id;
		this.school_id = school_id;
		this.first_name = first_name;
		this.surname = surname;
		try {
			if (login.length() < 3) throw new Exception("Login is too short.");
			this.login = login;
			this.email = email;
			this.password = password;
			qtt_answers = 0;
			qtt_wrong_answers = 0;
			qtt_sum_answers = 0;
			qtt_correct_sum_answers = 0;
			qtt_sub_answers = 0;
			qtt_correct_sub_answers = 0;
			qtt_mul_answers = 0;
			qtt_correct_mul_answers = 0;
			qtt_div_answers = 0;
			qtt_correct_div_answers = 0;
		}catch(Exception e) {
			System.err.println("ERROR: "+ e.getMessage());
		}
	}
	public Person(int school_id, int id, String first_name, String surname,
			int qtt_answers, int qtt_wrong_answers, int qtt_sum_answers, int qtt_correct_sum_answers,
			int qtt_sub_answers, int qtt_correct_sub_answers, int qtt_mul_answers,
			int qtt_correct_mul_answers, int qtt_div_answers, int qtt_correct_div_answers) {
		this.id = id;
		this.school_id = school_id;
		this.first_name = first_name;
		this.surname = surname;
		this.qtt_answers = qtt_answers;
		this.qtt_wrong_answers = qtt_wrong_answers;
		this.qtt_sum_answers = qtt_sum_answers;
		this.qtt_correct_sum_answers = qtt_correct_sum_answers;
		this.qtt_sub_answers = qtt_sub_answers;
		this.qtt_correct_sub_answers = qtt_correct_sub_answers;
		this.qtt_mul_answers = qtt_mul_answers;
		this.qtt_correct_mul_answers = qtt_correct_mul_answers;
		this.qtt_div_answers = qtt_div_answers;
		this.qtt_correct_div_answers = qtt_correct_div_answers;
	}
	public Person(int school_id, int id, String first_name, String surname) {
		this.id = id;
		this.school_id = school_id;
		this.first_name = first_name;
		this.surname = surname;
	}
	public int getSchool_id() {
		return school_id;
	}

	public void setSchool_id(int school_id) {
		this.school_id = school_id;
	}

	public String getFirst_name() {
		return first_name;
	}

	public void setFirst_name(String first_name) {
		this.first_name = first_name;
	}

	public String getSurname() {
		return surname;
	}

	public void setSurname(String surname) {
		this.surname = surname;
	}

	public String getLogin() {
		return login;
	}

	public void setLogin(String login) {
		this.login = login;
	}
	
	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}
	public static int getMaxId() {
		return ++max_id;
	}
	public int getId() {
		return id;
	}
}