package model;

public class Person {
	public static int count = 20;
	public static int max_id = 21;
	private int school_id; // fk
	private int id; // pk
	private String first_name;
	private String surname;
	private String email;
	private String login; // email
	private String password;
	
	public Person() {
		this(0, "", "", "", "", "");
	}
	
	public Person(int school_id, String first_name, String surname, String email, String login, String password) {
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
			// this.password = MessageDigest.getInstance("MD5").digest(password.getBytes("UTF-8")).toString();
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
			// this.password = MessageDigest.getInstance("MD5").digest(password.getBytes("UTF-8")).toString();
		}catch(Exception e) {
			System.err.println("ERROR: "+ e.getMessage());
		}
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