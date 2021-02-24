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
        boolean registerSuccessful = database.registerNewUser(username, password);
        if (!registerSuccessful) {
            return "redirect:/createNewUser?userAlreadyExists";
        }
        System.out.println("user created");
        return "redirect:/login?userCreated";
    }

    @GetMapping("/createNewUser")
    public String createNewUser() {
        return "registerUser.html";
    }





}
