package server;
import dao.StudentDAO;
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
			Student[] students = studentDAO.getStudents();
			Student correct_student = new Student();
			for(int i = 0; i < students.length; i++) {
				if(login.equals(students[i].getLogin()) && password.equals(students[i].getPassword())) {
					correct_student = students[i].clone();
					valid = true;
				}
			}
			if(!valid) throw new Exception("Username or password incorrect.");
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
					"<style>"
					+ "\n" + 
					"/*     TAGS GERAIS INICIO     */\n" + 
					"html, body{\n" + 
					"    margin: 0;\n" + 
					"    padding: 0;\n" + 
					"    font-family: sans-serif;\n" + 
					"    text-align: justify;\n" + 
					"    box-sizing: border-box;\n" + 
					"}\n" + 
					":root{\n" + 
					"    --wrong-answer-red: #f00;\n" + 
					"    \n" + 
					"    --dark-red: #7f1d1d;\n" + 
					"    --medium-dark-red: #af1d1d;\n" + 
					"    --medium-red: #fa9d9d;\n" + 
					"    --light-red: #fa9a9a;\n" + 
					"    --lightest-red: #fee2e2;\n" + 
					"\n" + 
					"    --dark-gray: #27272a;\n" + 
					"    --medium-gray: #a8a8a8;\n" + 
					"    --medium-m-gray: #cacaca;\n" + 
					"    --light-gray: #fff7f7;\n" + 
					"\n" + 
					"    --dark-blue: #0f3d5c;\n" + 
					"    --medium-blue: #2563eb;\n" + 
					"    --light-blue: #60a5fa;\n" + 
					"\n" + 
					"    --sky-light: #57beea;\n" + 
					"    --sky-medium: #0284c7;\n" + 
					"\n" + 
					"    --dark-green: #15803d;\n" + 
					"\n" + 
					"    --semi-transparent-white: #ffffffdf;\n" + 
					"    --semi-transparent-red: #e61818df;\n" + 
					"    --transparent-color: #fff0;\n" + 
					"    --input-focus-color: #1aa2d3;\n" + 
					"    --dark-gray-color: #232323;\n" + 
					"    --gray-color: #767676;\n" + 
					"    --gray-hover: #333;\n" + 
					"    --light-gray-hover: #e6e6e6;\n" + 
					"    --extra-light-gray: #f6f6f6;\n" + 
					"    --semi-transparent-extra-light-gray: #f6f6f6df;\n" + 
					"\n" + 
					"    --base-transition: 150ms;\n" + 
					"}\n" + 
					".logo{\n" + 
					"    font-family: cursive;\n" + 
					"    font-size: 1.8em;\n" + 
					"    padding-left: 15px;\n" + 
					"}\n" + 
					"/*     TAGS GERAIS FIM     */\n" + 
					"\n" + 
					"/*     TAGS HEADER INICIO     */\n" + 
					"\n" + 
					".submit-button-reg{\n" + 
					"    border-radius: 999px;\n" + 
					"    background-color: var(--sky-light);\n" + 
					"    padding: 0.5rem 1.5rem;\n" + 
					"    color: white;\n" + 
					"    float: right;\n" + 
					"    cursor: pointer;\n" + 
					"    font-size: 1rem;\n" + 
					"    border: none;\n" + 
					"    margin-right: 4rem;\n" + 
					"}\n" + 
					".submit-button{\n" + 
					"    border-radius: 999px;\n" + 
					"    background-color: var(--sky-light);\n" + 
					"    padding: 0.5rem 1.5rem;\n" + 
					"    color: white;\n" + 
					"    float: right;\n" + 
					"    cursor: pointer;\n" + 
					"    font-size: 1rem;\n" + 
					"    border: none;\n" + 
					"}\n" + 
					".submit-button-non{\n" + 
					"    border-radius: 999px;\n" + 
					"    background-color: var(--sky-light);\n" + 
					"    padding: 0.5rem 1.5rem;\n" + 
					"    color: white;\n" + 
					"    cursor: pointer;\n" + 
					"    font-size: 1rem;\n" + 
					"    border: none;\n" + 
					"    margin-left: 4rem;\n" + 
					"    margin-bottom: 2rem;\n" + 
					"}\n" + 
					".submit-button-reg:disabled{\n" + 
					"    cursor: auto;\n" + 
					"    background-color: var(--medium-gray);\n" + 
					"}\n" + 
					".submit-button:disabled{\n" + 
					"    cursor: auto;\n" + 
					"    background-color: var(--medium-gray);\n" + 
					"}\n" + 
					".submit-button-non:disabled{\n" + 
					"    cursor: auto;\n" + 
					"    background-color: var(--medium-gray);\n" + 
					"}\n" + 
					".submit-button-reg:disabled:hover{\n" + 
					"    background-color: var(--medium-gray);\n" + 
					"}\n" + 
					".submit-button:disabled:hover{\n" + 
					"    background-color: var(--medium-gray);\n" + 
					"}\n" + 
					".submit-button-non:disabled:hover{\n" + 
					"    background-color: var(--medium-gray);\n" + 
					"}\n" + 
					"\n" + 
					".navbar{\n" + 
					"    background-color: var(--light-gray);\n" + 
					"    display: flex;\n" + 
					"    flex-direction: row;\n" + 
					"    justify-content: space-between;\n" + 
					"    justify-items: center;\n" + 
					"    height: 90px;\n" + 
					"}\n" + 
					".navbar-ul{\n" + 
					"    display: flex;\n" + 
					"    flex-direction: row;\n" + 
					"    height: 100%;\n" + 
					"    list-style: none;\n" + 
					"    padding-top: 0.25rem;\n" + 
					"}\n" + 
					".navbar-ul li{\n" + 
					"    max-width: 100px;\n" + 
					"    min-width: 50px;\n" + 
					"    margin: 0 2rem;\n" + 
					"    font-size: 2rem;\n" + 
					"}\n" + 
					"\n" + 
					".navbar-ul a{\n" + 
					"    color: var(--dark-blue);\n" + 
					"}\n" + 
					".navbar img{\n" + 
					"    width: auto;\n" + 
					"    height: 80px;\n" + 
					"}\n" + 
					".text_logout{\n" + 
					"    margin: 0 2rem;\n" + 
					"    font-size: 2rem;\n" + 
					"}\n" + 
					".sidebar{\n" + 
					"    width: 20%;\n" + 
					"    background: #dc3545;\n" + 
					"    height: 100%;\n" + 
					"    position: fixed;\n" + 
					"    top:0;\n" + 
					"    z-index: 2;\n" + 
					"    \n" + 
					"}\n" + 
					".logo_puc{\n" + 
					"    width: 150px;\n" + 
					"}\n" + 
					".sidebar ul:first-child{\n" + 
					"    padding-top: 50%;\n" + 
					"\n" + 
					"}\n" + 
					".sidebar nav ul li a{\n" + 
					"    display: flex;\n" + 
					"    flex-wrap: nowrap;\n" + 
					"    align-items: center;\n" + 
					"    color: #fff;\n" + 
					"}\n" + 
					".sidebar nav ul li{\n" + 
					"    padding-top: 10px;\n" + 
					"    padding-bottom: 10px;\n" + 
					"}\n" + 
					".sidebar nav ul{\n" + 
					"    position: sticky;\n" + 
					"    right: 15px;\n" + 
					"}\n" + 
					".sidebar i{\n" + 
					"    padding-right: 30px;\n" + 
					"    font-size: 1.3em;\n" + 
					"    max-width: 20px;\n" + 
					"}\n" + 
					"/*   ROW MAIN INICIO   */\n" + 
					"\n" + 
					".main{\n" + 
					"    background: #fff;\n" + 
					"    margin: 0;\n" + 
					"    justify-content: space-between;\n" + 
					"}\n" + 
					".conteudo{\n" + 
					"    width: 76%;\n" + 
					"    min-height: 600px;\n" + 
					"}\n" + 
					".main section{\n" + 
					"    position: relative;\n" + 
					"    left: 24%;\n" + 
					"}\n" + 
					".main > div{\n" + 
					"    margin-left: 0rem;\n" + 
					"}\n" + 
					".center div{\n" + 
					"    padding: 4px;\n" + 
					"}\n" + 
					".opcao{\n" + 
					"    cursor: pointer;\n" + 
					"    width: 95%;\n" + 
					"    border-radius: 0.25rem;\n" + 
					"    border: 1px solid var(--gray-color);\n" + 
					"}\n" + 
					".opcao:hover{\n" + 
					"    background-color: var(--light-gray-hover);\n" + 
					"}\n" + 
					".negrito{\n" + 
					"    font-weight: bolder;\n" + 
					"}\n" + 
					".titulo{\n" + 
					"    font-weight: 700;\n" + 
					"    font-size: 2.3em;\n" + 
					"}\n" + 
					".subtitulo{\n" + 
					"    font-size: 1.7em;\n" + 
					"    font-weight: 400;\n" + 
					"\n" + 
					"}\n" + 
					".tela{\n" + 
					"    margin: 10px 0;\n" + 
					"}\n" + 
					"#tela{\n" + 
					"    display: flex;\n" + 
					"    flex-direction: row;\n" + 
					"    flex-wrap: wrap;\n" + 
					"}\n" + 
					".enunciado{\n" + 
					"    font-size: 1.1rem;\n" + 
					"    font-weight: 100;\n" + 
					"}\n" + 
					".justify{\n" + 
					"    text-align: justify;\n" + 
					"}\n" + 
					".select{\n" + 
					"    width: 100%;\n" + 
					"}\n" + 
					"#aparecerAtividadeDiv{\n" + 
					"    width: 100%;\n" + 
					"}\n" + 
					".dropdown-toggle{\n" + 
					"    background-color: var(--medium-m-gray);\n" + 
					"    border: none;\n" + 
					"    border-radius: 0.25rem;\n" + 
					"    padding: .5rem;\n" + 
					"    transition: 150ms;\n" + 
					"    cursor: pointer;\n" + 
					"}\n" + 
					".d-block{\n" + 
					"    position: absolute;\n" + 
					"    top: -10%;\n" + 
					"    left: 10%;\n" + 
					"    width: 100%;\n" + 
					"    display: block;\n" + 
					"}\n" + 
					".dropdown-toggle:hover{\n" + 
					"    background-color: var(--medium-gray);\n" + 
					"}\n" + 
					".divQuestao{\n" + 
					"    width: 95%;\n" + 
					"    text-align: center;\n" + 
					"    margin-left: 10px;\n" + 
					"    margin-right: 10px;\n" + 
					"}\n" + 
					"#btnApagarCriarAtividade{\n" + 
					"    margin: 10px;\n" + 
					"}\n" + 
					"#btnApagarVisualizacaoAtividade{\n" + 
					"    margin: 10px;\n" + 
					"}\n" + 
					"\n" + 
					".inline{\n" + 
					"    display: inline;\n" + 
					"}\n" + 
					".cartao-main{\n" + 
					"    width: 320px;\n" + 
					"    height: 250px;\n" + 
					"    margin: 15px;\n" + 
					"}\n" + 
					".titulo-main{\n" + 
					"    margin-top: 10px;\n" + 
					"    margin-left: 30px;\n" + 
					"}\n" + 
					"/*   ROW MAIN FIM   */\n" + 
					"\n" + 
					"/*  ROW FOOTER INICIO*/\n" + 
					"\n" + 
					".footer {\n" + 
					"    position: inherit;\n" + 
					"    bottom: 0;\n" + 
					"    margin: 0;\n" + 
					"    background: #dc3545;\n" + 
					"    width: 100%;\n" + 
					"    z-index: 2;\n" + 
					"\n" + 
					"}\n" + 
					".footer p{\n" + 
					"    color: #fff;\n" + 
					"}\n" + 
					".input{\n" + 
					"    margin: 0 auto;\n" + 
					"    max-width: 80%;\n" + 
					"}\n" + 
					"label{\n" + 
					"    text-align: left;\n" + 
					"    font-weight: bolder;\n" + 
					"}\n" + 
					"\n" + 
					"/*  ROW FOOTER FIM*/\n" + 
					".none{\n" + 
					"    display: none;\n" + 
					"}\n" + 
					".modal-aside{\n" + 
					"    display: none;\n" + 
					"    position: fixed;\n" + 
					"    left: 0;\n" + 
					"    top: 0;\n" + 
					"    width: 250px;\n" + 
					"    height: 100%;\n" + 
					"    z-index: 1;\n" + 
					"    animation: aside-animation 350ms ease-in-out;\n" + 
					"    overflow: auto;\n" + 
					"    background-color: var(--medium-dark-red);\n" + 
					"}\n" + 
					"\n" + 
					"@keyframes aside-animation{\n" + 
					"    from{left:-500px;}\n" + 
					"    to{left:0;}\n" + 
					"}\n" + 
					"/*     TAGS MAIN FIM     */\n" + 
					"\n" + 
					"@media screen and (max-width: 600px){\n" + 
					"    .aside-option{\n" + 
					"        display: none;\n" + 
					"    }\n" + 
					"    .sidebar{\n" + 
					"        width: 15%;\n" + 
					"        background: #dc3545;\n" + 
					"        height: 100%;\n" + 
					"        position: fixed;\n" + 
					"        top:0;\n" + 
					"        z-index: 2;\n" + 
					"    }\n" + 
					"    #qualAtividade{\n" + 
					"        width: 200px;\n" + 
					"    }\n" + 
					"    .sidebar ul:first-child{\n" + 
					"        padding-top: 90px;\n" + 
					"    \n" + 
					"    }\n" + 
					"    body{\n" + 
					"        font-size: smaller;\n" + 
					"    }\n" + 
					"    .cartao-main{\n" + 
					"        width: 230px;\n" + 
					"        height: 350px;\n" + 
					"        margin: 15px;\n" + 
					"    }\n" + 
					"    button{\n" + 
					"        width: 180px;\n" + 
					"    }\n" + 
					"    #conteudoVer{\n" + 
					"        width: 150px;\n" + 
					"    }\n" + 
					"    select{\n" + 
					"        width: 200px;\n" + 
					"    }\n" + 
					"}\n" + 
					"\n" + 
					"h1{\n" + 
					"    color: var(--dark-gray-color);\n" + 
					"    font-weight: 600;\n" + 
					"    font-size: 1.375rem;\n" + 
					"    line-height: 1.375rem;\n" + 
					"}\n" + 
					"::-webkit-scrollbar{\n" + 
					"    width: 5px;\n" + 
					"    background-color: #ffffff;\n" + 
					"}\n" + 
					"::-webkit-scrollbar-thumb{\n" + 
					"    background-color: #616161;\n" + 
					"    border-radius: 999px;\n" + 
					"}\n" + 
					"::marker{\n" + 
					"    font-size: 1.25rem;\n" + 
					"    color: var(--input-focus-color);\n" + 
					"}\n" + 
					".flex{\n" + 
					"    display: flex;\n" + 
					"}\n" + 
					".inline{\n" + 
					"    display: inline;\n" + 
					"}\n" + 
					"button{\n" + 
					"    cursor: pointer;\n" + 
					"}\n" + 
					".center{\n" + 
					"    align-items: center;\n" + 
					"    text-align: center;\n" + 
					"}\n" + 
					".none{\n" + 
					"    display: none;\n" + 
					"}\n" + 
					".nav-top{\n" + 
					"    z-index: 1;\n" + 
					"    background-color: var(--semi-transparent-red);\n" + 
					"    top: 0;\n" + 
					"    width: 100%;\n" + 
					"    height: 65px;\n" + 
					"    display: flex;\n" + 
					"    position: fixed;\n" + 
					"    flex-direction: row;\n" + 
					"    flex-wrap: wrap;\n" + 
					"    justify-content: space-between;\n" + 
					"    padding: 0.5rem;\n" + 
					"    align-items: center;\n" + 
					"}\n" + 
					".nav-top-expand{\n" + 
					"    display: inline;\n" + 
					"}\n" + 
					".nav-top-expand button{\n" + 
					"    border: 1px solid #151515;\n" + 
					"    width: 37px;\n" + 
					"    height: 37px;\n" + 
					"    border-radius: 0.375rem;\n" + 
					"    background-color: var(--transparent-color);\n" + 
					"    font-size: 1.5rem;\n" + 
					"    margin-left: 1rem;\n" + 
					"    margin-right: 1rem;\n" + 
					"}\n" + 
					".nav-top-logo{\n" + 
					"    display: inline;\n" + 
					"    font-family:Cambria, Cochin, Georgia, Times, 'Times New Roman', serif;\n" + 
					"    text-decoration: underline var(--light-gray);\n" + 
					"    color: #fff;\n" + 
					"    cursor: pointer;\n" + 
					"}\n" + 
					".nav-top-search{\n" + 
					"    margin-right: 2rem;\n" + 
					"}\n" + 
					".nav-top-input{\n" + 
					"    font-size: 0.9rem;\n" + 
					"    padding: 0.75rem 1rem;\n" + 
					"    border-radius: 999px;\n" + 
					"    border: 1px solid #ccc;\n" + 
					"    outline: none;\n" + 
					"    float: left;\n" + 
					"    margin-right: 2rem;\n" + 
					"}\n" + 
					".nav-top-input:focus{\n" + 
					"    border: 1px solid var(--input-focus-color);\n" + 
					"}\n" + 
					".main{\n" + 
					"    display: flex;\n" + 
					"    flex-direction: row;\n" + 
					"    flex-wrap: nowrap;\n" + 
					"    position: relative;\n" + 
					"    top: 80px;\n" + 
					"}\n" + 
					".aside-bar{\n" + 
					"    top: 80px;\n" + 
					"    position: fixed;\n" + 
					"    padding-top: 1rem;\n" + 
					"    height: 100%;\n" + 
					"    width: 200px;\n" + 
					"    background-color: var(--semi-transparent-red);\n" + 
					"}\n" + 
					".aside-bar div{\n" + 
					"    padding: 0.75rem;\n" + 
					"    margin-bottom: 1rem;\n" + 
					"}\n" + 
					".aside-bar div:hover{\n" + 
					"    background-color: var(--extra-light-gray);\n" + 
					"}\n" + 
					".aside-bar div:hover .aside-option{\n" + 
					"    color: var(--dark-gray);\n" + 
					"}\n" + 
					".aside-bar div:hover i{\n" + 
					"    color: var(--dark-gray);\n" + 
					"}\n" + 
					".icon{\n" + 
					"    font-size: 1.875rem;\n" + 
					"    margin-left: 1rem;\n" + 
					"    width: 2.5rem;\n" + 
					"}\n" + 
					".icon-l{\n" + 
					"    font-size: 1.875rem;\n" + 
					"    margin-right: 1rem;\n" + 
					"    margin-top: 0.375rem;\n" + 
					"    width: 2.5rem;\n" + 
					"    position: relative;\n" + 
					"    color: #fff;\n" + 
					"    font-weight: 700;\n" + 
					"}\n" + 
					".content{\n" + 
					"    display: flex;\n" + 
					"    flex-direction: row;\n" + 
					"    flex-wrap: wrap;\n" + 
					"    margin: 1rem;\n" + 
					"    position: relative;\n" + 
					"    left: 200px;\n" + 
					"    width: calc(100% - 200px - 1rem);\n" + 
					"}\n" + 
					".aside-option{\n" + 
					"    display: inline;\n" + 
					"    margin-left: 0.5rem;\n" + 
					"    font-size: 1rem;\n" + 
					"    color: var(--extra-light-gray);\n" + 
					"}\n" + 
					".aside-bar > a{\n" + 
					"    text-decoration: none;\n" + 
					"    color: #fff;\n" + 
					"}\n" + 
					".profile{\n" + 
					"    display: flex;\n" + 
					"    flex-wrap: nowrap;\n" + 
					"    flex-direction: row;\n" + 
					"    justify-content: space-evenly;\n" + 
					"    margin-bottom: 2rem;\n" + 
					"    padding-bottom: 1rem;\n" + 
					"    border-bottom: 2px solid var(--dark-gray-color);\n" + 
					"}\n" + 
					".profile-about{\n" + 
					"    padding: 1rem;\n" + 
					"    margin-left: 1rem;\n" + 
					"}\n" + 
					".main button{\n" + 
					"    border: 1px solid var(--medium-m-gray);\n" + 
					"    padding: 0.375rem 0.875rem;\n" + 
					"    font-size: 0.9rem;\n" + 
					"    font-weight: 600;\n" + 
					"    border-radius: 0.375rem;\n" + 
					"    background-color: var(--extra-light-gray);\n" + 
					"    color: var(--dark-gray);\n" + 
					"}\n" + 
					"\n" + 
					".main button:hover{\n" + 
					"    background-color: var(--gray-hover);\n" + 
					"    transition: var(--base-transition);\n" + 
					"    color: var(--extra-light-gray);\n" + 
					"\n" + 
					"}\n" + 
					".profile-bio{\n" + 
					"    width: 80%;\n" + 
					"}\n" + 
					".how{\n" + 
					"    padding: 1rem;\n" + 
					"    width: 45%;\n" + 
					"    margin-right: 3rem;\n" + 
					"    border-radius: 0.375rem;\n" + 
					"    border: 1px solid var(--dark-gray-color);\n" + 
					"    box-shadow: -1px 4px 5px 0 var(--dark-gray-color);\n" + 
					"}\n" + 
					".projects{\n" + 
					"    display: flex;\n" + 
					"    flex-direction: row;\n" + 
					"    flex-wrap: wrap;\n" + 
					"    gap: 0.5rem;\n" + 
					"    justify-content: space-evenly;\n" + 
					"}\n" + 
					".project{\n" + 
					"\n" + 
					"    width: 30%;\n" + 
					"    margin: 0.25rem;\n" + 
					"    padding: 0.5rem;\n" + 
					"    border: 1px solid var(--gray-color);\n" + 
					"    border-radius: 0.375rem;\n" + 
					"    box-shadow: -1px 2px 4px 0 var(--gray-color);\n" + 
					"    padding-bottom: 2rem;\n" + 
					"}\n" + 
					".project-text{\n" + 
					"    margin-right: 1rem;\n" + 
					"    font-size: 1rem;\n" + 
					"    line-height: 1.1rem;\n" + 
					"}\n" + 
					".project-title{\n" + 
					"    font-size: 1.25rem;\n" + 
					"}\n" + 
					".project button{\n" + 
					"    float: right;\n" + 
					"    margin-right: 1rem;\n" + 
					"}\n" + 
					"\n" + 
					".the{\n" + 
					"    font-size: 2rem;\n" + 
					"}\n" + 
					"\n" + 
					".card-ex{\n" + 
					"    box-shadow: 0 1px 2px 0 rgba(159, 170, 179, 0.3), 0 2px 6px 2px rgb(159, 170, 179, 0.3);\n" + 
					"    display: block;\n" + 
					"    width: 30%;\n" + 
					"    height: 30vh;\n" + 
					"    margin: 0.5rem 0.75rem;\n" + 
					"    transition: 200ms ease-in-out;\n" + 
					"}\n" + 
					".card-ex:hover{\n" + 
					"    box-shadow: 0 1px 2px 0 rgb(60 64 67 / 30%), 0 2px 6px 2px rgb(60 64 67 / 15%);\n" + 
					"    width: 30.5%;\n" + 
					"    height: 30.5vh;\n" + 
					"}\n" + 
					".card-title{\n" + 
					"    line-height:1rem;\n" + 
					"    padding: 0.075rem 0.5rem 0.075rem 0.5rem;\n" + 
					"    font-size:1rem;\n" + 
					"    background-color: var(--medium-m-gray);\n" + 
					"}\n" + 
					".card-body{\n" + 
					"    margin: 0.25rem 0.125rem;\n" + 
					"    padding: 0.25rem 0.125rem;\n" + 
					"}\n" + 
					".card-text{\n" + 
					"    margin: 0.25rem;\n" + 
					"    padding: 0.25rem ;\n" + 
					"}\n" + 
					".block{\n" + 
					"    display: block;\n" + 
					"}\n" + 
					".ex-title{\n" + 
					"    line-height:1rem;\n" + 
					"    padding: 1rem;\n" + 
					"    border-radius: 1rem;\n" + 
					"    font-size:1rem;\n" + 
					"    background-color: var(--medium-m-gray);\n" + 
					"    margin-bottom: 2rem;\n" + 
					"}\n" + 
					".div-title{\n" + 
					"    border-bottom: 2px solid var(--dark-gray-color);\n" + 
					"    border-radius: 1rem;\n" + 
					"}\n" + 
					"#openSideBar{\n" + 
					"    background-color: #fff;\n" + 
					"}\n" + 
					"#openSideBar:hover{\n" + 
					"    transition: 150ms;\n" + 
					"    background-color: var(--extra-light-gray);\n" + 
					"}\n" + 
					"#conteudoPostado{\n" + 
					"    position: absolute;\n" + 
					"    right: 25%;\n" + 
					"    width: 100%;\n" + 
					"}\n" + 
					"#conteudoColocado{\n" + 
					"    position: absolute;\n" + 
					"    right: 25%;\n" + 
					"    width: 100%;\n" + 
					"}\n" + 
					"#tipoMaterial{\n" + 
					"    background-color: #000000;\n" + 
					"}\n" + 
					".div{\n" + 
					"    width: 100%;\n" + 
					"    position: relative;\n" + 
					"    left: 10%;\n" + 
					"    margin-top: 1rem;\n" + 
					"}\n" + 
					".input-text{\n" + 
					"    width: 100%;\n" + 
					"    position: relative;\n" + 
					"    margin: 1rem;\n" + 
					"    border: 1px solid var(--gray-color);\n" + 
					"    padding: 0.5rem 0.75rem;\n" + 
					"    border-radius: 0.375rem;\n" + 
					"}\n" + 
					"</style>" +
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
			res.body("ERROR: "+ res.status() + " | "+ e.getMessage());
		}
		return res.body();
	}
}
