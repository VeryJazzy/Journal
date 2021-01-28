package journalProject;

public class Entry {

    public String getDate() {
        return date;
    }

    public String getTitle() {
        return title;
    }



    public String getMessage() {
        return message;
    }

    private String date;
    private String title;

    private String message;

    public Entry(String date, String title, String message) {
        this.date = date;
        this.title = title;

        this.message = message;
    }
}
