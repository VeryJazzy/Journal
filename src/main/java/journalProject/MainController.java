package journalProject;

import journalProject.Database.Dao;
import journalProject.Database.Entry;
import journalProject.Database.Sort;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

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
    public String greeting(Model model) throws IOException {
        model.addAttribute("entries", database.getEntries(""));
        return "entries";
    }

    @PostMapping("/search")
    public String search(@RequestParam("searched") String searched, Model model) { // idk if model works here
        List<Entry> entryList = Sort.BooleanSearch(database, searched);
        if (entryList.isEmpty()) {
            model.addAttribute("message", "no entries for this search");
            return "entries";
        }
        model.addAttribute("entries", entryList);
        return "entries";
    }

    @PostMapping("/dateSearch")
    public String dateSearch(@RequestParam("searchStart") String from, @RequestParam("searchEnd") String to, Model model) {
        model.addAttribute("entries", Sort.searchDates(database, from, to));
        return "entries";
    }

    @RequestMapping("/sort")
    public String sortDate(@RequestParam("order") String order, Model model) throws IOException {
        model.addAttribute("entries", Sort.sortBy(database, order));
        return "entries";
    }

}
