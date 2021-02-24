package journalProject;

import journalProject.Database.Dao;
import journalProject.Database.Entry;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.time.LocalDate;
import java.util.*;

@Controller
public class MainController {

    @Autowired
    Dao database;

    @GetMapping("/error")
    public String error() {
        System.out.println("errorBaby");
        return "error";
    }

    @PostMapping("/sendForm")
    public String handleForm(@RequestParam(name = "user_date") String date, @RequestParam(name = "user_title") String title, @RequestParam(name = "user_message") String message) {
        String id = UUID.randomUUID().toString();
        Entry entry = new Entry(id, date, title, message);
        database.add(entry);
        return "redirect:/entries";
    }

    @GetMapping("/deleteEntry/{id}")
    public String deleteEntry(@PathVariable("id") String id) {
        database.delete(id);
        return "redirect:/entries";
    }

    @GetMapping("/entries")
    public String greeting(Model model, HttpServletResponse response) throws IOException {
        response.addCookie(new Cookie("searchTerms", ""));
        response.addCookie(new Cookie("searchDateFrom", ""));
        response.addCookie(new Cookie("searchDateTo", ""));

        model.addAttribute("entries", database.getEntries("", "", "", ""));
        return "entries";
    }


    @PostMapping("/search")
    public String search(@RequestParam("searchTerms") String searched, Model model, HttpServletResponse response) {
        response.addCookie(new Cookie("searchTerms", URLEncoder.encode(searched, StandardCharsets.UTF_8)));

        List<Entry> entryList = database.getEntries(searched, "", "", "");

        if (entryList.isEmpty()) {
            model.addAttribute("message", "no entries for this search");
            return "entries";
        }
        model.addAttribute("entries", entryList);
        return "entries";
    }

    @PostMapping("/dateSearch")
    public String dateSearch(@RequestParam("searchFrom") String from, @RequestParam("searchTo") String to, Model model, HttpServletResponse response) {
        if (to.equals("")) {
            to = LocalDate.now().toString();
        }
        response.addCookie(new Cookie("searchDateFrom", URLEncoder.encode(from, StandardCharsets.UTF_8)));
        response.addCookie(new Cookie("searchDateTo", URLEncoder.encode(to, StandardCharsets.UTF_8)));
        response.addCookie(new Cookie("searchTerms", ""));


        model.addAttribute("entries", database.getEntries("", from, to, ""));
        return "entries";
    }

    @RequestMapping("/sort")
    public String sortDate(Model model,
                           @RequestParam("order") String order,
                           @CookieValue("searchTerms") Cookie searchTerms,
                           @CookieValue("searchDateFrom") Cookie searchFrom,
                           @CookieValue("searchDateTo") Cookie searchTo,
                           HttpServletResponse response) throws IOException {
        List<Entry> entryList = database.getEntries(searchTerms.getValue(), searchFrom.getValue(), searchTo.getValue(), order);
        model.addAttribute("entries", entryList);
        return "entries";
    }

}
