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
<<<<<<< HEAD
    	get("/content/see/:id", (req, res) -> cs.see(req, res));
    	get("/activity/see/:id", (req, res) -> as.see(req, res));
    	get("/activity/:id", (req, res) -> as.see_all(req, res));
    	get("/activity/create/:id", (req, res) -> as.create(req, res));
    	post("/create", (req, res) -> as.add(req, res));
    	get("/activity/update/:id", (req, res) -> as.update(req, res));
    	post("/update", (req, res) -> as.change(req, res));
=======
    	port(4567);
>>>>>>> branch 'main' of git@github.com:Artxzyy/focus.git
        post("/user", (req, res) -> ss.add(req, res));
        post("/main", (req, res) -> ps.login(req, res));
<<<<<<< HEAD
=======
        get("/content/see/:id", (req, res) -> cs.see(req, res));
        get("/content/check_user_type/:id", (req, res) -> cs.checkUserType(req, res));
        post("/content/insert", (req, res) -> cs.insert(req, res));
        get("/content/update/:id", (req, res) -> cs.update(req, res));
        post("/content/update", (req, res) -> cs.updateSave(req, res));
        get("/content/delete/:id", (req, res) -> cs.delete(req, res));
>>>>>>> branch 'main' of git@github.com:Artxzyy/focus.git
    }
}
