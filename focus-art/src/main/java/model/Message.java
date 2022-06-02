package model;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

public class Message {
	private static final String ASSUNTO_DEFAULT = "No subject was writen.";
	private int sender_id;
	private int addresee_id;
	private int id;
	private String subject;
	private String body;
	private Date date;
	
	public Message() {
		this.sender_id = -1;
		this.addresee_id = -2;
		this.id = -1;
		this.subject = getAssuntoDefault();
		this.body = "";
		this.date = new Date();
	}
	public Message(int senderid,int addreseeid,int ident,String assunto,String corpo,Date data) {
		this.sender_id = senderid;
		this.addresee_id = addreseeid;
		this.id = ident;
		this.subject = assunto;
		this.body = corpo;
		this.date = data;
	}
	public int getSender_id() {
		return sender_id;
	}
	public void setSender_id(int sender_id) {
		this.sender_id = sender_id;
	}
	public int getAddresee_id() {
		return addresee_id;
	}
	public void setAddresee_id(int addresee_id) {
		this.addresee_id = addresee_id;
	}
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getSubject() {
		return subject;
	}
	public void setSubject(String subject) {
		this.subject = subject;
	}
	public String getBody() {
		return body;
	}
	public void setBody(String body) {
		this.body = body;
	}
	public Date getDate() {
		return date;
	}
	public String getDateAsStringSQL() {
		DateFormat df = new SimpleDateFormat("yyyy/MM/dd");
		return df.format(date);
	}
	public String getDateAsStringDisplay() {
		DateFormat df = new SimpleDateFormat("dd/MM/yyyy");
		return df.format(date);
	}
	public void setDate(Date date) {
		this.date = date;
	}
	@Override
	public String toString() {
		DateFormat df = new SimpleDateFormat("dd/MM/yyyy");
		return "Mensagens [senderid = "+sender_id+", addresseid = "+addresee_id+", id = "+id + ", subject = " + subject + ", body = "+ body + ", date = "+df.format(date);
	}
	public static String getAssuntoDefault() {
		return ASSUNTO_DEFAULT;
	}
}
