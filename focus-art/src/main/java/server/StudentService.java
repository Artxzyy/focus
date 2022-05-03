package server;
import dao.StudentDAO;
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
	ContentDAO contentDAO = new ContentDAO();
	
	public Object add(Request req, Response res) {
		studentDAO.conectar();
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
			Student student = new Student(school_id, professor_id, name, surname, login, password);
			if(studentDAO.add(student)) {
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
	public Object login(Request req, Response res) {
		boolean valid = false;
		try {
			String login = req.queryParams("login");
			//SecureRandom random = new SecureRandom();
			//byte[] salt = new byte[16];
			//random.nextBytes(salt);
			//KeySpec spec = new PBEKeySpec(req.queryParams("password").toCharArray(), salt, 200, 128);
			//String hash = SecretKeyFactory.getInstance("PBKDF2WithHmacSHA1").generateSecret(spec).getEncoded().toString();
			String password = req.queryParams("password"); // tmp
			Person[] students = studentDAO.getStudents();
			Person correct_student = null;
			for(int i = 0; i < students.length; i++) {
				if(login.equals(students[i].getLogin()) && password.equals(students[i].getPassword())) {
					correct_student = students[i];
					valid = true;
					break;
				}
			}
			if (!valid) throw new Exception("Invalid username or password.");
			res.status(200);
			// html code for main page with css
			Content[] cont = contentDAO.getContents();
			String contents = "";
			
			for(Content c : cont) {
				contents +=
						""
						+ "<div class=\"card-ex\">\n" + 
						"      <div class=\"card-title\">\n" + 
						"         <h1 class=\"card-title\">Atividade -" +c.getSubject()+"</h1>\n" + 
						"      </div>\n" + 
						"      <div class=\"card-body\">\n" + 
						"         <strong><p class=\"card-text\"> " +c.getTitle() + "</p></strong>\n" + 
						"            <p class=\"card-text\">Professor ID (tmp): "+ c.getProfessor_id() + "</p>\n" +
						"            <form onsubmit=\"addIdToPath('form-content', 'http://localhost:4567/content/see/')\" method=\"get\" id=\"form-content\">" +
						"            <input type=\"text\" name=\"id\" value=\""+c.getId()+"\" style=\"visibility: hidden\">"
						+ "		     <button type=\"submit\">Ver Atividade</button>\n" + 
						"            </form>"+
						"      </div>\n" + 
						" </div>";
			}

			String body = "<!DOCTYPE html>\n" + 
					"<html lang=\"pt-br\">\n" + 
					"\n" + 
					"<head>\n" + 
					"  <title>FOCUS - Educação mais acessível</title>\n" + 
					"  <meta charset=\"utf-8\">\n" + 
					"  <meta name=\"viewport\" content=\"width=device-width, initial-scale=1, shrink-to-fit=no\">\n" + 
					"  <script src=\"https://kit.fontawesome.com/37e4898af2.js\" crossorigin=\"anonymous\"></script>\n" + 
					"  <link rel=\"stylesheet\" href=\"styles/style-main.css\">\n" + 
					"\n" + 
					"</head>\n" + 
					"\n" + 
					"<body>\n" + 
					"<script type=\"text/javascript\">"+
					"const checkAside = document.querySelector(\"input.none\");\n" + 
					"const openAsideBtn = document.querySelector(\"#openSideBar\");\n" + 
					"const asideButton = document.querySelector(\".nav-top-expand\");\n" + 
					"function toggleAside(){\n" + 
					"    let asideBar = document.querySelector(\"aside.aside-bar\");\n" + 
					"    let asideBarH1 = document.querySelectorAll(\"h1.aside-option\");\n" + 
					"    let mainArticle = document.querySelector(\"article.content\");\n" + 
					"    let isChecked = document.querySelector(\"input.none\").checked;\n" + 
					"    if(!isChecked){\n" + 
					"        asideBar.style.width = \"80px\";\n" + 
					"        mainArticle.style.width = \"calc(100% - 80px - 1rem)\";\n" + 
					"        mainArticle.style.left = \"80px\";\n" + 
					"        asideBarH1.forEach((e) => {\n" + 
					"            e.style.display = \"none\";\n" + 
					"        });\n" + 
					"        document.querySelector(\"input.none\").checked = true;\n" + 
					"    }else{\n" + 
					"        asideBar.style.width = \"200px\";\n" + 
					"        mainArticle.style.width = \"calc(100% - 200px - 1rem)\";\n" + 
					"        mainArticle.style.left = \"200px\";\n" + 
					"        asideBarH1.forEach((e) => {\n" + 
					"            e.style.display = \"inline\";\n" + 
					"        });\n" + 
					"        document.querySelector(\"input.none\").checked = false;\n" + 
					"    }    \n" + 
					"}\n" + 
					"asideButton.addEventListener(\"click\", toggleAside);"+
					""
					+ "function addIdToPath(form_name, base_url){\n" + 
					"		    var your_form = document.getElementById(form_name);\n" + 
					"			var id = your_form.elements.namedItem(\"id\").value;\n" + 
					"			action_src = base_url + id;\n" + 
					"			your_form.action = action_src;\n" + 
					"		}"
					+ ""+
					"</script>"+
					"  <header>\n" + 
					"    <nav class=\"nav-top\">\n" + 
					"      <div>\n" + 
					"        <div class=\"nav-top-expand\">\n" + 
					"          <button id=\"openSideBar\"><i class=\"fa-solid fa-bars\"></i></button>\n" + 
					"          <input type=\"checkbox\" class=\"none\">\n" + 
					"        </div>\n" + 
					"        <h1 class=\"nav-top-logo\">FOCUS</h1>\n" + 
					"      </div>\n" + 
					"      <div>\n" + 
					"        <input type=\"text\" class=\"nav-top-input\" placeholder=\"Search for a keyword\" id=\"searchInputId\">\n" + 
					"        <a href=\"index.html\"><i class=\"fa-solid fa-arrow-right-from-bracket icon-l\"></i></a>\n" + 
					"      </div>\n" + 
					"    </nav>\n" + 
					"  </header>\n" + 
					"\n" + 
					"  <main class=\"main\">\n" + 
					"      <aside class=\"aside-bar\">\n" + 
					"            <a href=\"main.html\"><div><i class=\"fa-solid fa-stopwatch icon\"></i><h1 class=\"aside-option\">Pendências</h1></div></a>\n" + 
					"            <a href=\"conteudo.html\"><div><i class=\"fa-solid fa-file-alt icon\"></i><h1 class=\"aside-option\">Conteúdos</h1></div></a>\n" + 
					"            <a href=\"atividade.html\"><div><i class=\"fa-solid fa-pencil-alt icon\"></i><h1 class=\"aside-option\">Atividades</h1></div></a>\n" + 
					"            <a href=\"mensagem.html\"><div><i class=\"fa-solid fa-envelope icon\"></i><h1 class=\"aside-option\">Mensagens</h1></div></a>\n" + 
					"      </aside>\n" + 
					"      <article id=\"tela\" class=\"content\">"
					+ contents
					+ "</article>\n" + 
					"  </main>\n" + 
					"  <script src=\"js/scriptsMain.js\"></script>\n" + 
					"</body>\n" + 
					"</html>";
			res.body(body);
			
		}catch(Exception e) {
			res.status(400);
			res.body("ERROR: ss.login:  "+ res.status() + " | "+ e.getMessage());
		}
		return res.body();
	}
}
