package app;

import static spark.Spark.*;
import server.StudentService;
import server.ContentService;
import server.PersonService;
public class App {
	public static StudentService ss = new StudentService();
	public static PersonService ps = new PersonService();
	public static ContentService cs = new ContentService();
    public static void main(String[] args) {
    	staticFiles.location("/public");
    	port(4567);
        post("/user", (req, res) -> ss.add(req, res));
        post("/main", (req, res) -> ps.login(req, res));
        get("/content/see/:id", (req, res) -> cs.see(req, res));
        get("/content/check_user_type/:id", (req, res) -> cs.checkUserType(req, res));
        post("/content/insert", (req, res) -> cs.insert(req, res));
        get("/content/update/:id", (req, res) -> cs.update(req, res));
        post("/content/update", (req, res) -> cs.updateSave(req, res));
        get("/content/delete/:id", (req, res) -> cs.delete(req, res));
    }
}
