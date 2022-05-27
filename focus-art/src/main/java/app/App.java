package app;
// test
import static spark.Spark.*;
import server.StudentService;
import server.MessageService;
import server.ActivityService;
import server.ContentService;
import server.PersonService;
public class App {
	public static StudentService ss = new StudentService();
	public static PersonService ps = new PersonService();
	public static ContentService cs = new ContentService();
	public static ActivityService as = new ActivityService();
	private static MessageService ms= new MessageService();
    public static void main(String[] args) {
    	staticFiles.location("/public");
    	port(4567);
    	get("/content/see/:id", (req, res) -> cs.see(req, res));
    	get("/activity/see/:id", (req, res) -> as.see(req, res));
    	post("/activity/see/validate", (req, res) -> as.validate(req, res));
    	post("/activity/see/next", (req, res) -> as.next(req, res));
    	get("/activity/:id", (req, res) -> as.see_all(req, res));
    	get("/activity/create/:id", (req, res) -> as.create(req, res));
    	post("/create", (req, res) -> as.add(req, res));
    	post("/activity/update/validate", (req, res) -> as.change(req, res));
    	post("/activity/update/form", (req, res) -> as.update(req, res));
    	post("/activity/delete", (req, res) -> as.delete(req, res));
    	post("/update", (req, res) -> as.change(req, res));
        get("/content/see/:id", (req, res) -> cs.see(req, res));
        get("/content/check_user_type/:id", (req, res) -> cs.checkUserType(req, res));
        post("/content/insert", (req, res) -> cs.insert(req, res));
        get("/content/update/:id", (req, res) -> cs.update(req, res));
        post("/content/update", (req, res) -> cs.updateSave(req, res));
        get("/content/delete/:id", (req, res) -> cs.delete(req, res));
        post("/main", (req, res) -> ps.login(req, res));
        post("/register/validate", (req, res) -> ss.add(req, res));
        get("/message/one/:msgid",(request,response)->ms.see1Msg(request, response));
		get("/message/:id",(request,response)->ms.seeAllMsg(request, response));
		get("/message/edit/:msgid",(request,response)->ms.editMsg(request, response));
		get("/message/upd/:msgid",(request,response)->ms.updateMsg(request,response));
		get("/message/cre/:senderid",(request,response)->ms.createMsg(request,response));
		get("/message/ate/:senderid",(request,response)->ms.sendMsg(request,response));
    }
}
