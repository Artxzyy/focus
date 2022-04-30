package server;
import dao.StudentDAO;
import dao.ContentDAO;
import model.Student;
import spark.Request;
import spark.Response;
import model.Content;
public class ContentService {
	StudentDAO studentDAO = new StudentDAO();
	ContentDAO contentDAO = new ContentDAO();
	
	public Object see(Request req, Response res) {
		Content content = null;
		try {
			int id = Integer.parseInt(req.params(":id"));
			content = contentDAO.get(id);
			if(content == null) throw new NullPointerException();
			String body = "";
			body += ""
					+ "<!DOCTYPE html>\n" + 
					"<html lang=\"pt-br\">\n" + 
					"\n" + 
					"<head>\n" + 
					"    <title>FOCUS - Educação mais acessível</title>\n" + 
					"    <meta charset=\"utf-8\">\n" + 
					"    <meta name=\"viewport\" content=\"width=device-width, initial-scale=1, shrink-to-fit=no\">\n" + 
					"    <script src=\"https://kit.fontawesome.com/37e4898af2.js\" crossorigin=\"anonymous\"></script>\n" + 
					"    <link rel=\"stylesheet\" href=\"css/style-main.css\">\n" + 
					"</head>\n" + 
					"\n" + 
					"<body>\n" + 
					"    <header>\n" + 
					"        <nav class=\"nav-top\">\n" + 
					"          <div>\n" + 
					"            <div class=\"nav-top-expand\">\n" + 
					"              <button id=\"openSideBar\"><i class=\"fa-solid fa-bars\"></i></button>\n" + 
					"              <input type=\"checkbox\" class=\"none\">\n" + 
					"            </div>\n" + 
					"            <h1 class=\"nav-top-logo\">FOCUS</h1>\n" + 
					"          </div>\n" + 
					"          <div>\n" + 
					"            <input type=\"text\" class=\"nav-top-input\" placeholder=\"Search for a keyword\" id=\"searchInputId\">\n" + 
					"            <a href=\"index.html\"><i class=\"fa-solid fa-arrow-right-from-bracket icon-l\"></i></a>\n" + 
					"          </div>\n" + 
					"        </nav>\n" + 
					"      </header>\n" + 
					"    \n" + 
					"      <main class=\"main\">\n" + 
					"          <aside class=\"aside-bar\">\n" + 
					"                <a href=\"main.html\"><div><i class=\"fa-solid fa-stopwatch icon\"></i><h1 class=\"aside-option\">Pendências</h1></div></a>\n" + 
					"                <a href=\"conteudo.html\"><div><i class=\"fa-solid fa-file-alt icon\"></i><h1 class=\"aside-option\">Conteúdos</h1></div></a>\n" + 
					"                <a href=\"atividade.html\"><div><i class=\"fa-solid fa-pencil-alt icon\"></i><h1 class=\"aside-option\">Atividades</h1></div></a>\n" + 
					"                <a href=\"mensagem.html\"><div><i class=\"fa-solid fa-envelope icon\"></i><h1 class=\"aside-option\">Mensagens</h1></div></a>\n" + 
					"          </aside>\n" + 
					"          \n" + 
					"          <article id=\"tela\" class=\"content\">\n" + 
					"            <section class=\"main conteudo\">\n" + 
					"                    <div id=\"professorApenas\">\n" + 
					"                        <h2>\n" + 
					"                            Escolha o tipo de conteúdo desejado\n" + 
					"                        </h2>\n" + 
					"        \n" + 
					"                        <select id=\"tipoMaterial\" class=\"dropdown-toggle\">\n" + 
					"                            <option hidden selected disabled>Selecione o tipo:</option>\n" + 
					"                            <option value=\"texto\">Texto</option>\n" + 
					"                            <option value=\"imagem\">Imagem</option>\n" + 
					"                            <option value=\"video\">Video</option>\n" + 
					"                        </select>\n" + 
					"                        <button id=\"btnConteudo\">Ok</button>\n" + 
					"                    </div>\n" + 
					"                    <div class=\"content-div\" id=\"conteudoColocado\">\n" + 
					"                        <!--Aqui fica a parte de colocar novo conteudo,criada dinamicamente-->\n" + 
					"                    </div>\n" + 
					"                    <div class=\"d-block\">\n" + 
					"                        <select id=\"conteudoVer\" class=\"dropdown-toggle\">\n" + 
					"                            <option value=3 hidden selected disabled>Selecione o conteúdo a ver:</option>\n" + 
					"                        </select>\n" + 
					"                        <button id=\"btnConteudoMostrar\" class=\"btn btn-secondary\">Visualizar conteúdo</button>\n" + 
					"                    </div>\n" + 
					"                    <div id=\"conteudoPostado\">\n" + 
					"                        <!--Vou jogar os conteudos criados aqui dentro-->\n" + 
					"                    </div>\n" + 
					"            </section>\n" + 
					"\n" + 
					"          </article>\n" + 
					"      </main>\n" + 
					"    <script src=\"js/scriptsConteudo.js\"></script>\n" + 
					"</body>\n" + 
					"\n" + 
					"</html>";
			res.body(body);
		}catch(Exception e) {
			
		}
		return res.body();
	}
}
