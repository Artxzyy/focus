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
        post("/user", (req, res) -> ss.add(req, res));
        post("/main", (req, res) -> ps.login(req, res));
        get("/content/see/:id", (req, res) -> cs.see(req, res));
    }
}
