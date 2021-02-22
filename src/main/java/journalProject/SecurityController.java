package journalProject;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.CookieValue;
import org.springframework.web.bind.annotation.GetMapping;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletResponse;

@Controller
public class SecurityController {

    @GetMapping("/login")
    public String login() {
        return "loginPage";
    }

    @GetMapping("/customLogout")
    public String logout(HttpServletResponse response) {
        Cookie wipeSession = new Cookie("JSESSIONID", null);
        wipeSession.setMaxAge(0);
        response.addCookie(wipeSession);
        return "loginPage";
    }







}
