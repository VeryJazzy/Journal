package journalProject.Database;

import java.time.LocalDate;
import java.util.List;

public class Sort {

    private static String query = "";

    public static List<Entry> BooleanSearch(Dao database, String searched) {
        query = " WHERE MATCH(title, message) AGAINST('+" + searched + "' IN BOOLEAN MODE)";
        return database.getEntries(query);
    }

    public static List<Entry> searchDates(Dao database, String from, String to) {
        if (to.equals("")) {
            to = LocalDate.now().toString();
        }
        query = " WHERE date BETWEEN '" + from + "' and '" + to + "'";
        return database.getEntries(query);
    }

    public static List<Entry> sortBy(Dao database, String order) {
        switch (order) {
            case "NEWEST" -> {
                return database.getEntries(query + " ORDER BY date DESC");
            }
            case "OLDEST" -> {
                return database.getEntries(query + " ORDER BY date");
            }
        }
        return null;
    }

}

