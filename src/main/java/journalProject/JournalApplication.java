package journalProject;

import com.fasterxml.jackson.databind.util.JSONPObject;
import org.apache.tomcat.util.json.JSONParser;
import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;


import java.io.File;
import java.io.FileReader;
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
        date = formatDate(date);
        JSONObject entryJSON = writeToJSONObj(date, title, mood, message);
        writeToFile(entryJSON);
        return "index-formSent.html";
    }

    private JSONObject writeToJSONObj(String date, String title, String mood, String message) {
        JSONObject obj = new JSONObject();

        obj.put("id", getIDNumber());
        obj.put("date", date);
        obj.put("title", title);
        obj.put("mood", mood);
        obj.put("message", message);
        return obj;
    }

    private String getIDNumber() {
        JSONArray jsonArray = null;
        try {
            String jsonString = Files.readString(Path.of("src/main/resources/Log.json"));
            JSONObject obj = new JSONObject(jsonString);
            jsonArray = (JSONArray) obj.get("entries");

        } catch (Exception e) {
            e.printStackTrace();
        }
        return String.valueOf(jsonArray.length());
    }

    public void writeToFile(JSONObject toBeAdded) {
        try {
            JSONObject obj = new JSONObject(Files.readString(Path.of("src/main/resources/Log.json")));
            JSONArray jsonArray = (JSONArray) obj.get("entries");
            jsonArray.put(toBeAdded);
            obj.put("entries",jsonArray);
            FileWriter myWriter = new FileWriter("src/main/resources/Log.json", false);
            myWriter.write(obj.toString());
            myWriter.close();
        } catch (IOException e) {
            System.out.println("An error occurred.");
            e.printStackTrace();
        }
    }

    private String formatDate(String date) {
        // 2021-01-27
        // 27/01/2021
        String formattedDate = date.substring(8) + "/" + date.substring(5, 7) + "/" + date.substring(0, 4);
        return formattedDate.toString();
    }

    @GetMapping("/entries")
    public String greeting(Model model) throws IOException {
        String file = Files.readString(Path.of("src/main/resources/EntryLog.txt"), StandardCharsets.UTF_8);
        String[] entries = file.split(System.lineSeparator() + System.lineSeparator());

        ArrayList<Entry> entryList = new ArrayList<>();
        for (String anEntry : entries) {
            String[] values = anEntry.split(System.lineSeparator());
            Entry entry = new Entry(values[0],values[1],values[2],values[3]);
            entryList.add(entry);
        }
        model.addAttribute("entries", entryList);
        return "entries";
    }



//    @GetMapping("/entries")
//    public String greeting(Model model) throws IOException {
//        String file = Files.readString(Path.of("src/main/resources/EntryLog.txt"), StandardCharsets.UTF_8);
//        String[] entries = file.split(System.lineSeparator() + System.lineSeparator());
//
//        ArrayList<Entry> entryList = new ArrayList<>();
//        for (String anEntry : entries) {
//            String[] values = anEntry.split(System.lineSeparator());
//            Entry entry = new Entry(values[0],values[1],values[2],values[3]);
//            entryList.add(entry);
//        }
//        model.addAttribute("entries", entryList);
//        return "entries";
//    }


}
