package app;

import static spark.Spark.*;
import server.StudentService;
import server.ActivityService;
import server.ContentService;
import server.PersonService;
public class App {
	public static StudentService ss = new StudentService();
	public static PersonService ps = new PersonService();
	public static ContentService cs = new ContentService();
	public static ActivityService as = new ActivityService();
    public static void main(String[] args) {
    	staticFiles.location("/public");
    	get("/content/see/:id", (req, res) -> cs.see(req, res));
    	get("/activity/see/:id", (req, res) -> as.see(req, res));
    	get("/activity/:id", (req, res) -> as.see_all(req, res));
    	get("/activity/create/:id", (req, res) -> as.create(req, res));
    	post("/create", (req, res) -> as.add(req, res));
    	get("/activity/update/:id", (req, res) -> as.update(req, res));
    	post("/update", (req, res) -> as.change(req, res));
        post("/user", (req, res) -> ss.add(req, res));
        post("/main", (req, res) -> ps.login(req, res));
    }
}
