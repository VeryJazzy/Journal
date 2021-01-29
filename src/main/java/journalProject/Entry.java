package journalProject;

public class Entry {



    private String id;
    private String date;
    private String title;
    private String message;

    public Entry(String id, String date, String title, String message) {
        this.id = id;
        this.date = date;
        this.title = title;
        this.message = message;
    }
    public String getId() { return id; }

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
