package journalProject.Database;

public class Entry {

    private final String user;
    private final String id;
    private final String date;
    private final String title;
    private final String message;


    public Entry(String user, String id, String date, String title, String message) {
        this.user = user;
        this.id = id;
        this.date = date;
        this.title = title;
        this.message = message;
    }

    public String getUser() {
        return user;
    }

    public String getId() {
        return id;
    }

    public String getDate() {
        return date;
    }

    public String getTitle() {
        return title;
    }

    public String getMessage() {
        return message;
    }

}
