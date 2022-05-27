package dao;

import java.sql.*;

import model.Message;
import model.Person;

public class MessageDAO extends DAO{	
	public int idNextFinder() {
		int id = 1;
		Message msgs[]=null;
		try {
			Statement st = conexao.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE,ResultSet.CONCUR_READ_ONLY);
			ResultSet rs = st.executeQuery("SELECT * FROM message");
			if(rs.next()) {
				rs.last();
				msgs = new Message[rs.getRow()];
				rs.beforeFirst();
				for(int v = 0;rs.next();v++) {
					msgs[v]= new Message(rs.getInt("sender_id"),rs.getInt("addresee_id"),rs.getInt("id"),rs.getString("subject"),
							rs.getString("body"),rs.getDate("date"));
				}
			}
			st.close();
			}catch(SQLException u){
			throw new RuntimeException(u);
		}
		for(int v=0;v<msgs.length;v++) {
			
			for(int s=0;s<msgs.length;s++) {
				if(msgs[s].getId()==id) {
					id++;
				}
				
			}
			
		}
		return id;
	}
	public boolean createMessage(Message msg) {
		boolean status = false;
		try {
			Statement st = conexao.createStatement();
			st.executeUpdate("INSERT INTO message (sender_id, addresee_id, id, subject, body, date) "+ "VALUES ("
			+msg.getSender_id()+", "+msg.getAddresee_id()+", "+msg.getId()+", '"+msg.getSubject()+"', '"+msg.getBody()
			+"', '"+msg.getDateAsStringSQL()+"')");
			st.close();
			status = true;
		}catch(SQLException u) {
			throw new RuntimeException(u);
		}
		return status;
	}
	public Person[] dropdownChoices(int idIgnore) {
		Person pessoas[] = null;
		PersonDAO pdao = new PersonDAO(); 
		Person pessoinha =  pdao.get_by_id(idIgnore);
		try {
			Statement st = conexao.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE,ResultSet.CONCUR_READ_ONLY);
			ResultSet rs = st.executeQuery("Select * FROM person WHERE school_id = "+pessoinha.getSchool_id()+" AND id != "+idIgnore+";");
			if(rs.next()) {
				rs.last();
				pessoas = new Person[rs.getRow()];
				rs.beforeFirst();
				for(int v=0;rs.next();v++) {
					pessoas[v] = new Person(rs.getInt("school_id"),rs.getInt("id"),rs.getString("first_name"),rs.getString("surname"));
				}
			}
			st.close();
		}catch(SQLException u) {
			throw new RuntimeException(u);
		}
		return pessoas;
	}
	public Message[]readMessagesSent(int senderid) {
		Message[] msgs = null;
		try {
			Statement st = conexao.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE,ResultSet.CONCUR_READ_ONLY);
			ResultSet rs = st.executeQuery("SELECT * FROM message WHERE sender_id = "+senderid+";");
			if(rs.next()) {
				rs.last();
				msgs = new Message[rs.getRow()];
				rs.beforeFirst();
				for(int v = 0;rs.next();v++) {
					msgs[v]= new Message(rs.getInt("sender_id"),rs.getInt("addresee_id"),rs.getInt("id"),rs.getString("subject"),
							rs.getString("body"),rs.getDate("date"));
				}
			}
			st.close();
		}catch(SQLException u) {
			throw new RuntimeException(u);
		}
		return msgs;
	}
	public Message[]readMessagesReceived(int addresseid) {
		Message[] msgs = null;
		try {
			Statement st = conexao.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE,ResultSet.CONCUR_READ_ONLY);
			ResultSet rs = st.executeQuery("SELECT * FROM message WHERE addresee_id = "+addresseid+";");
			if(rs.next()) {
				rs.last();
				msgs = new Message[rs.getRow()];
				rs.beforeFirst();
				for(int v = 0;rs.next();v++) {
					msgs[v]= new Message(rs.getInt("sender_id"),rs.getInt("addresee_id"),rs.getInt("id"),rs.getString("subject"),
							rs.getString("body"),rs.getDate("date"));
				}
			}
			st.close();
		}catch(SQLException u) {
			throw new RuntimeException(u);
		}
		return msgs;
	}
	public boolean updateMessage(Message msg) {
		boolean status = false;
		try {
			Statement st = conexao.createStatement();
			st.executeUpdate("UPDATE message SET subject = '"+msg.getSubject()+"', body = '"+msg.getBody()+"'"
			+" WHERE id = "+msg.getId()+";");
			st.close();
			status = true;
		}catch(SQLException u) {
			throw new RuntimeException(u);
		}
		return status;
	}
	public boolean deleteMessage(int id) {
		boolean status = false;
		try {
			Statement st = conexao.createStatement();
			st.executeUpdate("DELETE FROM message WHERE id = "+ id);
			st.close();
			status = true;
		}catch(SQLException u) {
			throw new RuntimeException(u);
		}
		return status;
	}
	public Message readMessage(int id) {
		Message msg = null;
			try {
				Statement st = conexao.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE,ResultSet.CONCUR_READ_ONLY);
				ResultSet rs = st.executeQuery("SELECT * FROM message WHERE id = "+id+";");
				if(rs.next()) {
					msg = new Message(rs.getInt("sender_id"),rs.getInt("addresee_id"),rs.getInt("id"),rs.getString("subject"),
							rs.getString("body"),rs.getDate("date"));
				}
				st.close();
			}catch(SQLException u){
				throw new RuntimeException(u);
			}
		return msg;
	}
}
