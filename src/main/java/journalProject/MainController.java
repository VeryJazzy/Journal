package journalProject;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.io.IOException;
import java.util.List;
import java.util.UUID;

@Controller
public class MainController {

    @Autowired
    Dao database;


    @PostMapping("/sendForm")
    public String handleForm(@RequestParam(name = "user_date") String date, @RequestParam(name = "user_title") String title, @RequestParam(name = "user_message") String message) {
        date = formatDate(date);
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
        List<Entry> entryList = database.getEntries();
        model.addAttribute("entries", entryList);
        return "entries";
    }

    private String formatDate(String date) {
        String formattedDate = date.substring(8) + "/" + date.substring(5, 7) + "/" + date.substring(0, 4);
        return formattedDate.toString();
    }
}
