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
        String entries = Files.readString(Path.of("src/main/resources/EntryLog.txt"), StandardCharsets.UTF_8);
        //method to extract data from entries



        model.addAttribute("mood", mood);
        model.addAttribute("title", title);
        model.addAttribute("date", date);
        model.addAttribute("message", message);
        return "entries";
    }




    public void writeToFile(String date, String title, String mood, String message) {
        try {
            FileWriter myWriter = new FileWriter("src/main/resources/EntryLog.txt", true);
            myWriter.write(
                    "{\n" +
                            "date : " + date + "\n" +
                            "title : " + title + "\n" +
                            "mood : " + mood + "\n" +
                            "log : " + message + "\n" +
                            "}\n\n"
            );
            myWriter.close();
            System.out.println("Successfully wrote to the file.");
        } catch (IOException e) {
            System.out.println("An error occurred.");
            e.printStackTrace();
        }
    }


}
