package journalProject.Database;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletResponse;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.time.LocalDate;
import java.util.List;

public class Sort {

    public static List<Entry> BooleanSearch(Dao database, String searched, HttpServletResponse response) {
        response.addCookie(createCookie("SearchQuery", URLEncoder.encode(searched, StandardCharsets.UTF_8)));

        return database.getEntries(" WHERE MATCH(title, message) AGAINST('+" + searched + "' IN BOOLEAN MODE)");
    }

    public static List<Entry> searchDates(Dao database, String from, String to, HttpServletResponse response) {
        if (to.equals("")) {
            to = LocalDate.now().toString();
        }
        response.addCookie(createCookie("SearchFrom", URLEncoder.encode(from, StandardCharsets.UTF_8)));
        response.addCookie(createCookie("SearchTo", URLEncoder.encode(to, StandardCharsets.UTF_8)));

        return database.getEntries(" WHERE date BETWEEN '" + from + "' and '" + to + "'");
    }





    public static List<Entry> sortBy(Dao database, String order, Cookie searchQuery, Cookie searchFrom, Cookie searchTo) {
        String query = "";

        if (searchQuery.getValue() != "") {
            query = " WHERE MATCH(title, message) AGAINST('+" + searchQuery.getValue() + "' IN BOOLEAN MODE)";
        }
        if (searchFrom.getValue() != "") {
            query = " WHERE date BETWEEN '" + searchFrom.getValue() + "' and '" + searchTo.getValue() + "'";
        }
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

    public static Cookie createCookie(String name, String value){
        Cookie cookie = new Cookie(name, value);
        cookie.setMaxAge(60*60*24);
        return cookie;
    }
}

