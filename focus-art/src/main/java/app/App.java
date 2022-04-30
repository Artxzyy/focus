package app;

import static spark.Spark.*;
import server.StudentService;
import server.ContentService;
public class App {
	public static StudentService ss = new StudentService();
	public static ContentService cs = new ContentService();
    public static void main(String[] args) {
    	get("/", (req, res) -> "");
        get("/hello", (req, res) -> "STATUS: " + res.status() + " | Hello World");
        post("/user", (req, res) -> ss.add(req, res));
        post("/main", (req, res) -> ss.login(req, res));
        get("/content/see/:id", (req, res) -> cs.see(req, res));
    }
}
