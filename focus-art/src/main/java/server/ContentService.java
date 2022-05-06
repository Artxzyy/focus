package server;

import java.io.File;
import java.util.Scanner;
import dao.ContentDAO;
import dao.StudentDAO;
import spark.Request;
import spark.Response;
import model.Content;

public class ContentService {
	StudentDAO studentDAO = new StudentDAO();
	ContentDAO contentDAO = new ContentDAO();
	
	private String page;
	private String dropdown;
	
	public void showPage() {
		page = "";
		try {
			Scanner entrada = new Scanner(new File("src/main/resources/public/conteudo.html"));
			while(entrada.hasNext()) {
				page += (entrada.nextLine() + "\n");
			}
			entrada.close();
		} catch (Exception e) {System.out.println(e.getMessage());}
	}
	
	public void showEditPage() {
		page = "";
		try {
			Scanner entrada = new Scanner(new File("src/main/resources/public/editarConteudo.html"));
			while(entrada.hasNext()) {
				page += (entrada.nextLine() + "\n");
			}
			entrada.close();
		} catch (Exception e) {System.out.println(e.getMessage());}
	}
	
	public void showContentByID(Content content) {
		String titulo = content.getTitle();
		String disciplina = content.getSubject();
		String materia = content.getTheme();
		String texto = content.getText();
		
		page = page.replace("<TITULO>", titulo);
		page = page.replace("<DISCIPLINA>", disciplina);
		page = page.replace("<MATERIA>", materia);
		page = page.replace("<TEXTO>", texto);
	}
	
	public void showContentToEdit(Content content) {
		String id = Integer.toString(content.getId());
		String professor_id = Integer.toString(content.getProfessor_id());
		String titulo = content.getTitle();
		String disciplina = content.getSubject();
		String materia = content.getTheme();
		String texto = content.getText();
		
		page = page.replace("content-ID", id);
		page = page.replace("teacher-ID", professor_id);
		page = page.replace("TITULO", titulo);
		page = page.replace("DISCIPLINA", disciplina);
		page = page.replace("MATERIA", materia);
		page = page.replace("<TEXTO>", texto);
	}
	
	public void getDropdown() {
		Content[] content = contentDAO.getContents();
		
		if(content != null) {
			dropdown = "";
			for(Content c : content) 
			{
				dropdown += "" + "<option value=" + c.getId() + ">" + c.getTitle() + "</option>" + "</a>\n";
			}
			page = page.replace("<OPCOES>", dropdown);
		}
		
	}
	
	public Object see(Request request, Response response) {
		int id = Integer.parseInt(request.params(":id"));
		Content content = contentDAO.get(id);
		
		if(content != null) {
			response.status(200);
			showPage();
			getDropdown();
			showContentByID(content);
		} else {
			response.status(404);
		}
		
		return page;
	}
	
	public Object insert(Request request, Response response) {
		String resp = "";
		int professorID = Integer.parseInt(request.queryParams("professorID"));
		String disciplina = request.queryParams("disciplina");
		String materia = request.queryParams("materia");
		String titulo = request.queryParams("titulo");
		String texto = request.queryParams("texto");
		
		Content content = new Content(professorID, titulo, disciplina, materia, texto);
		
		if (disciplina.isEmpty() || materia.isEmpty() || titulo.isEmpty() || texto.isEmpty()) {
			resp = "Por favor, preencha todos os campos";
		} else {
			if(contentDAO.insert(content) == true) {
				resp = "Conteudo (" + titulo + ") inserido!";
				response.status(201);
			} else if(contentDAO.insert(content) == false) {
				resp = "Conteudo (" + titulo + ") não inserido!";
				response.status(404);
			}			
		}
		
		return resp;
	}
	
	public Object update(Request request, Response response) {
		int id = Integer.parseInt(request.params(":id"));
		Content content = contentDAO.get(id);
		showEditPage();
		showContentToEdit(content);
		return page;
	}
	
	public Object updateSave(Request request, Response response) {
		String resp = "";
		int conteudoID = Integer.parseInt(request.queryParams("conteudoID"));
		int professorID = Integer.parseInt(request.queryParams("professorID"));
		String disciplina = request.queryParams("disciplina");
		String materia = request.queryParams("materia");
		String titulo = request.queryParams("titulo");
		String texto = request.queryParams("texto");		
		
		if (disciplina.isEmpty() || materia.isEmpty() || titulo.isEmpty() || texto.isEmpty()) {
			resp = "Por favor, preencha todos os campos";
		} else {
			Content content = new Content(professorID, conteudoID, titulo, disciplina, materia,texto);
			if(contentDAO.update(content) == true) {
				resp = "Conteudo (" + titulo + ") atualizado com sucesso!";
				response.status(201);
			} else if(contentDAO.insert(content) == false) {
				resp = "Houve um erro ao tentar atualizar o conteúdo " + titulo + ".";
				response.status(404);
			}			
		}
		
		return resp;
	}
	
	// tratar redirecionamento da página futuramente
	public Object delete(Request request, Response response) {
		 int id = Integer.parseInt(request.params(":id"));
		 String resp = "";
		 Content content = contentDAO.get(id);
		 
		 if(content != null) {
			 contentDAO.delete(id);
			 response.status(200);
			 resp = "Conteúdo excluído com sucesso!";
		 } else {
			 response.status(404);
			 resp = "Houve um erro ao tentar excluir este conteúdo. Tente novamente, mais tarde";
		 }
		 
		 return resp;
	}
	
	public String checkUserType(Request request, Response response) {
		String resp = "";
		int userID = Integer.parseInt(request.params(":id"));
		resp = contentDAO.checkUserType(userID);		
		return resp;
	}
}