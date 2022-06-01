package server;
import dao.ProfessorDAO;
import model.Person;
import model.Professor;
import dao.ContentDAO;
import model.Student;
import model.Content;

import spark.Request;
import spark.Response;
public class ProfessorService {
	ProfessorDAO professorDAO = new ProfessorDAO();
	ContentDAO contentDAO = new ContentDAO();
	
	public Object add(Request req, Response res) {
		professorDAO.conectar();
		int school_id = Integer.parseInt(req.queryParams("school_id"));
		int professor_id = Integer.parseInt(req.queryParams("professor_id"));
		String name = req.queryParams("name");
		String surname = req.queryParams("surname");
		String email = req.queryParams("email");
		String login = req.queryParams("login");
		String password = req.queryParams("password");
		try {
			if(password.length() < 8) throw new Exception("Password is too short.");
			Person professor = new Person(school_id, professor_id, name, surname, email, login, password);
			if(professorDAO.add(professor)) {
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
		professorDAO.close();
		return res.body();
	}
}