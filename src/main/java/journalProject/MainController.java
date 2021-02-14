package journalProject;

import journalProject.Database.Dao;
import journalProject.Database.Entry;
import journalProject.Database.Sort;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.*;

@Controller
public class MainController {

    @Autowired
    Dao database;

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

        //clears all cookies
        Cookie searchCookie = new Cookie("SearchQuery", "");
        searchCookie.setMaxAge(60 * 60 * 24);
        response.addCookie(searchCookie);

        Cookie fromCookie = new Cookie("SearchFrom", "");
        fromCookie.setMaxAge(60 * 60 * 24);
        response.addCookie(fromCookie);

        Cookie toCookie = new Cookie("SearchTo", "");
        toCookie.setMaxAge(60 * 60 * 24);
        response.addCookie(toCookie);

        model.addAttribute("entries", database.getEntries(""));
        return "entries";
    }

    @PostMapping("/search")
    public String search(@RequestParam("searched") String searched, Model model, HttpServletResponse response) { // idk if model works here
        List<Entry> entryList = Sort.BooleanSearch(database, searched, response);
        if (entryList.isEmpty()) {
            model.addAttribute("message", "no entries for this search");
            return "entries";
        }
        model.addAttribute("entries", entryList);
        return "entries";
    }

    @PostMapping("/dateSearch")
    public String dateSearch(@RequestParam("searchStart") String from, @RequestParam("searchEnd") String to, Model model, HttpServletResponse response) {
        model.addAttribute("entries", Sort.searchDates(database, from, to, response));
        return "entries";
    }

    @RequestMapping("/sort")
    public String sortDate(Model model,
                           @RequestParam("order") String order,
                           @CookieValue("SearchQuery") Cookie searchQuery,
                           @CookieValue("SearchFrom") Cookie searchFrom,
                           @CookieValue("SearchTo") Cookie searchTo) throws IOException {

        model.addAttribute("entries", Sort.sortBy(database, order, searchQuery, searchFrom, searchTo));
        return "entries";
    }

}
