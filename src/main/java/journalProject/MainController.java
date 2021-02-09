package journalProject;

import journalProject.Database.Dao;
import journalProject.Database.Entry;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.io.IOException;
import java.time.LocalDate;
import java.util.*;

@Controller
public class MainController {

    private String query = "";

    @Autowired
    Dao database;

    @PostMapping("/sendForm")
    public String handleForm(@RequestParam(name = "user_date") String date, @RequestParam(name = "user_title") String title, @RequestParam(name = "user_message") String message) {
        String id = UUID.randomUUID().toString();

        Entry entry = new Entry(id, date, title, message);
        database.add(entry);
        return "redirect:/entries";
    }

    @PostMapping("/search")
    public String search(@RequestParam("searched") String searched, Model model) { // idk if model works here
        query = " WHERE MATCH(title, message) AGAINST('+" + searched + "' IN BOOLEAN MODE)";
        List<Entry> entryList = database.getEntries(query);
        if (entryList.isEmpty()) {
            model.addAttribute("message","no entries for this search");
            return "entries";
        }
        model.addAttribute("entries", entryList);
        return "entries";
    }

    @PostMapping("/dateSearch")
    public String dateSearch(@RequestParam("searchStart") String start, @RequestParam("searchEnd") String end, Model model) {
        if (end == "") {
            end = LocalDate.now().toString();
        }
        query = " where date between '" + start + "' and '" + end + "'";
        List<Entry> entryList = database.getEntries(query);
        model.addAttribute("entries", entryList);
        return "entries";
    }


    @GetMapping("/deleteEntry/{id}")
    public String deleteEntry(@PathVariable("id") String id) {
        database.delete(id);
        return "redirect:/entries";
    }

    @GetMapping("/entries")
    public String greeting(Model model) throws IOException {
        query = "";
        List<Entry> entryList = database.getEntries(query);
        model.addAttribute("entries", entryList);
        return "entries";
    }

    @GetMapping("/sortNewest")
    public String sortDateNewest(Model model) throws IOException {
        List<Entry> entryList = database.getEntries(query + " ORDER BY date DESC");
        model.addAttribute("entries", entryList);
        return "entries";
    }

    @GetMapping("/sortOldest")
    public String sortDateOldest(Model model) throws IOException {
        List<Entry> entryList = database.getEntries(query + " ORDER BY date");
        model.addAttribute("entries", entryList);
        return "entries";
    }



    //make these one mapping
}
