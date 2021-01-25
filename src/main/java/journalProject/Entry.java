package journalProject;

public class Entry {

    public String getDate() {
        return date;
    }

    public String getTitle() {
        return title;
    }

    public String getMood() {
        return mood;
    }

    public String getMessage() {
        return message;
    }

    private String date;
    private String title;
    private String mood;
    private String message;

    public Entry(String date, String title, String mood, String message) {
        this.date = date;
        this.title = title;
        this.mood = mood;
        this.message = message;
    }
}
