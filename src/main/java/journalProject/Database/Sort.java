package journalProject.Database;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletResponse;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.time.LocalDate;
import java.util.List;

public class Sort {

    private static String query = "";

    public static List<Entry> BooleanSearch(Dao database, String searched, HttpServletResponse response) {
        query = " WHERE MATCH(title, message) AGAINST('+" + searched + "' IN BOOLEAN MODE)";

        Cookie searchCookie = new Cookie("SearchQuery", URLEncoder.encode(searched, StandardCharsets.UTF_8));
        searchCookie.setMaxAge(60*60*24);
        response.addCookie(searchCookie);

        return database.getEntries(query);
    }

    public static List<Entry> searchDates(Dao database, String from, String to, HttpServletResponse response) {
        if (to.equals("")) {
            to = LocalDate.now().toString();
        }
        query = " WHERE date BETWEEN '" + from + "' and '" + to + "'";

        Cookie fromCookie = new Cookie("SearchFrom", URLEncoder.encode(from, StandardCharsets.UTF_8));
        fromCookie.setMaxAge(60*60*24);
        response.addCookie(fromCookie);

        Cookie toCookie = new Cookie("SearchTo", URLEncoder.encode(to, StandardCharsets.UTF_8));
        toCookie.setMaxAge(60*60*24);
        response.addCookie(toCookie);

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

    public static void resetQuery() {
        query = "";
    }

}

