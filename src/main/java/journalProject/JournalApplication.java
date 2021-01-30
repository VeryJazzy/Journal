package journalProject;

import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;


import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.UUID;

@SpringBootApplication

@Controller
public class JournalApplication {

    public static void main(String[] args) {
        SpringApplication.run(JournalApplication.class, args);
    }

    @PostMapping("/sendForm")
    public String handleForm(@RequestParam(name = "user_date") String date, @RequestParam(name = "user_title") String title, @RequestParam(name = "user_message") String message) {
        date = formatDate(date);
        JSONObject entryJSON = writeToJSONObj(date, title, message);
        writeToFile(entryJSON);
        return "redirect:/entries";
    }

    @GetMapping("/deleteEntry/{id}")
    public String deleteEntry(@PathVariable("id") String id) {
        try {
            JSONObject obj = new JSONObject(Files.readString(Path.of("src/main/resources/Log.json")));
            JSONArray entries = (JSONArray) obj.get("entries");
            entries.remove(findIdIndex(entries, id));

            FileWriter myWriter = new FileWriter("src/main/resources/Log.json", false);
            myWriter.write(obj.toString());
            myWriter.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "redirect:/entries";
    }

    public int findIdIndex(JSONArray entries, String id) {
        for (int i = 0; i < entries.length(); i++) {
            JSONObject entry = (JSONObject) entries.get(i);
            if (entry.get("id").equals(id)) {
                return i;
            }
        }
        return -1;
    }


    @GetMapping("/entries")
    public String greeting(Model model) throws IOException {
        ArrayList<Entry> entryList = new ArrayList<>();

        JSONObject jsonObject = new JSONObject(Files.readString(Path.of("src/main/resources/Log.json")));
        JSONArray jsonArray = (JSONArray) jsonObject.get("entries");

        for (Object obj : jsonArray) {
            JSONObject aJsonObject = (JSONObject) obj;
            String id = (String) aJsonObject.get("id");
            String date = (String) aJsonObject.get("date");
            String title = (String) aJsonObject.get("title");
            String message = (String) aJsonObject.get("message");
            Entry entry = new Entry(id, date, title, message);
            entryList.add(entry);
        }

        model.addAttribute("entries", entryList);
        return "entries";
    }



    private JSONObject writeToJSONObj(String date, String title, String message) {
        JSONObject obj = new JSONObject();
        obj.put("id", getIDNumber());
        obj.put("date", date);
        obj.put("title", title);
        obj.put("message", message);
        return obj;
    }

    private String getIDNumber() {
        return UUID.randomUUID().toString();
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




}
