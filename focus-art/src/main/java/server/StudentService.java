package server;
import dao.StudentDAO;
import dao.ProfessorDAO;
import model.Person;
import dao.ContentDAO;
import model.Student;
import model.Content;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.security.spec.KeySpec;

import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.PBEKeySpec;

import spark.Request;
import spark.Response;
public class StudentService {
	StudentDAO studentDAO = new StudentDAO();
	ProfessorDAO professorDAO = new ProfessorDAO();
	ContentDAO contentDAO = new ContentDAO();
	
	public Object add(Request req, Response res) {
		studentDAO.conectar();
		int school_id = Integer.parseInt(req.queryParams("school_id"));
		int professor_id = Integer.parseInt(req.queryParams("prof_id"));
		String name = req.queryParams("name");
		String surname = req.queryParams("surname");
		String email = req.queryParams("email");
		String login = req.queryParams("username");
		String password1 = req.queryParams("p");
		String repeat_p = req.queryParams("repeat_p");
		try {
			if(!password1.equals(repeat_p)) throw new Exception("Both passwords need to be equal.");
			if(password1.length() < 8) throw new Exception("Password is too short.");
			Person student = new Person(school_id, name, surname, email, login, StudentDAO.encryptPassword(password1), 0, 0 ,0, 0, 0, 0, 0, 0, 0, 0);
			if(studentDAO.add(student, professor_id)) {
				res.status(201);
				res.body("Account successfully created. STATUS: "+ res.status() + ".");
			}
			else {
				res.status(502);
				res.body("Our fault. We could not create the account. | STATUS: "+ res.status() +".");
			}
		}catch(Exception e) {
			res.status(400);
			res.body("Could not create account. "+ e.getMessage() + " | STATUS: "+ res.status() +".");
		}
		studentDAO.close();
		return res.body();
	}
	public Object update(Request req, Response res) {
		String email = req.queryParams("email");
		String new_username = req.queryParams("new_username");
		String p = req.queryParams("new_p1");
		int id = studentDAO.get_by_email(email);
		if(id < 0) {
			res.status(500);
			res.body("Não foi possível encontrar esse e-mail em nosso banco de dados.");
		}else {
			Person tmp = studentDAO.get_by_id(id);
			Person updated = new Person(tmp.getSchool_id(), tmp.getId(), tmp.getFirst_name(), tmp.getSurname(), tmp.getEmail(), new_username, StudentDAO.encryptPassword(p));
			boolean result = studentDAO.update(updated, id);
			if(!result) {
				res.body("Não foi possível atualizar seus dados. Tente novamente mais tarde.");
				res.status(502);
			}else {
				res.body("Dados atualizados com sucesso!");
				res.status(200);
			}	
		}
		return res.body();
	}
}
