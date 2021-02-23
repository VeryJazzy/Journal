package journalProject;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@Controller
public class SecurityController {

    @GetMapping("/login")
    public String login() {
        return "loginPage";
    }

    @GetMapping("/customLogout")
    public String logout(HttpServletResponse response, HttpServletRequest request) {
        request.getSession(true).invalidate();
        return "loginPage";
    }







}
