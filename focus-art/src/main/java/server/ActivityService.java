package server;

import java.util.MissingFormatWidthException;

import dao.ActivityDAO;
import dao.PersonDAO;
import model.Activity;
import model.Person;
import model.Option;
import spark.Request;
import spark.Response;
import dao.StudentDAO;
public class ActivityService {
	public static ActivityDAO activityDAO = new ActivityDAO();
	public static StudentDAO studentDAO = new StudentDAO();
	public static PersonDAO personDAO = new PersonDAO();
	public static final int WRONG = 0;
	public static final int RIGHT = 1;
	public Object see_all(Request req, Response res) throws MissingFormatWidthException{
		int id = Integer.parseInt(req.params(":id"));
		Activity[] activities = activityDAO.get_student_activities(id);
		if(activities == null) {
			activities = activityDAO.get_professor_activities(id);
		}
		String contents = "";
		String body = "";
		if(personDAO.is_professor(id)) {
			contents += "<div style=\"display:block\"><form id=\"form-create\" onsubmit=\"addIdToPath('form-create', 'http://localhost:4567/activity/create/')\" method=\"get\">"+
						"<input type=\"text\" name=\"id\" value=\""+id+"\" style=\"display: none\">"
					+ "<button type=\"submit\" class=\"btn btn-secondary\">Criar nova atividade</button></form><br>"+
					"</div>";
		}
		Person user = personDAO.get_by_id(id);
		int total = (user.getQtt_correct_sum_answers()+user.getQtt_correct_sub_answers()+
				user.getQtt_correct_mul_answers()+user.getQtt_correct_div_answers());
		String diagnostic =
				"<div style=\"position: relative; left: 15%;\">\n" + 
				"    <h1>Diagnóstico geral</h1>\n" + 
				"\n" + 
				"    <table id=\"diagnostic\">\n" + 
				"      <tr>\n" + 
				"        <th>Áreas</th>\n" + 
				"        <th>Total de acertos</th>\n" + 
				"        <th>Total de erros</th>\n" + 
				"        <th>Percentual de acertos</th>\n" + 
				"      </tr>\n" + 
				"      <tr>\n" + 
				"        <td style=\"font-weight:bolder\">Geral</td>\n" + 
				"        <td>"+ (total) +"</td>\n" + 
				"        <td>"+ (user.getQtt_answers()-total) +"</td>\n" + 
				"        <td>"+ String.format("%.2f",((float)total/user.getQtt_answers())*100f) +"%</td>\n" + 
				"      </tr>\n" + 
				"      <tr>\n" + 
				"        <td style=\"font-weight:bolder\">Soma</td>\n" + 
				"        <td>"+ (user.getQtt_correct_sum_answers()) +"</td>\n" + 
				"        <td>"+ (user.getQtt_sum_answers()-user.getQtt_correct_sum_answers()) +"</td>\n" + 
				"        <td>"+ String.format("%.2f",((float)user.getQtt_correct_sum_answers()/user.getQtt_sum_answers())*100f) +"%</td>\n" + 
				"      </tr>\n" + 
				"      <tr>\n" + 
				"        <td style=\"font-weight:bolder\">Subtração</td>\n" + 
				"        <td>"+ (user.getQtt_correct_sub_answers()) +"</td>\n" + 
				"        <td>"+ (user.getQtt_sub_answers()-user.getQtt_correct_sub_answers()) +"</td>\n" + 
				"        <td>"+ String.format("%.2f",((float)user.getQtt_correct_sub_answers()/user.getQtt_sub_answers())*100f) +"%</td>\n" + 
				"      </tr>\n" + 
				"      <tr>\n" + 
				"        <td style=\"font-weight:bolder\">Multiplicação</td>\n" + 
				"        <td>"+ (user.getQtt_correct_mul_answers()) +"</td>\n" + 
				"        <td>"+ (user.getQtt_mul_answers()-user.getQtt_correct_mul_answers()) +"</td>\n" + 
				"        <td>"+ String.format("%.2f",((float)user.getQtt_correct_mul_answers()/user.getQtt_mul_answers())*100f) +"%</td>\n" + 
				"      </tr>\n" + 
				"      <tr>\n" + 
				"        <td style=\"font-weight:bolder\">Divisão</td>\n" + 
				"        <td>"+ (user.getQtt_correct_div_answers()) +"</td>\n" + 
				"        <td>"+ (user.getQtt_div_answers()-user.getQtt_correct_div_answers()) +"</td>\n" + 
				"        <td>"+ String.format("%.2f",((float)user.getQtt_correct_div_answers()/user.getQtt_div_answers())*100f) +"%</td>\n" + 
				"      </tr>\n" + 
				"\n" + 
				"    </table>\n" + 
				"  </div>";
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
					"\n"+
					"<style>\n" + 
					"    #diagnostic {\n" + 
					"      font-family: Arial, Helvetica, sans-serif;\n" + 
					"      border-collapse: collapse;\n" + 
					"      width: 100%;\n" + 
					"    }\n" + 
					"\n" + 
					"    #diagnostic td,\n" + 
					"    #diagnostic th {\n" + 
					"      border: 1px solid #ddd;\n" + 
					"      padding: 8px;\n" + 
					"    }\n" + 
					"\n" + 
					"    #diagnostic tr:nth-child(even) {\n" + 
					"      background-color: #f2f2f2;\n" + 
					"    }\n" + 
					"\n" + 
					"    #diagnostic tr:hover {\n" + 
					"      background-color: #cdcdcd;\n" + 
					"    }\n" + 
					"\n" + 
					"    #diagnostic th {\n" + 
					"      padding-top: 12px;\n" + 
					"      padding-bottom: 12px;\n" + 
					"      text-align: left;\n" + 
					"      color: black;\n" + 
					"      font-weight: bolder;\n" + 
					"    }\n" + 
					"  </style>"+
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
					diagnostic +
					"        <div id=\"aparecerAtividadeDiv\" class=\"content center\">\n" +		
					contents + 
					"        </div>\n" +
					"      </article>\n" +
					"  </main>\n" +
					"  <script src=\"js/scriptsAtividade.js\"></script>\n" +
					"</body>\n" +
					"\n" +
					"</html>";
		}else {
			if(personDAO.is_professor(id)) {
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
							"            </form>"
							+ "<form id=\"form-update\" action=\"http://localhost:4567/activity/update/form\"\" method=\"post\">"+
							"  <input type=\"text\" name=\"id\" value=\""+a.getId()+"\" style=\"display:none\">"+
							"  <input type=\"text\" name=\"person_id\" value=\""+id+"\" style=\"display:none\">"
							+ ""
							+ "<button type=\"submit\">Editar atividade</button>"
							+ "</form>"
							+ "<form action=\"http://localhost:4567/activity/delete\" method=\"post\">"
							+ "<div><input type=\"text\" class=\"nav-top-input\" name=\"id\" value=\""+a.getId()+"\" style=\"display:none\"></div>"
							+ "<div><input type=\"text\" class=\"nav-top-input\" name=\"person_id\" value=\""+id+"\" style=\"display:none\"></div>"
							+ "<button type=\"submit\">Deletar atividade</button>"
							+ "</form>"
							+ "</div>\n" +
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
						"\n"+
						"<style>\n" + 
						"    #diagnostic {\n" + 
						"      font-family: Arial, Helvetica, sans-serif;\n" + 
						"      border-collapse: collapse;\n" + 
						"      width: 100%;\n" + 
						"    }\n" + 
						"\n" + 
						"    #diagnostic td,\n" + 
						"    #diagnostic th {\n" + 
						"      border: 1px solid #ddd;\n" + 
						"      padding: 8px;\n" + 
						"    }\n" + 
						"\n" + 
						"    #diagnostic tr:nth-child(even) {\n" + 
						"      background-color: #f2f2f2;\n" + 
						"    }\n" + 
						"\n" + 
						"    #diagnostic tr:hover {\n" + 
						"      background-color: #cdcdcd;\n" + 
						"    }\n" + 
						"\n" + 
						"    #diagnostic th {\n" + 
						"      padding-top: 12px;\n" + 
						"      padding-bottom: 12px;\n" + 
						"      text-align: left;\n" + 
						"      color: black;\n" + 
						"      font-weight: bolder;\n" + 
						"    }\n" + 
						"  </style>"+
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
						"      <article class=\"content\">\n" +
						diagnostic +
						"        <div id=\"aparecerAtividadeDiv\" class=\"flex-wrap center\">\n" +
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
								"            </form>"
								+ "</div>\n" +
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
							"\n"+
							"<style>\n" + 
							"    #diagnostic {\n" + 
							"      font-family: Arial, Helvetica, sans-serif;\n" + 
							"      border-collapse: collapse;\n" + 
							"      width: 100%;\n" + 
							"    }\n" + 
							"\n" + 
							"    #diagnostic td,\n" + 
							"    #diagnostic th {\n" + 
							"      border: 1px solid #ddd;\n" + 
							"      padding: 8px;\n" + 
							"    }\n" + 
							"\n" + 
							"    #diagnostic tr:nth-child(even) {\n" + 
							"      background-color: #f2f2f2;\n" + 
							"    }\n" + 
							"\n" + 
							"    #diagnostic tr:hover {\n" + 
							"      background-color: #cdcdcd;\n" + 
							"    }\n" + 
							"\n" + 
							"    #diagnostic th {\n" + 
							"      padding-top: 12px;\n" + 
							"      padding-bottom: 12px;\n" + 
							"      text-align: left;\n" + 
							"      color: black;\n" + 
							"      font-weight: bolder;\n" + 
							"    }\n" + 
							"  </style>"+
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
							"      <article class=\"content\">\n" +
							diagnostic +
							"        <div id=\"aparecerAtividadeDiv\" class=\"flex-wrap center\">\n" +
										contents + 
							"        </div>\n" +
							"      </article>\n" +
							"  </main>\n" +
							"  <script src=\"js/scriptsAtividade.js\"></script>\n" +
							"</body>\n" +
							"\n" +
							"</html>";
					}
				}
			
			
		res.body(body);
		return res.body();
	}
	public Object see(Request req, Response res) {
		int act_id = Integer.parseInt(req.queryParams("id"));
		int person_id = Integer.parseInt(req.queryParams("person_id"));
		Activity activity = activityDAO.get_by_id(act_id);
		Option[] options = activityDAO.get_options_by_activity(activity);
		String diff = "";
		try{diff = String.format("%.2f", activity.getDifficulty());}catch(MissingFormatWidthException e) {}
		String contents = "";
		contents += "<form action=\"http://localhost:4567/activity/see/validate\" method=\"post\">"+
				"      <div id=\"disciplinaMostrarAtividade\" class=\"div-title\"><h1 class=\"ex-title\">"+ activity.getSubject() +"</h1></div>\n" +
				"      <div id=\"tituloMostrarAtividade\" class=\"center\"><h1 class=\"subtitulo\">"+ activity.getTitle() +"</h1><h4>Dificuldade (0 - 5): "+ diff +"</4></div>\n" +
				"      <div id=\"enunciadoMostrarAtividade\" class=\"justify enunciado\">"+ activity.getStatement() +"</div>\n" +
				"      <div onclick=\"validate_options('"+options[0].getId()+"', 'respostaOpcao1', 'respostaOpcao2', 'respostaOpcao3', 'respostaOpcao4');\"id=\"opcao1MostrarAtividade\" class=\"justify ex-option\"><span class=\"negrito\">A</span>   <div class=\"opcao\" id=\"respostaOpcao1\">"+options[0].getOption_text()+"</div></div>\n" +
				"      <div onclick=\"validate_options('"+options[1].getId()+"', 'respostaOpcao2', 'respostaOpcao1', 'respostaOpcao3', 'respostaOpcao4');\"id=\"opcao2MostrarAtividade\" class=\"justify ex-option\"><span class=\"negrito\">B</span>   <div class=\"opcao\" id=\"respostaOpcao2\">"+options[1].getOption_text()+"</div></div>\n" +
				"      <div onclick=\"validate_options('"+options[2].getId()+"', 'respostaOpcao3', 'respostaOpcao2', 'opcao1MostrarAtividade', 'respostaOpcao4');\"id=\"opcao3MostrarAtividade\" class=\"justify ex-option\"<span class=\"negrito\">C</span>   <div class=\"opcao\" id=\"respostaOpcao3\">"+options[2].getOption_text()+"</div></div>\n" 
				+ "<input type=\"text\" name=\"id\" style=\"display:none\" value=\""+act_id+"\">"+
				"      <input type=\"text\" name=\"person_id\" value=\""+person_id+"\" style=\"display:none\">"
				+ "	   <div onclick=\"validate_options('"+options[3].getId()+"', 'respostaOpcao4', 'respostaOpcao2', 'respostaOpcao3', 'respostaOpcao1');\"id=\"opcao4MostrarAtividade\" class=\"justify ex-option\"><span class=\"negrito\">D</span>   <div class=\"opcao\" id=\"respostaOpcao4\">"+options[3].getOption_text()+"</div></div>\n" +
				"      <span id=\"validaRespostaSpan\" class=\"negrito center\"></span>"
				+ "<input type=\"text\" name=\"answer\" value=\"0\" style=\"visibility:hidden\" id=\"answer\">"+
				"		<center><button type=\"submit\">Responder</button></center>";
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
				+ "function validate_options(id, clicked, option2, option3, option4){\n" +
				" document.getElementById(clicked).style.backgroundColor = '#a5a5a5';"
				+ "document.getElementById('answer').setAttribute('value', id);"
				+ "document.getElementById(option2).style.backgroundColor = '#fff';"
				+ "document.getElementById(option3).style.backgroundColor = '#fff';"
				+ "document.getElementById(option4).style.backgroundColor = '#fff';" + 
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
				"        <div style=\"position: relative; width: 80%; left: 230px; top: 20px;\">\n" +
							contents + 
				"        </div>\n" + 
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
					"<form action=\"http://localhost:4567/create\" method=\"post\">" +
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
					"        <div id=\"aparecerAtividadeDiv\" style=\"position: relative; width: 80%; left: 230px; top: 20px;\">\n" +
								contents + 
					"        </div>\n" +
					"  </main>\n" +
					"  <script src=\"js/scriptsAtividade.js\"></script>\n" +
					"</body>\n" +
					"\n" +
					"</html>";
			res.body(body);
		}
		return res.body();
	}

	public Object change(Request req, Response res) {
		int id = Integer.parseInt(req.queryParams("id"));
		int person_id = Integer.parseInt(req.queryParams("person_id"));
		String title = req.queryParams("title");
		String subject = req.queryParams("subject");
		String theme = req.queryParams("theme");
		String statement = req.queryParams("statement");
		String op_a = req.queryParams("a");
		String op_b = req.queryParams("b");
		String op_c = req.queryParams("c");
		String op_d = req.queryParams("d");
		String or1 = req.queryParams("option_radio1");
		String or2 = req.queryParams("option_radio2");
		String or3 = req.queryParams("option_radio3");
		String or4 = req.queryParams("option_radio4");
		Activity a = activityDAO.get_by_id(id);
		Activity updated = new Activity(person_id, id, title, subject, theme, statement, a.getDifficulty(), a.getQttAnswers(), a.getQttWrongAnswers());
		Option[] old_options = activityDAO.get_options_by_activity(a);
		Option[] options  = new Option[4];
		if(or1 != null) options[0] = new Option(a.getId(), old_options[0].getId(), op_a, true);
		else options[0] = new Option(a.getId(), old_options[0].getId(), op_a, false);
		if(or2 != null) options[1] = new Option(a.getId(), old_options[1].getId(), op_b, true);
		else options[1] = new Option(a.getId(), old_options[1].getId(), op_b, false);
		if(or3 != null) options[2] = new Option(a.getId(), old_options[2].getId(), op_c, true);
		else options[2] = new Option(a.getId(), old_options[2].getId(), op_c, false);
		if(or4 != null) options[3] = new Option(a.getId(), old_options[3].getId(), op_d, true);
		else options[3] = new Option(a.getId(), old_options[3].getId(), op_d, false);
		String contents = "";
		if(activityDAO.update(updated, options, id)) {
			res.status(200);
			contents += "A atividade foi atualizada com sucesso";
		}else {
			res.status(400);
			contents += "Não foi possível atualizar a atividade.";
		}
		String body = "";
		body += "<!DOCTYPE html>\n" +
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
		"        <div id=\"aparecerAtividadeDiv\" style=\"position: relative; width: 80%; left: 230px; top: 20px;\">\n" +
					contents + 
		"        </div>\n" +
		"  </main>\n" +
		"  <script src=\"js/scriptsAtividade.js\"></script>\n" +
		"</body>\n" +
		"\n" +
		"</html>";
		res.body(body);
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
			if(activityDAO.add(a, options)) {
				res.status(201);
				res.body("Success! Status "+ res.status() + ".");				
			}else {
				res.status(400);
				res.body("Success! Activity was published. Status: "+ res.status() + ".");
			}

		}else {
			res.body("You're not allowed here!");
		}
		return res.body();
	}
	public Object delete(Request req, Response res) {
		int id = Integer.parseInt(req.queryParams("id"));
		int person_id = Integer.parseInt(req.queryParams("person_id"));
		String contents = "";
		String body = "";
		boolean b = false;
		if(personDAO.is_professor(person_id)) {
			b = activityDAO.delete(id);
			contents += "Conteúdo deletado com sucesso.";
		}
		if(b) {
			body += "<!DOCTYPE html>" + 
					"<html lang=\"pt-br\">" + 
					"<head>" + 
					"<title>FOCUS - Educação mais acessível</title>" + 
					"<meta charset=\"utf-8\">" + 
					"<meta name=\"viewport\" content=\"width=device-width, initial-scale=1, shrink-to-fit=no\">" + 
					"<script src=\"https://kit.fontawesome.com/37e4898af2.js\" crossorigin=\"anonymous\"></script>" + 
					"<link rel=\"stylesheet\" href=\"/styles/style-main.css\">" + 
					"</head>" + 
					"<body>" + 
					"<script type=\"text/javascript\">" + 
					"function addIdToPath(form_name, base_url){" + 
					"var your_form = document.getElementById(form_name);" + 
					"var id = your_form.elements.namedItem(\"id\").value;" + 
					"action_src = base_url + id;" + 
					"your_form.action = action_src;" + 
					"}" + 
					"function validate_options(id, clicked, option2, option3, option4){" + 
					" document.getElementById(clicked).style.backgroundColor = '#a5a5a5';" + 
					"document.getElementById('answer').setAttribute('value', id);" + 
					"document.getElementById(option2).style.backgroundColor = '#fff';" + 
					"document.getElementById(option3).style.backgroundColor = '#fff';" + 
					"document.getElementById(option4).style.backgroundColor = '#fff';" + 
					" }" + 
					"</script>" + 
					"  <header>" + 
					"    <nav class=\"nav-top\">" + 
					"      <div>" + 
					"        <div class=\"nav-top-expand\">" + 
					"          <button id=\"openSideBar\"><i class=\"fa-solid fa-bars\"></i></button>" + 
					"          <input type=\"checkbox\" class=\"none\">" + 
					"        </div>" + 
					"        <h1 class=\"nav-top-logo\">FOCUS</h1>" + 
					"      </div>" + 
					"      <div>" + 
					"        <input type=\"text\" class=\"nav-top-input\" placeholder=\"Search for a keyword\" id=\"searchInputId\">" + 
					"        <a href=\"index.html\"><i class=\"fa-solid fa-arrow-right-from-bracket icon-l\"></i></a>" + 
					"      </div>" + 
					"    </nav>" + 
					"  </header>" + 
					"  <main class=\"main\">" + 
					"      <aside class=\"aside-bar\">" + 
					"        <a href=\"/main\"><div><i class=\"fa-solid fa-stopwatch icon\"></i><h1 class=\"aside-option\">Pendências</h1></div></a>" + 
					"        <a href=\"/content\"><div><i class=\"fa-solid fa-file-alt icon\"></i><h1 class=\"aside-option\">Conteúdos</h1></div></a>" + 
					"        <a href=\"/activity\"><div><i class=\"fa-solid fa-pencil-alt icon\"></i><h1 class=\"aside-option\">Atividades</h1></div></a>" + 
					"        <a href=\"/message\"><div><i class=\"fa-solid fa-envelope icon\"></i><h1 class=\"aside-option\">Mensagens</h1></div></a>" + 
					"      </aside>" + 
					"        <div style=\"position: relative; width: 80%; left: 230px; top: 20px;\">" + 
								contents + 
					"        </div>" + 
					"  </main>" + 
					"  <script src=\"js/scriptsAtividade.js\"></script>" + 
					"</body>" + 
					"</html>";
			res.body(body);
		}
		else res.body("Something went wrong.");
		return res.body();
	}
	public Object update(Request req, Response res) {
		int id = Integer.parseInt(req.queryParams("id"));
		int person_id = Integer.parseInt(req.queryParams("person_id"));
		String contents = "";
		String body = "";
		boolean b = false;
		if(personDAO.is_professor(person_id)) {
			contents += ""
					+ "<form action=\"http://localhost:4567/activity/update/validate\" method=\"post\">"
					+ "<div><input type=\"text\" class=\"nav-top-input flex-form\" name=\"id\" value=\""+id+"\" style=\"display:none\"></div>"
					+ "<div><input type=\"text\" class=\"nav-top-input flex-form\" name=\"person_id\" value=\""+person_id+"\" style=\"display:none\"></div>"
					+ "<div><input type=\"text\" class=\"nav-top-input flex-form\" name=\"title\" placeholder=\"Título\"></div></br>"
					+ "<div><input type=\"text\" class=\"nav-top-input flex-form\" name=\"subject\" placeholder=\"Disciplina\"></div></br>"
					+ "<div><input type=\"text\" class=\"nav-top-input flex-form\" name=\"theme\" placeholder=\"Matéria\"></div></br>"
					+ "<div><input type=\"text\" class=\"nav-top-input flex-form\" name=\"statement\" placeholder=\"Enunciado\"></div></div></br>"
					+ "<div style=\"position: relative;\">"
					+ "<div><input type=\"radio\" aria-label=\"Radio button for following text input\" name=\"option_radio1\" id=\"respostaOpcaoC\"><div><input type=\"text\" class=\"nav-top-input flex-form\" name=\"a\" placeholder=\"Opção A\"></div></div><br>\n" + 
					"  <div><input type=\"radio\" aria-label=\"Radio button for following text input\" name=\"option_radio2\" id=\"respostaOpcaoC\"><div><input type=\"text\" class=\"nav-top-input flex-form\" name=\"b\" placeholder=\"Opção B\"></div></div><br>\n" + 
					"  <div><input type=\"radio\" aria-label=\"Radio button for following text input\" name=\"option_radio3\" id=\"respostaOpcaoC\"><div><input type=\"text\" class=\"nav-top-input flex-form\" name=\"c\" placeholder=\"Opção C\"></div></div><br>\n" + 
					"  <div><input type=\"radio\" aria-label=\"Radio button for following text input\" name=\"option_radio4\" id=\"respostaOpcaoC\"><div><input type=\"text\" class=\"nav-top-input flex-form\" name=\"d\" placeholder=\"Opção D\"></div></div><br>"
					+ "<br><button type=\"submit\">Atualizar dados</button>"
					+ "</form>";
			b = true;
		}
		if(b) {
			body += "<!DOCTYPE html>" + 
					"<html lang=\"pt-br\">" + 
					"<head>" + 
					"<title>FOCUS - Educação mais acessível</title>" + 
					"<meta charset=\"utf-8\">" + 
					"<meta name=\"viewport\" content=\"width=device-width, initial-scale=1, shrink-to-fit=no\">" + 
					"<script src=\"https://kit.fontawesome.com/37e4898af2.js\" crossorigin=\"anonymous\"></script>" + 
					"<link rel=\"stylesheet\" href=\"/styles/style-main.css\">" + 
					"</head>" + 
					"<body>" + 
					"<script type=\"text/javascript\">" + 
					"function addIdToPath(form_name, base_url){" + 
					"var your_form = document.getElementById(form_name);" + 
					"var id = your_form.elements.namedItem(\"id\").value;" + 
					"action_src = base_url + id;" + 
					"your_form.action = action_src;" + 
					"}" + 
					"function validate_options(id, clicked, option2, option3, option4){" + 
					" document.getElementById(clicked).style.backgroundColor = '#a5a5a5';" + 
					"document.getElementById('answer').setAttribute('value', id);" + 
					"document.getElementById(option2).style.backgroundColor = '#fff';" + 
					"document.getElementById(option3).style.backgroundColor = '#fff';" + 
					"document.getElementById(option4).style.backgroundColor = '#fff';" + 
					" }" + 
					"</script>" + 
					"  <header>" + 
					"    <nav class=\"nav-top\">" + 
					"      <div>" + 
					"        <div class=\"nav-top-expand\">" + 
					"          <button id=\"openSideBar\"><i class=\"fa-solid fa-bars\"></i></button>" + 
					"          <input type=\"checkbox\" class=\"none\">" + 
					"        </div>" + 
					"        <h1 class=\"nav-top-logo\">FOCUS</h1>" + 
					"      </div>" + 
					"      <div>" + 
					"        <input type=\"text\" class=\"nav-top-input\" placeholder=\"Search for a keyword\" id=\"searchInputId\">" + 
					"        <a href=\"index.html\"><i class=\"fa-solid fa-arrow-right-from-bracket icon-l\"></i></a>" + 
					"      </div>" + 
					"    </nav>" + 
					"  </header>" + 
					"  <main class=\"main\">" + 
					"      <aside class=\"aside-bar\">" + 
					"        <a href=\"/main\"><div><i class=\"fa-solid fa-stopwatch icon\"></i><h1 class=\"aside-option\">Pendências</h1></div></a>" + 
					"        <a href=\"/content\"><div><i class=\"fa-solid fa-file-alt icon\"></i><h1 class=\"aside-option\">Conteúdos</h1></div></a>" + 
					"        <a href=\"/activity\"><div><i class=\"fa-solid fa-pencil-alt icon\"></i><h1 class=\"aside-option\">Atividades</h1></div></a>" + 
					"        <a href=\"/message\"><div><i class=\"fa-solid fa-envelope icon\"></i><h1 class=\"aside-option\">Mensagens</h1></div></a>" + 
					"      </aside>" + 
					"        <div style=\"position: relative; width: 80%; left: 230px; top: 20px;\">" + 
					contents + 
					"        </div>" + 
					"  </main>" + 
					"  <script src=\"js/scriptsAtividade.js\"></script>" + 
					"</body>" + 
					"</html>";
			res.body(body);
		}
		else res.body("Something went wrong.");
		return res.body();
	}
	public Object validate(Request req, Response res) {
		int act_id = Integer.parseInt(req.queryParams("id"));
		int person_id = Integer.parseInt(req.queryParams("person_id"));
		int answer = Integer.parseInt(req.queryParams("answer"));
		String body = "";
		String contents = "";
		boolean status = (answer == activityDAO.get_correct_option_by_activity_id(act_id).getId());
		Activity activity = null;
		float difficulty = -1.0f;
		String diff = "";
		if(status) {
			activity = activityDAO.get_by_id(act_id);
			difficulty = activity.getDifficulty();
			try{diff = String.format("%.2f", activity.getDifficulty());}catch(MissingFormatWidthException e) {}
			Option[] options = activityDAO.get_options_by_activity(activity);
			contents = "" +
					"<form action=\"http://localhost:4567/activity/see/next\" method=\"post\">" +
					"      <div id=\"disciplinaMostrarAtividade\" class=\"div-title\"><h1 class=\"ex-title\">"+ activity.getSubject() +"</h1></div>\n" +
					"      <div id=\"tituloMostrarAtividade\" class=\"center\"><h1 class=\"subtitulo\">"+ activity.getTitle() +"</h1><h4>Dificuldade (0 - 5): "+ diff +"</h4></div>\n" +
					"      <div id=\"enunciadoMostrarAtividade\" class=\"justify enunciado\">"+ activity.getStatement() +"</div>\n" +
					"      <div id=\"opcao1MostrarAtividade\" class=\"justify ex-option\"><span class=\"negrito\">A</span>   <div class=\"opcao\" id=\"respostaOpcao1\">"+options[0].getOption_text()+"</div></div>\n" +
					"      <div id=\"opcao2MostrarAtividade\" class=\"justify ex-option\"><span class=\"negrito\">B</span>   <div class=\"opcao\" id=\"respostaOpcao2\">"+options[1].getOption_text()+"</div></div>\n" +
					"      <div id=\"opcao3MostrarAtividade\" class=\"justify ex-option\"<span class=\"negrito\">C</span>   <div class=\"opcao\" id=\"respostaOpcao3\">"+options[2].getOption_text()+"</div></div>\n" 
					+ "<input type=\"text\" name=\"id\" style=\"display:none\" value=\""+act_id+"\">"+
					"      <input type=\"text\" name=\"person_id\" value=\""+person_id+"\" style=\"display:none\">"+
					"      <input type=\"text\" name=\"status\" value=\"1\" style=\"display:none\">"
					+ "	   <div id=\"opcao4MostrarAtividade\" class=\"justify ex-option\"><span class=\"negrito\">D</span>   <div class=\"opcao\" id=\"respostaOpcao4\">"+options[3].getOption_text()+"</div></div>\n" +
					"      <span id=\"validaRespostaSpan\" class=\"negrito center\"></span>"
					+ "<input type=\"text\" name=\"answer\" value=\"0\" style=\"display:none\" id=\"answer\">"
					+ "<input type=\"text\" name=\"d\" value=\""+ difficulty +"\" style=\"display:none\">"
					+ "<span id=\"validaRespostaSpan\" class=\"negrito center\" style=\"color:#0f0\">Correto!</span>"+
					"		<center><button type=\"submit\">Próxima questão</button></center></form>";
		}else {
			activity = activityDAO.get_by_id(act_id);
			difficulty = activity.getDifficulty();
			try{diff = String.format("%.2f", activity.getDifficulty());}catch(MissingFormatWidthException e) {}
			Option[] options = activityDAO.get_options_by_activity(activity);
			contents = ""+
					"<form action=\"http://localhost:4567/activity/see/next\" method=\"post\">" +
					"      <div id=\"disciplinaMostrarAtividade\" class=\"div-title\"><h1 class=\"ex-title\">"+ activity.getSubject() +"</h1></div>\n" +
					"      <div id=\"tituloMostrarAtividade\" class=\"center\"><h1 class=\"subtitulo\">"+ activity.getTitle() +"</h1><h4>Dificuldade (0 - 5): "+diff+"</h4></div>\n" +
					"      <div id=\"enunciadoMostrarAtividade\" class=\"justify enunciado\">"+ activity.getStatement() +"</div>\n" +
					"      <div id=\"opcao1MostrarAtividade\" class=\"justify ex-option\"><span class=\"negrito\">A</span>   <div class=\"opcao\" id=\"respostaOpcao1\">"+options[0].getOption_text()+"</div></div>\n" +
					"      <div id=\"opcao2MostrarAtividade\" class=\"justify ex-option\"><span class=\"negrito\">B</span>   <div class=\"opcao\" id=\"respostaOpcao2\">"+options[1].getOption_text()+"</div></div>\n" +
					"      <div id=\"opcao3MostrarAtividade\" class=\"justify ex-option\"<span class=\"negrito\">C</span>   <div class=\"opcao\" id=\"respostaOpcao3\">"+options[2].getOption_text()+"</div></div>\n" 
					+ "<input type=\"text\" name=\"id\" style=\"display:none\" value=\""+act_id+"\">"+
					"      <input type=\"text\" name=\"person_id\" value=\""+person_id+"\" style=\"display:none\">"+
					"      <input type=\"text\" name=\"status\" value=\"0\" style=\"display:none\">"
					+ "	   <div id=\"opcao4MostrarAtividade\" class=\"justify ex-option\"><span class=\"negrito\">D</span>   <div class=\"opcao\" id=\"respostaOpcao4\">"+options[3].getOption_text()+"</div></div>\n" +
					"      <span id=\"validaRespostaSpan\" class=\"negrito center\"></span>"
					+ "<input type=\"text\" name=\"answer\" value=\"0\" style=\"display:none\" id=\"answer\">"
					+ "<input type=\"text\" name=\"d\" value=\""+ difficulty +"\" style=\"display:none\">"
					+ "<span id=\"validaRespostaSpan\" class=\"negrito center\" style=\"color:#f00\">Você errou :(</span>"+
					"		<center><button type=\"submit\">Próxima questão</button></center></form>";
		}
		Person user = personDAO.get_by_id(person_id);
		user.setQtt_answers(user.getQtt_answers()+1);
		if(status) {
			if(activity.getTheme().contains("Soma")) {
				user.setQtt_correct_sum_answers(user.getQtt_correct_sum_answers()+1);
				user.setQtt_sum_answers(user.getQtt_sum_answers()+1);
			}else if(activity.getTheme().contains("Subtração")) {
				user.setQtt_correct_sub_answers(user.getQtt_correct_sub_answers()+1);
				user.setQtt_sub_answers(user.getQtt_sub_answers()+1);
			}else if(activity.getTheme().contains("Multiplicação")) {
				user.setQtt_correct_mul_answers(user.getQtt_correct_mul_answers()+1);
				user.setQtt_mul_answers(user.getQtt_mul_answers()+1);
			}else if(activity.getTheme().contains("Divisão")) {
				user.setQtt_correct_div_answers(user.getQtt_correct_div_answers()+1);
				user.setQtt_div_answers(user.getQtt_div_answers()+1);
			}
		}else {
			user.setQtt_wrong_answers(user.getQtt_wrong_answers()+1);
			if(activity.getTheme().contains("Soma")) {
				user.setQtt_sum_answers(user.getQtt_sum_answers()+1);
			}else if(activity.getTheme().contains("Subtração")) {
				user.setQtt_sub_answers(user.getQtt_sub_answers()+1);
			}else if(activity.getTheme().contains("Multiplicação")) {
				user.setQtt_mul_answers(user.getQtt_mul_answers()+1);
			}else if(activity.getTheme().contains("Divisão")) {
				user.setQtt_div_answers(user.getQtt_div_answers()+1);
			}
		}
		activity.updateDifficulty(status);
		activityDAO.update(activity, act_id);
		personDAO.update(user, person_id);
		body += ""
				+ "<!DOCTYPE html> "+ 
				"<html lang=\"pt-br\">" +
				"<head>" + 
				"  <title>FOCUS - Educação mais acessível</title> "+ 
				"  <meta charset=\"utf-8\">" + 
				"  <meta name=\"viewport\" content=\"width=device-width, initial-scale=1, shrink-to-fit=no\">" + 
				"  <script src=\"https://kit.fontawesome.com/37e4898af2.js\" crossorigin=\"anonymous\"></script> "+ 
				"  <link rel=\"stylesheet\" href=\"/styles/style-main.css\"> "+ 
				"</head>" + 
				"<body>"+
				"<script type=\"text/javascript\">"
				+ "function addIdToPath(form_name, base_url){" + 
				"var your_form = document.getElementById(form_name);" + 
				"var id = your_form.elements.namedItem(\"id\").value;" + 
				"action_src = base_url + id;" + 
				"your_form.action = action_src;" + 
				"}" + 
				"function validate_options(id, clicked, option2, option3, option4){ "+ 
				" document.getElementById(clicked).style.backgroundColor = '#a5a5a5';\"\n" + 
				"document.getElementById('answer').setAttribute('value', id);\"\n" + 
				"document.getElementById(option2).style.backgroundColor = '#fff';\"\n" + 
				"document.getElementById(option3).style.backgroundColor = '#fff';\"\n" + 
				"document.getElementById(option4).style.backgroundColor = '#fff';\"\n"+ 
				" }\n" + 
				"</script>"+ 
				"  <header> "+ 
				"    <nav class=\"nav-top\">" + 
				"      <div>" + 
				"        <div class=\"nav-top-expand\"> "+ 
				"          <button id=\"openSideBar\"><i class=\"fa-solid fa-bars\"></i></button> "+ 
				"          <input type=\"checkbox\" class=\"none\">" + 
				"        </div> "+ 
				"        <h1 class=\"nav-top-logo\">FOCUS</h1>" + 
				"      </div> "+ 
				"      <div> "+ 
				"        <input type=\"text\" class=\"nav-top-input\" placeholder=\"Search for a keyword\" id=\"searchInputId\"> "+ 
				"        <a href=\"index.html\"><i class=\"fa-solid fa-arrow-right-from-bracket icon-l\"></i></a> "+ 
				"      </div> "+ 
				"    </nav> "+ 
				"  </header>" + 
				"  <main class=\"main\"> "+ 
				"      <aside class=\"aside-bar\"> "+ 
				"        <a href=\"/main\"><div><i class=\"fa-solid fa-stopwatch icon\"></i><h1 class=\"aside-option\">Pendências</h1></div></a>" + 
				"        <a href=\"/content\"><div><i class=\"fa-solid fa-file-alt icon\"></i><h1 class=\"aside-option\">Conteúdos</h1></div></a>" + 
				"        <a href=\"/activity\"><div><i class=\"fa-solid fa-pencil-alt icon\"></i><h1 class=\"aside-option\">Atividades</h1></div></a>" + 
				"        <a href=\"/message\"><div><i class=\"fa-solid fa-envelope icon\"></i><h1 class=\"aside-option\">Mensagens</h1></div></a>" + 
				"      </aside>" + 
				"        <div style=\"position: relative; width: 80%; left: 230px; top: 20px;\"> "
				+ contents +
				"        </div>\n"+ 
				"  </main> "+ 
				"  <script src=\"js/scriptsAtividade.js\"></script>" + 
				"</body> "+
				"</html>";
		res.body(body);
		return res.body();
	}
	public Object next(Request req, Response res) {
		int act_id = Integer.parseInt(req.queryParams("id"));
		int status = Integer.parseInt(req.queryParams("status"));
		int person_id = Integer.parseInt(req.queryParams("person_id"));
		float difficulty = Float.parseFloat(req.queryParams("d"));
		Activity activity = activityDAO.get_by_diff(difficulty, person_id, act_id, status);
		Option[] options = activityDAO.get_options_by_activity(activity);
		String diff = "";
		try{diff = String.format("%.2f", activity.getDifficulty());}catch(MissingFormatWidthException e) {}
		String contents = "";
		contents += "<form action=\"http://localhost:4567/activity/see/validate\" method=\"post\">"+
				"      <div id=\"disciplinaMostrarAtividade\" class=\"div-title\"><h1 class=\"ex-title\">"+ activity.getSubject() +"</h1></div>\n" +
				"      <div id=\"tituloMostrarAtividade\" class=\"center\"><h1 class=\"subtitulo\">"+ activity.getTitle() +"</h1><h4>Dificuldade (0 - 5): "+ diff +"</4></div>\n" +
				"      <div id=\"enunciadoMostrarAtividade\" class=\"justify enunciado\">"+ activity.getStatement() +"</div>\n" +
				"      <div onclick=\"validate_options('"+options[0].getId()+"', 'respostaOpcao1', 'respostaOpcao2', 'respostaOpcao3', 'respostaOpcao4');\"id=\"opcao1MostrarAtividade\" class=\"justify ex-option\"><span class=\"negrito\">A</span>   <div class=\"opcao\" id=\"respostaOpcao1\">"+options[0].getOption_text()+"</div></div>\n" +
				"      <div onclick=\"validate_options('"+options[1].getId()+"', 'respostaOpcao2', 'respostaOpcao1', 'respostaOpcao3', 'respostaOpcao4');\"id=\"opcao2MostrarAtividade\" class=\"justify ex-option\"><span class=\"negrito\">B</span>   <div class=\"opcao\" id=\"respostaOpcao2\">"+options[1].getOption_text()+"</div></div>\n" +
				"      <div onclick=\"validate_options('"+options[2].getId()+"', 'respostaOpcao3', 'respostaOpcao2', 'opcao1MostrarAtividade', 'respostaOpcao4');\"id=\"opcao3MostrarAtividade\" class=\"justify ex-option\"<span class=\"negrito\">C</span>   <div class=\"opcao\" id=\"respostaOpcao3\">"+options[2].getOption_text()+"</div></div>\n" 
				+ "<input type=\"text\" name=\"id\" style=\"display:none\" value=\""+activity.getId()+"\">"+
				"      <input type=\"text\" name=\"person_id\" value=\""+person_id+"\" style=\"display:none\">"
				+ "	   <div onclick=\"validate_options('"+options[3].getId()+"', 'respostaOpcao4', 'respostaOpcao2', 'respostaOpcao3', 'respostaOpcao1');\"id=\"opcao4MostrarAtividade\" class=\"justify ex-option\"><span class=\"negrito\">D</span>   <div class=\"opcao\" id=\"respostaOpcao4\">"+options[3].getOption_text()+"</div></div>\n" +
				"      <span id=\"validaRespostaSpan\" class=\"negrito center\"></span>"
				+ "<input type=\"text\" name=\"answer\" value=\"0\" style=\"visibility:hidden\" id=\"answer\">"+
				"		<center><button type=\"submit\">Responder</button></center></form>";
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
				+ "function validate_options(id, clicked, option2, option3, option4){\n" +
				" document.getElementById(clicked).style.backgroundColor = '#a5a5a5';"
				+ "document.getElementById('answer').setAttribute('value', id);"
				+ "document.getElementById(option2).style.backgroundColor = '#fff';"
				+ "document.getElementById(option3).style.backgroundColor = '#fff';"
				+ "document.getElementById(option4).style.backgroundColor = '#fff';" + 
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
				"        <div style=\"position: relative; width: 80%; left: 230px; top: 20px;\">\n" +
							contents + 
				"        </div>\n" + 
				"  </main>\n" +
				"  <script src=\"js/scriptsAtividade.js\"></script>\n" +
				"</body>\n" +
				"\n" +
				"</html>";
		res.body(body);
		return res.body();
	}
}
