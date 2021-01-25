package journalProject;


import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;


import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Locale;


@SpringBootApplication

@Controller
public class JournalApplication {

    public static void main(String[] args) {
        SpringApplication.run(JournalApplication.class, args);
    }

    @PostMapping("/sendForm")
    public String handleForm(@RequestParam(name = "user_date") String date, @RequestParam(name = "user_title") String title, @RequestParam(name = "user_mood") String mood, @RequestParam(name = "user_message") String message) {
        writeToFile(date, title, mood, message);
        return "index-formSent.html";
    }

    @GetMapping("/entries")
    public String greeting(Model model) throws IOException {
        String file = Files.readString(Path.of("src/main/resources/EntryLog.txt"), StandardCharsets.UTF_8);
        String[] entries = file.split("\n\n");

        ArrayList<Entry> entryList = new ArrayList<>();
        for (String anEntry : entries) {
            String[] values = anEntry.split("\n");
            Entry entry = new Entry(values[0],values[1],values[2],values[3]);
            entryList.add(entry);
        }
        model.addAttribute("entries", entryList);


        return "entries";
    }


//    String[] values = entries[0].split("\n");
//
//    ArrayList<String> entryArr = new ArrayList<>();
//        entryArr.add(values[0]);
//        entryArr.add(values[1]);
//        entryArr.add(values[2]);
//        entryArr.add(values[3]);
//        model.addAttribute("entry", entryArr);


//            model.addAttribute("date", values[0]);
//            model.addAttribute("title", values[1]);
//            model.addAttribute("mood", values[2]);
//            model.addAttribute("message", values[3]);


    public void writeToFile(String date, String title, String mood, String message) {
        try {
            FileWriter myWriter = new FileWriter("src/main/resources/EntryLog.txt", true);
            myWriter.write(
                    date + "\n" +
                            title + "\n" +
                            mood + "\n" +
                            message + "\n\n"
            );
            myWriter.close();
            System.out.println("Successfully wrote to the file.");
        } catch (IOException e) {
            System.out.println("An error occurred.");
            e.printStackTrace();
        }
    }


}
