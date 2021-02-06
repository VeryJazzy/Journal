package journalProject.Database;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;

public class JsonDao implements Dao {

    @Override
    public void add(Entry entry) {
        JSONObject jsonToBeAdded = createJsonEntry(entry);
        JSONObject entriesObj = getEntriesObj();
        JSONArray jsonArray = (JSONArray) entriesObj.get("entries");
        jsonArray.put(jsonToBeAdded);
        entriesObj.put("entries", jsonArray);

        update(entriesObj);
    }

    @Override
    public void delete(String id) {
        JSONObject entriesObj = getEntriesObj();
        JSONArray entriesAsList = (JSONArray) entriesObj.get("entries");
        entriesAsList.remove(findIdIndex(entriesAsList, id));

        update(entriesObj);
    }

    @Override
    public ArrayList<Entry> getEntries() {
        ArrayList<Entry> entryList = new ArrayList<>();
        JSONObject entriesObj = getEntriesObj();
        JSONArray entriesAsList = (JSONArray) entriesObj.get("entries");

        for (Object anEntry : entriesAsList) {
            JSONObject jsonEntry = (JSONObject) anEntry;
            String id = (String) jsonEntry.get("id");
            String date = (String) jsonEntry.get("date");
            String title = (String) jsonEntry.get("title");
            String message = (String) jsonEntry.get("message");

            Entry completeEntry = new Entry(id, date, title, message);
            entryList.add(completeEntry);
        }
        return entryList;
    }

    private void update(JSONObject entriesObj) {
        try {
            FileWriter myWriter = new FileWriter("src/main/resources/Log.json", false);
            myWriter.write(entriesObj.toString());
            myWriter.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public int findIdIndex(JSONArray entriesAsList, String id) {
        for (int i = 0; i < entriesAsList.length(); i++) {
            JSONObject entry = (JSONObject) entriesAsList.get(i);
            if (entry.get("id").equals(id)) {
                return i;
            }
        }
        return -1;
    }

    private JSONObject createJsonEntry(Entry entry) {
        JSONObject obj = new JSONObject();
        obj.put("id", entry.getId());
        obj.put("date", entry.getDate());
        obj.put("title", entry.getTitle());
        obj.put("message", entry.getMessage());
        return obj;
    }

    private JSONObject getEntriesObj() {
        JSONObject entriesObj = null;
        try {
            entriesObj = new JSONObject(Files.readString(Path.of("src/main/resources/Log.json")));
        } catch (Exception e) {
            e.printStackTrace();
        }
        return entriesObj;
    }
}
