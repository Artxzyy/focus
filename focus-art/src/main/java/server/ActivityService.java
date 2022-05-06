package server;

import dao.ActivityDAO;
import dao.PersonDAO;
import model.Activity;
import model.Content;
import model.Person;
import model.Option;
import spark.Request;
import spark.Response;
import dao.StudentDAO;
public class ActivityService {
	public static ActivityDAO activityDAO = new ActivityDAO();
	public static StudentDAO studentDAO = new StudentDAO();
	public static PersonDAO personDAO = new PersonDAO();
	public Object see_all(Request req, Response res) {
		int id = Integer.parseInt(req.params(":id"));
		Activity[] activities = activityDAO.get_student_activities(id);
		String contents = "";
		String body = "";
		if(personDAO.is_professor(id)) {
			contents += "<form id=\"form-create\" onsubmit=\"addIdToPath('form-create', 'http://localhost:4567/activity/create/')\" method=\"get\">"+
						"<input type=\"text\" name=\"id\" value=\""+id+"\" style=\"display: none\">"
					+ "<button type=\"submit\" class=\"btn btn-secondary\">Criar nova atividade</button></form><br>"+
					"<form id=\"form-update\" onsubmit=\"addIdToPath('form-update', 'http://localhost:4567/activity/update/')\" method=\"get\">";
		}
		if(activities == null) {
			body += ""
					+ "<!DOCTYPE html>\n" + 
					"<html lang=\"pt-br\">\n" + 
					"\n" + 
					"<head>\n" + 
					"  <title>FOCUS - Educação mais acessível</title>\n" + 
					"  <meta charset=\"utf-8\">\n" + 
					"  <meta name=\"viewport\" content=\"width=device-width, initial-scale=1, shrink-to-fit=no\">\n" + 
					"  <script src=\"https://kit.fontawesome.com/37e4898af2.js\" crossorigin=\"anonymous\"></script>\n" + 
					"  <link rel=\"stylesheet\" href=\"/styles/style-main.css\">\n" + 
					"\n" + 
					"</head>\n" + 
					"\n" + 
					"<body>\n"
					+ "<script type=\"text/javascript\">"
					+ "function addIdToPath(form_name, base_url){\n" + 
					" var your_form = document.getElementById(form_name);\n" + 
					" var id = your_form.elements.namedItem(\"id\").value;\n" + 
					" action_src = base_url + id;\n"+ 
					" your_form.action = action_src;\n" + 
					" }"
					+ "</script>" + 
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
					"        <a href=\"/main\"><div><i class=\"fa-solid fa-stopwatch icon\"></i><h1 class=\"aside-option\">Pendências</h1></div></a>\n" + 
					"        <a href=\"/content\"><div><i class=\"fa-solid fa-file-alt icon\"></i><h1 class=\"aside-option\">Conteúdos</h1></div></a>\n" + 
					"        <a href=\"/activity\"><div><i class=\"fa-solid fa-pencil-alt icon\"></i><h1 class=\"aside-option\">Atividades</h1></div></a>\n" + 
					"        <a href=\"/message\"><div><i class=\"fa-solid fa-envelope icon\"></i><h1 class=\"aside-option\">Mensagens</h1></div></a>\n" + 
					"      </aside>\n" + 
					"      <article id=\"tela\" class=\"content\">\n" + 
					"        <div id=\"aparecerAtividadeDiv\" class=\"content center\">\n" + 
					"<h1>There's no activities to be displayed.<h1>"+			
					contents + 
					"        </div>\n" + 
					"      </article>\n" + 
					"  </main>\n" + 
					"  <script src=\"js/scriptsAtividade.js\"></script>\n" + 
					"</body>\n" + 
					"\n" + 
					"</html>";
		}else {
			Person correct_user = studentDAO.get_by_id(id);
			for(Activity a : activities) {
				contents +=
						""
						+ "<div class=\"card-ex\">\n" + 
						"      <div class=\"card-title\">\n" + 
						"         <h1 class=\"card-title\">Atividade - " +a.getSubject()+"</h1>\n" + 
						"      </div>\n" + 
						"      <div class=\"card-body\">\n" + 
						"         <strong><p class=\"card-text\"> " +a.getTitle() + "</p></strong>\n" + 
						"            <form id=\"form-activity"+a.getId()+"\" onsubmit=\"addIdToPath('form-activity"+a.getId() +"', 'http://localhost:4567/activity/see/')\" method=\"get\">" +
						"            <input type=\"text\" name=\"id\" value=\""+a.getId()+"\" style=\"display: none\">"
								+ "<input type=\"text\" name=\"person_id\" value=\""+correct_user.getId()+"\" style=\"display: none\">"
						+ "		     <button type=\"submit\">Ver atividade</button>\n" + 
						"            </form>"+
						"      </div>\n" + 
						" </div>";
			}
			body += ""
					+ "<!DOCTYPE html>\n" + 
					"<html lang=\"pt-br\">\n" + 
					"\n" + 
					"<head>\n" + 
					"  <title>FOCUS - Educação mais acessível</title>\n" + 
					"  <meta charset=\"utf-8\">\n" + 
					"  <meta name=\"viewport\" content=\"width=device-width, initial-scale=1, shrink-to-fit=no\">\n" + 
					"  <script src=\"https://kit.fontawesome.com/37e4898af2.js\" crossorigin=\"anonymous\"></script>\n" + 
					"  <link rel=\"stylesheet\" href=\"/styles/style-main.css\">\n" + 
					"\n" + 
					"</head>\n" + 
					"\n" + 
					"<body>\n"
					+ "<script type=\"text/javascript\">"
					+ "function addIdToPath(form_name, base_url){\n" + 
					" var your_form = document.getElementById(form_name);\n" + 
					" var id = your_form.elements.namedItem(\"id\").value;\n" + 
					" action_src = base_url + id;\n"+ 
					" your_form.action = action_src;\n" + 
					" }"
					+ "</script>" + 
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
					"        <a href=\"/main\"><div><i class=\"fa-solid fa-stopwatch icon\"></i><h1 class=\"aside-option\">Pendências</h1></div></a>\n" + 
					"        <a href=\"/content\"><div><i class=\"fa-solid fa-file-alt icon\"></i><h1 class=\"aside-option\">Conteúdos</h1></div></a>\n" + 
					"        <a href=\"/activity\"><div><i class=\"fa-solid fa-pencil-alt icon\"></i><h1 class=\"aside-option\">Atividades</h1></div></a>\n" + 
					"        <a href=\"/message\"><div><i class=\"fa-solid fa-envelope icon\"></i><h1 class=\"aside-option\">Mensagens</h1></div></a>\n" + 
					"      </aside>\n" + 
					"      <article id=\"tela\" class=\"content\">\n" + 
					"        <div id=\"aparecerAtividadeDiv\" class=\"content center\">\n" + 
								contents + 
					"        </div>\n" + 
					"      </article>\n" + 
					"  </main>\n" + 
					"  <script src=\"js/scriptsAtividade.js\"></script>\n" + 
					"</body>\n" + 
					"\n" + 
					"</html>";
		}
		res.body(body);
		return res.body();
	}
	public Object see(Request req, Response res) {
		int act_id = Integer.parseInt(req.params(":id"));
		Activity activity = activityDAO.get_by_id(act_id);
		Option[] options = activityDAO.get_options_by_activity(activity);
		String contents = "";
		contents += ""+
				"      <div id=\"disciplinaMostrarAtividade\" class=\"div-title\"><h1 class=\"ex-title\">"+ activity.getSubject() +"</h1></div>\n" + 
				"      <div id=\"tituloMostrarAtividade\" class=\"center\"><h1 class=\"subtitulo\">"+ activity.getTitle() +"</h1></div>\n" + 
				"      <div id=\"enunciadoMostrarAtividade\" class=\"justify enunciado\">"+ activity.getStatement() +"</div>\n" + 
				"      <div id=\"opcao1MostrarAtividade\" class=\"justify ex-option\"><span class=\"negrito\">A</span>   <div class=\"opcao\" id=\"respostaOpcao1\">"+options[0].getOption_text()+"</div></div>\n" + 
				"      <div id=\"opcao2MostrarAtividade\" class=\"justify ex-option\"><span class=\"negrito\">B</span>   <div class=\"opcao\" id=\"respostaOpcao2\">"+options[1].getOption_text()+"</div></div>\n" + 
				"      <div id=\"opcao3MostrarAtividade\" class=\"justify ex-option\"<span class=\"negrito\">C</span>   <div class=\"opcao\" id=\"respostaOpcao3\">"+options[2].getOption_text()+"</div></div>\n" + 
				"      <div id=\"opcao4MostrarAtividade\" class=\"justify ex-option\"><span class=\"negrito\">D</span>   <div class=\"opcao\" id=\"respostaOpcao4\">"+options[3].getOption_text()+"</div></div>\n" + 
				"      <span id=\"validaRespostaSpan\" class=\"negrito center\"></span>";
		String body = "";
		body += ""
				+ "<!DOCTYPE html>\n" + 
				"<html lang=\"pt-br\">\n" + 
				"\n" + 
				"<head>\n" + 
				"  <title>FOCUS - Educação mais acessível</title>\n" + 
				"  <meta charset=\"utf-8\">\n" + 
				"  <meta name=\"viewport\" content=\"width=device-width, initial-scale=1, shrink-to-fit=no\">\n" + 
				"  <script src=\"https://kit.fontawesome.com/37e4898af2.js\" crossorigin=\"anonymous\"></script>\n" + 
				"  <link rel=\"stylesheet\" href=\"/styles/style-main.css\">\n" + 
				"\n" + 
				"</head>\n" + 
				"\n" + 
				"<body>\n" + 
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
				"        <a href=\"/main\"><div><i class=\"fa-solid fa-stopwatch icon\"></i><h1 class=\"aside-option\">Pendências</h1></div></a>\n" + 
				"        <a href=\"/content\"><div><i class=\"fa-solid fa-file-alt icon\"></i><h1 class=\"aside-option\">Conteúdos</h1></div></a>\n" + 
				"        <a href=\"/activity\"><div><i class=\"fa-solid fa-pencil-alt icon\"></i><h1 class=\"aside-option\">Atividades</h1></div></a>\n" + 
				"        <a href=\"/message\"><div><i class=\"fa-solid fa-envelope icon\"></i><h1 class=\"aside-option\">Mensagens</h1></div></a>\n" + 
				"      </aside>\n" + 
				"        <center><div class=\"act center\">\n" + 
							contents + 
				"        </div></center>\n" +  
				"  </main>\n" + 
				"  <script src=\"js/scriptsAtividade.js\"></script>\n" + 
				"</body>\n" + 
				"\n" + 
				"</html>";
		res.body(body);
		return res.body();
	}
	public Object create(Request req, Response res) {
		int id = Integer.parseInt(req.params(":id"));
		if(personDAO.is_professor(id)) {
			String contents = "";
			contents +=
					"<form action=\"http://localhost:4567/create\" method=\"post\"" +
					"<div id=\"mostrarCriarAtividade\">\n" + 
							"<input type=\"text\" value=\""+ id +"\" name=\"id\" style=\"display:none\">" +
					"      <div>\n" + 
					"        <label for=\"disciplinaQuestao\">Disciplina:</label>\n" + 
					"        <input type=\"text\" name=\"subject\" class=\"form-control input\">\n" + 
					"      </div>\n" + 
					"      <div>\n" + 
					"        <label for=\"materiaQuestao\">Matéria:</label>\n" + 
					"        <input type=\"text\" name=\"theme\" class=\"form-control input\">\n" + 
					"      </div>\n" + 
					"      <div>\n" + 
					"        <label for=\"enunciadoQuestao\">Enunciado:</label>\n" + 
					"        <textarea name=\"statement\" cols=\"70\" rows=\"15\" class=\"form-control input\"></textarea>\n" + 
					"      </div>\n" + 
					"      <div>\n" + 
					"        <label for=\"tituloQuestao\">Título:</label>\n" + 
					"        <input type=\"text\" name=\"title\" class=\"form-control input\">\n" + 
					"      </div>\n" + 
					"      \n" + 
					"      <h3>Marque a opção que for a resposta correta.</h3>\n" + 
					"      <div class=\"input-group\">\n" + 
					"        <label for=\"respostaOpcaoA\">A: </label>\n" + 
					"        <div class=\"input-group-prepend\">\n" + 
					"          <div class=\"input-group-text\">\n" + 
					"            <input type=\"radio\" aria-label=\"Radio button for following text input\" name=\"option_radio1\" id=\"respostaOpcaoA\">\n" + 
					"          </div>\n" + 
					"        </div>\n" + 
					"        <input type=\"text\" class=\"form-control\" aria-label=\"Text input with radio button\" name=\"option_input1\" id=\"respostaOpcaoInputA\">\n" + 
					"      </div>\n" + 
					"      \n" + 
					"      <div class=\"input-group\">\n" + 
					"        <label for=\"respostaOpcaoB\">B: </label>\n" + 
					"        <div class=\"input-group-prepend\">\n" + 
					"          <div class=\"input-group-text\">\n" + 
					"            <input type=\"radio\" aria-label=\"Radio button for following text input\" name=\"option_radio2\" id=\"respostaOpcaoB\" >\n" + 
					"          </div>\n" + 
					"        </div>\n" + 
					"        <input type=\"text\" class=\"form-control\" aria-label=\"Text input with radio button\" name=\"option_input2\" id=\"respostaOpcaoInputB\">\n" + 
					"      </div>\n" + 
					"      \n" + 
					"      <div class=\"input-group\">\n" + 
					"        <label for=\"respostaOpcaoC\">C: </label>\n" + 
					"        <div class=\"input-group-prepend\">\n" + 
					"          <div class=\"input-group-text\">\n" + 
					"            <input type=\"radio\" aria-label=\"Radio button for following text input\" name=\"option_radio3\" id=\"respostaOpcaoC\">\n" + 
					"          </div>\n" + 
					"        </div>\n" + 
					"        <input type=\"text\" class=\"form-control\" aria-label=\"Text input with radio button\" name=\"option_input3\" id=\"respostaOpcaoInputC\">\n" + 
					"      </div>\n" + 
					"      \n" + 
					"      <div class=\"input-group\">\n" + 
					"        <label for=\"respostaOpcaoD\">D: </label>\n" + 
					"        <div class=\"input-group-prepend\">\n" + 
					"          <div class=\"input-group-text\">\n" + 
					"            <input type=\"radio\" aria-label=\"Radio button for following text input\" name=\"option_radio4\" id=\"respostaOpcaoD\">\n" + 
					"          </div>\n" + 
					"        </div>\n" + 
					"        <input type=\"text\" class=\"form-control\" aria-label=\"Text input with radio button\" name=\"option_input4\" id=\"respostaOpcaoInputD\">\n" + 
					"      </div>\n" + 
					"      <button type=\"submit\" id=\"btnPostarAtividade\" class=\"btn btn-success\">Publicar</button>\n" + 
					"    </div>";
			String body = "";
			body += ""
					+ "<!DOCTYPE html>\n" + 
					"<html lang=\"pt-br\">\n" + 
					"\n" + 
					"<head>\n" + 
					"  <title>FOCUS - Educação mais acessível</title>\n" + 
					"  <meta charset=\"utf-8\">\n" + 
					"  <meta name=\"viewport\" content=\"width=device-width, initial-scale=1, shrink-to-fit=no\">\n" + 
					"  <script src=\"https://kit.fontawesome.com/37e4898af2.js\" crossorigin=\"anonymous\"></script>\n" + 
					"  <link rel=\"stylesheet\" href=\"/styles/style-main.css\">\n" + 
					"\n" + 
					"</head>\n" + 
					"\n" + 
					"<body>\n"
					+ "<script type=\"text/javascript\">"
					+ "function addIdToPath(form_name, base_url){\n" + 
					" var your_form = document.getElementById(form_name);\n" + 
					" var id = your_form.elements.namedItem(\"id\").value;\n" + 
					" action_src = base_url + id;\n"+ 
					" your_form.action = action_src;\n" + 
					" }"
					+ "</script>" + 
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
					"        <a href=\"/\"><i class=\"fa-solid fa-arrow-right-from-bracket icon-l\"></i></a>\n" + 
					"      </div>\n" + 
					"    </nav>\n" + 
					"  </header>\n" + 
					"\n" + 
					"  <main class=\"main\">\n" + 
					"      <aside class=\"aside-bar\">\n" + 
					"        <a href=\"/main\"><div><i class=\"fa-solid fa-stopwatch icon\"></i><h1 class=\"aside-option\">Pendências</h1></div></a>\n" + 
					"        <a href=\"/content\"><div><i class=\"fa-solid fa-file-alt icon\"></i><h1 class=\"aside-option\">Conteúdos</h1></div></a>\n" + 
					"        <a href=\"/activity\"><div><i class=\"fa-solid fa-pencil-alt icon\"></i><h1 class=\"aside-option\">Atividades</h1></div></a>\n" + 
					"        <a href=\"/message\"><div><i class=\"fa-solid fa-envelope icon\"></i><h1 class=\"aside-option\">Mensagens</h1></div></a>\n" + 
					"      </aside>\n" + 
					"      <article id=\"tela\" class=\"content\">\n" + 
					"        <div id=\"aparecerAtividadeDiv\" class=\"content center\">\n" + 
								contents + 
					"        </div>\n" + 
					"      </article>\n" + 
					"  </main>\n" + 
					"  <script src=\"js/scriptsAtividade.js\"></script>\n" + 
					"</body>\n" + 
					"\n" + 
					"</html>";
			res.body(body);
		}
		return res.body();
	}
	// yet to do
	public Object change(Request req, Response res) {
		int id = Integer.parseInt(req.queryParams("id"));
		String title = req.queryParams("title");
		String subject = req.queryParams("subject");
		String theme = req.queryParams("theme");
		String statement = req.queryParams("statement");
		return res.body(); // 404
	}
	public Object add(Request req, Response res) {
		int id = Integer.parseInt(req.queryParams("id"));
		String title = req.queryParams("title");
		String subject = req.queryParams("subject");
		String theme = req.queryParams("theme");
		String statement = req.queryParams("statement");
		String or1 = req.queryParams("option_radio1");
		String or2 = req.queryParams("option_radio2");
		String or3 = req.queryParams("option_radio3");
		String or4 = req.queryParams("option_radio4");
		String oi1 = req.queryParams("option_input1");
		String oi2 = req.queryParams("option_input2");
		String oi3 = req.queryParams("option_input3");
		String oi4 = req.queryParams("option_input4");
		if(personDAO.is_professor(id)) {
			Activity a = new Activity(id, title, subject, theme, statement);
			Option[] options  = new Option[4];
			if(or1 != null) options[0] = new Option(a.getId(), oi1, true);
			else options[0] = new Option(a.getId(), oi1, false);
			if(or2 != null) options[1] = new Option(a.getId(), oi2, true);
			else options[1] = new Option(a.getId(), oi2, false);
			if(or3 != null) options[2] = new Option(a.getId(), oi3, true);
			else options[2] = new Option(a.getId(), oi3, false);
			if(or4 != null) options[3] = new Option(a.getId(), oi4, true);
			else options[3] = new Option(a.getId(), oi4, false);
			res.status(201);
			res.body("Success! Status "+ res.status() + ".");

		}else {
			res.body("You're not allowed here!");
		}
		return res.body();
	}
	public Object update(Request req, Response res) {
		int id = Integer.parseInt(req.queryParams("id"));
		int activity_id = Integer.parseInt(req.queryParams("activity_id"));
		String title = req.queryParams("title");
		String subject = req.queryParams("subject");
		String theme = req.queryParams("theme");
		String statement = req.queryParams("statement");
		Activity new_activity = new Activity(id, activity_id, title, subject, theme, statement);
		if(activityDAO.update(new_activity, activity_id)) res.body("Success. '"+title+"' was updated!");
		else res.body("Something went wrong.");
		return res.body();
	}
}
