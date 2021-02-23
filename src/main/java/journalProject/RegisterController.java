package journalProject;

import journalProject.Database.Dao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class RegisterController {

    @Autowired
    Dao database;

    @PostMapping("/register")
    public String registerNewUser(@RequestParam(name = "username") String username, @RequestParam(name = "password") String password) {
        boolean registered = database.registerNewUser(username, password);
        if (!registered) {
            System.out.println("user already exists");
            return "redirect:/login?userAlreadyExists";
        }
        return "redirect:/index.html";
    }

    @GetMapping("/createNewUser")
    public String createNewUser() {
        return "createNewUser.html";
    }





}
