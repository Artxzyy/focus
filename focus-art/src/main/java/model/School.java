package model;

public class School {
	public static int max_id = 1;
	private int id;
	private String name;
	private int max_students;
	private String address;
	
	public int getId() {
		return id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public int getMax_students() {
		return max_students;
	}
	public void setMax_students(int max_students) {
		this.max_students = max_students;
	}
	public String getAddress() {
		return address;
	}
	public void setAddress(String address) {
		this.address = address;
	}
	
	public School() {
		id = max_id++;
		name = "";
		max_students = 5;
		address = "";
	}
	public School(String name, int max_students, String address) {
		this.id = max_id++;
		this.name = name;
		this.max_students = max_students >= 5 ? max_students : 5;
		this.address = address;
	}
}
