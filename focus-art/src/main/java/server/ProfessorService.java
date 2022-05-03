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
		String login = req.queryParams("login");
		String password = req.queryParams("password");
		try {
			if(password.length() < 8) throw new Exception("Password is too short.");
			// SecureRandom random = new SecureRandom();
			//byte[] salt = new byte[16];
			//random.nextBytes(salt);
			//KeySpec spec = new PBEKeySpec(req.queryParams("password").toCharArray(), salt, 200, 128);
			//String hash = SecretKeyFactory.getInstance("PBKDF2WithHmacSHA1").generateSecret(spec).getEncoded().toString();
			Person professor = new Person(school_id, professor_id, name, surname, login, password);
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