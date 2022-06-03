package server;

import java.sql.SQLException;
import java.util.Date;

import model.Message;
import model.Person;
import dao.MessageDAO;
import dao.PersonDAO;
import spark.Request;
import spark.Response;

public class MessageService {
	private MessageDAO messageDAO = new MessageDAO();
	private PersonDAO personDAO = new PersonDAO();
	
	public MessageService() {
		messageDAO.conectar();
		
	}
	public Object sendMsg(Request request,Response response){
		boolean deuErro = false;
		int senderid = -1;
		int addreseeid = -1;
		int id = -1;
		String corpo = "";
		String assunto = Message.getAssuntoDefault();
		if(request.params(":senderid")==null) {
			deuErro = true;
		}else {
			senderid = Integer.parseInt(request.params(":senderid"));
			if(request.queryParams("addresee")==null) {
				deuErro = true;
			}else {
				 addreseeid = Integer.parseInt(request.queryParams("addresee"));
				if(request.queryParams("body")==null) {
					deuErro = true;
				}else {
					corpo = request.queryParams("body");
					}
				}
			}
		
		if(deuErro) {
			throw new RuntimeException();
		}else {
			 id = messageDAO.idNextFinder();
			if(request.queryParams("subject")!=null) {
				assunto = request.queryParams("subject");
			}
		}
		Date data = new Date();
		Message msg = new Message(senderid,addreseeid,id,assunto,corpo,data);
		messageDAO.createMessage(msg);
		Message[] msg1 = (Message[]) messageDAO.readMessagesReceived(senderid);
		Message[]msg2 = (Message[]) messageDAO.readMessagesSent(senderid);//Considerado dentro da função como a id de quem mandou
		String nome = "";
		String contents="";
		String mycontent ="";
		for(int v = 0;msg1!=null&&v<msg1.length;v++) {
			Person pessoa = (Person) personDAO.get_by_id(msg1[v].getSender_id());
		if(personDAO.is_professor(pessoa.getId())) {
			nome=pessoa.getSurname();
			}else {
			nome=pessoa.getFirst_name();
			}
		/*contents += "<div class=\"card-ex\">\n" +
				"      <div class=\"card-title\">\n" +
				"         <h1 class=\"card-title\">Mensagem de : " +nome+"</h1>\n" +
				"         <h2 class=\"card-title\">Data: " + msg1[v].getDateAsStringDisplay()+"</h2>\n" +
				"      </div>\n" +
				"      <div class=\"card-body\">\n" +
				"<strong><p class=\"card-text\"> <a href=\"http://localhost:4567/message/one/"+msg1[v].getId() +"\">Clique aqui para ver a mensagem</a></p></strong>\n" +
				"      </div>\n"+
				"    </div>\n";*/
		contents += "<a href=\"http://localhost:4567/message/one/" + msg1[v].getId() + "\" style=\"text-decoration: none\">" 
				   +"<div class=\"card-ex\" style=\"width: 100%;height: fit-content;margin: 15px 0px\">\n" 
				   +"<div class=\"card-title\" style=\"background-color: #3498DB\">\n" 
				   +"<h1 class=\"card-title\" style=\"color:black; background-color: transparent\">Mensagem de : " +nome+"</h1>\n" 
				   +"<h2 class=\"card-title\" style=\"color:#F4F6F6; background-color: transparent\">Data: " + msg1[v].getDateAsStringDisplay()+"</h2>\n" 
				   +"</div>\n" 
				   +"</div>\n"
				   +"</a>";
		}
		
		for(int v = 0;msg2!=null&&v<msg2.length;v++) {
			Person pessoaTu = (Person) personDAO.get_by_id(msg2[v].getAddresee_id());
			if(personDAO.is_professor(pessoaTu.getId())) {
				nome=pessoaTu.getSurname();
				}else {
				nome=pessoaTu.getFirst_name();
				}
		         /*mycontent += "<div class=\"card-ex\">\n" +
				"      <div class=\"card-title\">\n" +
				"         <h1 class=\"card-title\">Mensagem sua para : " +nome+"</h1>\n" +
				"         <h2 class=\"card-title\">Data: " + msg2[v].getDateAsStringDisplay()+"</h2>\n" +
				"      </div>\n" +
				"      <div class=\"card-body\">\n" +
				"<strong><p class=\"card-text\"> <a href=\"http://localhost:4567/message/edit/"+msg2[v].getId() +"\">Clique aqui para ver e/ou editar a mensagem</a></p></strong>\n" +
				"      </div>\n"+
				"    </div>\n";*/
			mycontent += "<a href=\"http://localhost:4567/message/edit/" + msg2[v].getId() + "\" style=\"text-decoration: none\">"  
    		 	    +"<div class=\"card-ex\" style=\"width: 100%;height: fit-content;margin: 15px 0px\">\n" 
    		        +"<div class=\"card-title\" style=\"background-color: #3498DB\">\n" 
    		        +"<h1 class=\"card-title\" style=\"color:black; background-color: transparent\">Mensagem sua para : " +nome+"</h1>\n" 
    		        +"<h2 class=\"card-title\" style=\"color:#F4F6F6; background-color: transparent\">Data: " + msg2[v].getDateAsStringDisplay()+"</h2>\n" 
    		        +"</div>\n" 
    		        +"</div>\n"
    		        +"</a>";
		}
		String body= "";
		body+=   "<!DOCTYPE html>\n" 
				+"<html lang=\"pt-br\">\n" 
				+"\n" 
				+"<head>\n" 
				+"<title>FOCUS - Educação mais acessível</title>\n" 
				+"<meta charset=\"utf-8\">\n" 
				+"<meta name=\"viewport\" content=\"width=device-width, initial-scale=1, shrink-to-fit=no\">\n" 
				+"<script src=\"https://kit.fontawesome.com/37e4898af2.js\" crossorigin=\"anonymous\"></script>\n" 
				+"<link rel=\"stylesheet\" href=\"/styles/style-main.css\">\n" 
				+"<link rel=\"stylesheet\" href=\"/styles/style-mensagens.css\">\n" 
				+"\n" 
				+"</head>\n" 
				+"\n" 
				+"<body>\n"
				+"<script type=\"text/javascript\">"
				+"function addIdToPath(form_name, base_url){\n" 
				+"var your_form = document.getElementById(form_name);\n" 
				+"var id = your_form.elements.namedItem(\"id\").value;\n" 
				+"action_src = base_url + id;\n"
				+"your_form.action = action_src;\n" 
				+"}"
				+"</script>" 
				+"<header>\n" 
				+"<nav class=\"nav-top\">\n" 
				+"<div>\n" 
				+"<div class=\"nav-top-expand\">\n" 
				+"<button id=\"openSideBar\"><i class=\"fa-solid fa-bars\"></i></button>\n" 
				+"<input type=\"checkbox\" class=\"none\">\n" 
				+"</div>\n" 
				+"<h1 class=\"nav-top-logo\">FOCUS</h1>\n" 
				+"</div>\n" 
				+"<div>\n" 
				+"<input type=\"text\" class=\"nav-top-input\" placeholder=\"Search for a keyword\" id=\"searchInputId\">\n" 
				+"<a href=\"/\"><i class=\"fa-solid fa-arrow-right-from-bracket icon-l\"></i></a>\n" 
				+"</div>\n" 
				+"</nav>\n" 
				+"</header>\n" 
				+"\n" 
				+"<main class=\"main\">\n" 
				+"<aside class=\"aside-bar\">\n" 
				+"<a href=\"/main\"><div><i class=\"fa-solid fa-stopwatch icon\"></i><h1 class=\"aside-option\">Pendências</h1></div></a>\n" 
				+"<a href=\"/content\"><div><i class=\"fa-solid fa-file-alt icon\"></i><h1 class=\"aside-option\">Conteúdos</h1></div></a>\n" 
				+"<a href=\"/activity\"><div><i class=\"fa-solid fa-pencil-alt icon\"></i><h1 class=\"aside-option\">Atividades</h1></div></a>\n" 
				+"<a href=\"/message\"><div><i class=\"fa-solid fa-envelope icon\"></i><h1 class=\"aside-option\">Mensagens</h1></div></a>\n" 
				+"</aside>\n" 
				+"<div style=\"position: relative; width: 80%; left: 230px; top: 20px\">" 
				+"<div id=\"botoes\">\n" 
				+"<a href=\"http://localhost:4567/message/cre/" + senderid + "\">"
				+"<button style=\"background: green !important; color: white;\"><i class=\"fa-solid fa-plus\"></i> Nova mensagem</button>"
				+"</a>"
				+"</div>\n" 
				+"<div id=\"mensagensRecebidas\" style=\"width:100%\">" 
				+"<div class=\"div-title\">"
                +"<h1 class=\"text-center ex-title\" id=\"titulo\">"
                +"Mensagens recebidas"
                +"</h1>"
                +"</div>"
				+ contents 
				+"</div>\n" 
				+"<div id=\"mensagensEnviadas\" style=\"width:100%\">" 
				+"<div class=\"div-title\">"
                +"<h1 class=\"text-center ex-title\" id=\"titulo\">"
                +"Mensagens enviadas"
                +"</h1>"
                +"</div>"
				+ mycontent 
				+"</div>\n" 
				+"</div>\n" 
				+"</main>\n" 
				+"<script src=\"js/scriptsAtividade.js\"></script>\n" 
				+"</body>\n" 
				+"\n" 
				+"</html>";
		response.body(body);
		return response.body();
	}
	public Object createMsg(Request request,Response response) {
		int senderid = Integer.parseInt(request.params(":senderid"));
		String options = "";
		Person pessoas[] = messageDAO.dropdownChoices(senderid);
		for(int v = 0;v<pessoas.length;v++){
			String nome;
			if(personDAO.is_professor(pessoas[v].getId())) {
				nome=pessoas[v].getSurname();
				}else {
				nome=pessoas[v].getFirst_name();
				}
			options+="<option value = " +pessoas[v].getId()+">"+nome+"</option>";
		}
		
		String creation = "<form id=\"create_form\" action=\"http://localhost:4567/message/ate/"+senderid+"\">\n" 
						+ "<div class=\"div-title\" style=\"margin: 0px 0px 30px 0px;\">"
						+ "<h1 class=\"text-center ex-title\">Crie sua mensagem nos campos abaixo</h1>\n" 
						+ "</div>" 
						+ "<label for=\"addresee\">Destinatário</label>\n"
						+ "<div class=\"input-group\">"
						+ "<select class=\"input\" name=\"addresee\">" 
						+ "<option value=\"\">Selecione...</option>"
						+ options 
						+ "</select>"
						+ "</div>"
						+ "<label for=\"subject\">Assunto:</label>\n"
						+ "<div class=\"input-group\">"
						+ "<input class=\"input\" type=\"text\" name=\"subject\">"
						+ "</div>"
						+ "<label for=\"body\">Mensagem:</label>"
						+ "<div class=\"input-group\">"
						+ "<textarea class=\"input\" name=\"body\"></textarea>\n"
						+ "</div>"
						+ "<div class=\"input-group\" style=\"display: flex; justify-content: center; align-items: center;\">"
						+ "<button type=\"submit\" class=\"btn btn-secondary\">Enviar Mensagem</button>\n"
						+ "</div>"
						+ "</form>\n";
		
		String body="<!DOCTYPE html>\n" +
				"<html lang=\"pt-br\">\n" +
				"\n" +
				"<head>\n" +
				"  <title>FOCUS - Educação mais acessível</title>\n" +
				"  <meta charset=\"utf-8\">\n" +
				"  <meta name=\"viewport\" content=\"width=device-width, initial-scale=1, shrink-to-fit=no\">\n" +
				"  <script src=\"https://kit.fontawesome.com/37e4898af2.js\" crossorigin=\"anonymous\"></script>\n" +
				"  <link rel=\"stylesheet\" href=\"/styles/style-main.css\">\n" +
				"  <link rel=\"stylesheet\" href=\"/styles/style-mensagem.css\">\n" +
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
				"      <div style=\"position: relative; width: 80%; left: 230px; top: 20px\">\n" +
				"        <div id=\"mensagemCriacao\" style=\"width:100%\">"  +
				creation + 
	    "        </div>\n" +
				"      </div>\n" +
				"  </main>\n" +
				"  <script src=\"js/scriptsAtividade.js\"></script>\n" +
				"</body>\n" +
				"\n" +
				"</html>";
		
		
		response.body(body);
		return response.body();
	}
	public Object updateMsg(Request request,Response response) {
		String subject = request.queryParams("subject");
		String corpoMsg = request.queryParams("body");
		int id = Integer.parseInt(request.params(":msgid"));
		Message msg = (Message) messageDAO.readMessage(id);
		msg.setSubject(subject);
		msg.setBody(corpoMsg);
		if(subject.length()==0&&corpoMsg.length()==0) {
			int user = msg.getSender_id();
			messageDAO.deleteMessage(msg.getId());
			Message[] msg1 = (Message[]) messageDAO.readMessagesReceived(user);
			Message[]msg2 = (Message[]) messageDAO.readMessagesSent(user);//Considerado dentro da função como a id de quem mandou
			String nome = "";
			String contents="";
			String mycontent ="";
			for(int v = 0;msg1!=null&&v<msg1.length;v++) {
				Person pessoa = (Person) personDAO.get_by_id(msg1[v].getSender_id());
			if(personDAO.is_professor(pessoa.getId())) {
				nome=pessoa.getSurname();
				}else {
				nome=pessoa.getFirst_name();
				}
			contents += "<div class=\"card-ex\">\n" +
					"      <div class=\"card-title\">\n" +
					"         <h1 class=\"card-title\">Mensagem de : " +nome+"</h1>\n" +
					"         <h2 class=\"card-title\">Data: " + msg1[v].getDateAsStringDisplay()+"</h2>\n" +
					"      </div>\n" +
					"      <div class=\"card-body\">\n" +
					"<strong><p class=\"card-text\"> <a href=\"http://localhost:4567/message/one/"+msg1[v].getId() +"\">Clique aqui para ver a mensagem</a></p></strong>\n" +
					"      </div>\n"+
					"    </div>\n";
			}
			
			for(int v = 0;msg2!=null&&v<msg2.length;v++) {
				Person pessoaTu = (Person) personDAO.get_by_id(msg2[v].getAddresee_id());
				if(personDAO.is_professor(pessoaTu.getId())) {
					nome=pessoaTu.getSurname();
					}else {
					nome=pessoaTu.getFirst_name();
					}
			         mycontent += "<div class=\"card-ex\">\n" +
					"      <div class=\"card-title\">\n" +
					"         <h1 class=\"card-title\">Mensagem sua para : " +nome+"</h1>\n" +
					"         <h2 class=\"card-title\">Data: " + msg2[v].getDateAsStringDisplay()+"</h2>\n" +
					"      </div>\n" +
					"      <div class=\"card-body\">\n" +
					"<strong><p class=\"card-text\"> <a href=\"http://localhost:4567/message/edit/"+msg2[v].getId() +"\">Clique aqui para ver e/ou editar a mensagem</a></p></strong>\n" +
					"      </div>\n"+
					"    </div>\n";
			}
			String bodyIf= "";
			bodyIf+= "<!DOCTYPE html>\n" +
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
					"        <div style = \"inline-block\"id=\"aparecerAtividadeDiv\" class=\"content center\">\n" +
								contents + 
					"        </div>\n" +
					 " <div style = \"inline-block\"id=\"aparecerAtividadeDiv\" class=\"content center\">\n" +
							mycontent + 
				    "        </div>\n" +
					"      </article>\n" +
					"  </main>\n" +
					"  <script src=\"js/scriptsAtividade.js\"></script>\n" +
					"</body>\n" +
					"\n" +
					"</html>";
			response.body(bodyIf);
		}else {
			
		
		messageDAO.updateMessage(msg);
		
		Person pessoa = (Person) personDAO.get_by_id(msg.getAddresee_id());
		String nome = "";
	//Se é professor,colocamos o sobrenome no cabeçalho das mensagens
	//Se é aluno,o primeiro nome
		if(personDAO.is_professor(pessoa.getId())) {
			nome+=pessoa.getSurname();
			}else {
			nome+=pessoa.getFirst_name();
			}
		String mycontent = "<form action=\"http://localhost:4567/message/upd/"+id+"\">\n" +
				"         <h1 class=\"card-title\">Altere sua mensagem nos campos abaixo:</h1>\n" +
				" <label for=\"subject\">Assunto:</label>\n"+
				 " <input type=\"text\" name=\"subject\">" +
				"<strong><p class=\"card-text\">" +
				" <label for=\"body\">Mensagem:</label>"+
				 "</p></strong>\n" +
				" <textarea name=\"body\"></textarea>\n" +
				"<button type=\"submit\" class=\"btn btn-secondary\">Editar Mensagem</button>\n"+
				"</form>\n";
		String contents = "<div class=\"card-ex\">\n" +
				"      <div class=\"card-title\">\n" +
				"         <h1 class=\"card-title\">Mensagem sua para : " +nome+"</h1>\n" +
				"         <h2 class=\"card-title\">Data: " + msg.getDateAsStringDisplay()+"</h2>\n" +
				"      </div>\n" +
				"<div class=\"card-body\">\n" +
				"<strong><p class=\"card-text\"> " +msg.getSubject() + "</p></strong>\n" +
				"<p class = \"card-text\"> "+msg.getBody() + "</p>\n"+
				"</div>\n"+
				"</div>\n";
		String body= "";
		body+= "<!DOCTYPE html>\n" +
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
				"        <div style = \"inline-block\" id=\"aparecerAtividadeDiv\" class=\"content center\">\n" +
							contents + 
				"        </div>\n" +
				"        <div style = \"inline-block\" id=\"aparecerAtividadeDiv\" class=\"content center\">\n" +
				        mycontent + 
	            "        </div>\n" +
				"      </article>\n" +
				"  </main>\n" +
				"  <script src=\"js/scriptsAtividade.js\"></script>\n" +
				"</body>\n" +
				"\n" +
				"</html>";
		response.body(body);
		}
		return response.body();
		}
	public Object editMsg(Request request,Response response) {
		int id = Integer.parseInt(request.params(":msgid"));
		Message msg = (Message) messageDAO.readMessage(id);
		Person pessoa = (Person) personDAO.get_by_id(msg.getAddresee_id());
		String nome = "";
		if(personDAO.is_professor(pessoa.getId())) {
			nome+=pessoa.getSurname();
			}else {
			nome+=pessoa.getFirst_name();
			}
		String mycontent = "<form id=\"update_form\" action=\"http://localhost:4567/message/upd/"+id+"\">\n" 
						 + "<h1 class=\"card-title\">Altere sua mensagem nos campos abaixo:</h1>\n" 
						 + "<label for=\"subject\">Assunto:</label>\n"
						 + "<div class=\"input-group\">"
						 + "<input class=\"input\" type=\"text\" name=\"subject\">" 
						 + "</div>"
						 + "<label for=\"body\">Mensagem:</label>"
						 + "<div class=\"input-group\">"
						 + "<textarea class=\"input\" name=\"body\" rows=\"20\" cols=\"45\"></textarea>\n" 
						 + "</div>"
						 + "<div class=\"input-group\" style=\"display: flex; justify-content: center; align-items: center;\">"
						 + "<button type=\"submit\" class=\"btn btn-secondary\">Editar Mensagem</button>\n"
						 + "</div>"
						 + "</form>\n";
		
		String contents = "<div class=\"div-title\">"
						+ "<h1 class=\"text-center ex-title\" id=\"assunto\">" + msg.getSubject() + "</h1>"
						+ "</div>"
						+ "<div class=\"center\">"
						+ "<p style=\"margin: 15px 0px; color:#212F3D\"><strong>De:</strong> " + nome + "</p>"
						+ "<p style=\"margin: 0px 0px 15px 0px; color:#212F3D\"><strong>Data:</strong> " + msg.getDateAsStringDisplay() + "</p>"
						+ "</div>"
						+ "<div class=\"my-3 mx-3\">"
						+ "<p class=\"text-center\">" + msg.getBody() + "</p>"
						+ "</div";
		String body= "";
		body+= "<!DOCTYPE html>\n" +
				"<html lang=\"pt-br\">\n" +
				"\n" +
				"<head>\n" +
				"  <title>FOCUS - Educação mais acessível</title>\n" +
				"  <meta charset=\"utf-8\">\n" +
				"  <meta name=\"viewport\" content=\"width=device-width, initial-scale=1, shrink-to-fit=no\">\n" +
				"  <script src=\"https://kit.fontawesome.com/37e4898af2.js\" crossorigin=\"anonymous\"></script>\n" +
				"  <link rel=\"stylesheet\" href=\"/styles/style-main.css\">\n" +
				"  <link rel=\"stylesheet\" href=\"/styles/style-mensagem.css\">\n" +
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
				"      <div style=\"position: relative; width: 80%; left: 230px; top: 20px\">\n" +
				"        <div id=\"mensagemVizualizacao\" style=\"width:100%\">" +
							contents + 
				"        </div>\n" +
				"        <div id=\"mensagemAtualizacao\" style=\"width:100%; margin: 30px 0px\">" +
				        mycontent + 
	            "        </div>\n" +
				"      </div>\n" +
				"  </main>\n" +
				"  <script src=\"js/scriptsAtividade.js\"></script>\n" +
				"</body>\n" +
				"\n" +
				"</html>";
		response.body(body);
		return response.body();
	}
	public Object see1Msg(Request request,Response response){
		int id = Integer.parseInt(request.params(":msgid"));
		Message msg = (Message) messageDAO.readMessage(id);
		Person pessoa = (Person) personDAO.get_by_id(msg.getSender_id());
		String nome = "";
		if(personDAO.is_professor(pessoa.getId())) {
			nome+=pessoa.getSurname();
			}else {
			nome+=pessoa.getFirst_name();
			}
		String contents = "<div class=\"div-title\">"
						+ "<h1 class=\"text-center ex-title\" id=\"assunto\">" + msg.getSubject() + "</h1>"
						+ "</div>"
						+ "<div class=\"center\">"
						+ "<p style=\"margin: 15px 0px; color:#212F3D\"><strong>De:</strong> " + nome + "</p>"
						+ "<p style=\"margin: 0px 0px 15px 0px; color:#212F3D\"><strong>Data:</strong> " + msg.getDateAsStringDisplay() + "</p>"
						+ "</div>"
						+ "<div class=\"my-3 mx-3\">"
						+ "<p class=\"text-center\">" + msg.getBody() + "</p>"
						+ "</div";
					
		String body= "";
		body+= "<!DOCTYPE html>\n" +
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
				"      <div style=\"position: relative; width: 80%; left: 230px; top: 20px\">\n" +
				"        <div id=\"mensagemVizualizacao\" style=\"width:100%\">" +
							contents + 
				"        </div>\n" +
				"      </div>\n" +
				"  </main>\n" +
				"  <script src=\"js/scriptsAtividade.js\"></script>\n" +
				"</body>\n" +
				"\n" +
				"</html>";
		response.body(body);
		return response.body();
	}
	public Object seeAllMsg(Request request,Response response) {
		int addresseid = Integer.parseInt(request.params(":id"));
		
		Message[] msg = (Message[]) messageDAO.readMessagesReceived(addresseid);
		Message[]msg2 = (Message[]) messageDAO.readMessagesSent(addresseid);//Considerado dentro da função como a id de quem mandou
		String nome = "";
		String contents="";
		String mycontent ="";
		String creation = "<div class=\"card-ex\">\n" +
				"      <div class=\"card-title\">\n" +
				"         <a href =\"http://localhost:4567/message/cre/" + addresseid+"\"><img src =\"http://localhost:4567/imgs/sinalMais.png\"alt = \"Sinal de Mais,simbolizando criação de mensagem\" width=20% height=auto></a>\n" +
				"      </div>\n" +
				"      <div class=\"card-body\">\n" +
				"<strong><p class=\"card-text\">Clique acima para criar uma mensagem nova:</p></strong>\n" +
				"      </div>\n"+
				"    </div>\n";
		for(int v = 0;msg!=null&&v<msg.length;v++) {
			Person pessoa = (Person) personDAO.get_by_id(msg[v].getSender_id());
		if(personDAO.is_professor(pessoa.getId())) {
			nome=pessoa.getSurname();
		}else {
			nome=pessoa.getFirst_name();
		}
		contents += "<a href=\"http://localhost:4567/message/one/" + msg[v].getId() + "\" style=\"text-decoration: none\">" 
				   +"<div class=\"card-ex\" style=\"width: 100%;height: fit-content;margin: 15px 0px\">\n" 
				   +"<div class=\"card-title\" style=\"background-color: #3498DB\">\n" 
				   +"<h1 class=\"card-title\" style=\"color:black; background-color: transparent\">Mensagem de : " +nome+"</h1>\n" 
				   +"<h2 class=\"card-title\" style=\"color:#F4F6F6; background-color: transparent\">Data: " + msg[v].getDateAsStringDisplay()+"</h2>\n" 
				   +"</div>\n" 
				   +"</div>\n"
				   +"</a>";
		}
		
		for(int v = 0;msg2!=null&&v<msg2.length;v++) {
			Person pessoaTu = (Person) personDAO.get_by_id(msg2[v].getAddresee_id());
			if(personDAO.is_professor(pessoaTu.getId())) {
				nome=pessoaTu.getSurname();
			}else {
				nome=pessoaTu.getFirst_name();
			}
	    mycontent += "<a href=\"http://localhost:4567/message/edit/" + msg2[v].getId() + "\" style=\"text-decoration: none\">"  
    		 	    +"<div class=\"card-ex\" style=\"width: 100%;height: fit-content;margin: 15px 0px\">\n" 
    		        +"<div class=\"card-title\" style=\"background-color: #3498DB\">\n" 
    		        +"<h1 class=\"card-title\" style=\"color:black; background-color: transparent\">Mensagem sua para : " +nome+"</h1>\n" 
    		        +"<h2 class=\"card-title\" style=\"color:#F4F6F6; background-color: transparent\">Data: " + msg2[v].getDateAsStringDisplay()+"</h2>\n" 
    		        +"</div>\n" 
    		        +"</div>\n"
    		        +"</a>";
		}
		String body= "";
		body+=   "<!DOCTYPE html>\n" 
				+"<html lang=\"pt-br\">\n" 
				+"\n" 
				+"<head>\n" 
				+"<title>FOCUS - Educação mais acessível</title>\n" 
				+"<meta charset=\"utf-8\">\n" 
				+"<meta name=\"viewport\" content=\"width=device-width, initial-scale=1, shrink-to-fit=no\">\n" 
				+"<script src=\"https://kit.fontawesome.com/37e4898af2.js\" crossorigin=\"anonymous\"></script>\n" 
				+"<link rel=\"stylesheet\" href=\"/styles/style-main.css\">\n" 
				+"<link rel=\"stylesheet\" href=\"/styles/style-mensagens.css\">\n" 
				+"\n" 
				+"</head>\n" 
				+"\n" 
				+"<body>\n"
				+"<script type=\"text/javascript\">"
				+"function addIdToPath(form_name, base_url){\n" 
				+"var your_form = document.getElementById(form_name);\n" 
				+"var id = your_form.elements.namedItem(\"id\").value;\n" 
				+"action_src = base_url + id;\n"
				+"your_form.action = action_src;\n" 
				+"}"
				+"</script>" 
				+"<header>\n" 
				+"<nav class=\"nav-top\">\n" 
				+"<div>\n" 
				+"<div class=\"nav-top-expand\">\n" 
				+"<button id=\"openSideBar\"><i class=\"fa-solid fa-bars\"></i></button>\n" 
				+"<input type=\"checkbox\" class=\"none\">\n" 
				+"</div>\n" 
				+"<h1 class=\"nav-top-logo\">FOCUS</h1>\n" 
				+"</div>\n" 
				+"<div>\n" 
				+"<input type=\"text\" class=\"nav-top-input\" placeholder=\"Search for a keyword\" id=\"searchInputId\">\n" 
				+"<a href=\"/\"><i class=\"fa-solid fa-arrow-right-from-bracket icon-l\"></i></a>\n" 
				+"</div>\n" 
				+"</nav>\n" 
				+"</header>\n" 
				+"\n" 
				+"<main class=\"main\">\n" 
				+"<aside class=\"aside-bar\">\n" 
				+"<a href=\"/main\"><div><i class=\"fa-solid fa-stopwatch icon\"></i><h1 class=\"aside-option\">Pendências</h1></div></a>\n" 
				+"<a href=\"/content\"><div><i class=\"fa-solid fa-file-alt icon\"></i><h1 class=\"aside-option\">Conteúdos</h1></div></a>\n" 
				+"<a href=\"/activity\"><div><i class=\"fa-solid fa-pencil-alt icon\"></i><h1 class=\"aside-option\">Atividades</h1></div></a>\n" 
				+"<a href=\"/message\"><div><i class=\"fa-solid fa-envelope icon\"></i><h1 class=\"aside-option\">Mensagens</h1></div></a>\n" 
				+"</aside>\n" 
				+"<div style=\"position: relative; width: 80%; left: 230px; top: 20px\">" 
				+"<div id=\"botoes\">\n" 
				+"<a href=\"http://localhost:4567/message/cre/" + addresseid + "\">"
				+"<button style=\"background: green !important; color: white;\"><i class=\"fa-solid fa-plus\"></i> Nova mensagem</button>"
				+"</a>"
				+"</div>\n" 
				+"<div id=\"mensagensRecebidas\" style=\"width:100%\">" 
				+"<div class=\"div-title\">"
                +"<h1 class=\"text-center ex-title\" id=\"titulo\">"
                +"Mensagens recebidas"
                +"</h1>"
                +"</div>"
				+ contents 
				+"</div>\n" 
				+"<div id=\"mensagensEnviadas\" style=\"width:100%\">" 
				+"<div class=\"div-title\">"
                +"<h1 class=\"text-center ex-title\" id=\"titulo\">"
                +"Mensagens enviadas"
                +"</h1>"
                +"</div>"
				+ mycontent 
				+"</div>\n" 
				+"</div>\n" 
				+"</main>\n" 
				+"<script src=\"js/scriptsAtividade.js\"></script>\n" 
				+"</body>\n" 
				+"\n" 
				+"</html>";
		response.body(body);
		return response.body();
	}
}
