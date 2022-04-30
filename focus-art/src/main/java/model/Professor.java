package model;

// import java.security.MessageDigest;
public class Professor {
	public static int max_id = 1;
	private int school_id; // fk
	private int id; // pk
	private String first_name;
	private String surname;
	private String login; // email
	private String password;
	
	public Professor() {
		this(0, "", "", "", "");
	}
	
	public Professor(int school_id, String first_name, String surname, String login, String password) {
		this.id = max_id++;
		this.school_id = school_id;
		this.first_name = first_name;
		this.surname = surname;
		try {
			if (login.length() < 3) throw new Exception("Login is too short.");
			this.login = login;
			this.password = password;
			// this.password = MessageDigest.getInstance("MD5").digest(password.getBytes("UTF-8")).toString();
		}catch(Exception e) {
			System.err.println("ERROR: "+ e.getMessage());
		}
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

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public int getId() {
		return id;
	}
}
