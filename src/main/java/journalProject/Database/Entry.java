package journalProject.Database;

public class Entry {

    public static class Builder {

        private String id;
        private String user;
        private String date;
        private String title;
        private String message;

        public Builder(String id) {
            this.id = id;
        }

        public Builder withUser(String user){
            this.user = user;
            return this;  //By returning the builder each time, we can create a fluent interface.
        }

        public Builder withDate(String date){
            this.date= date;
            return this;
        }

        public Builder withTitle(String title){
            this.title = title;
            return this;
        }

        public Builder withMessage(String message){
            this.message = message;
            return this;
        }

        public Entry build() {
            Entry entry = new Entry();
            entry.id = this.id;
            entry.user = this.user;
            entry.date =this.date;
            entry.title = this.title;
            entry.message = this.message;
            return entry;
        }
    }



    private String user;
    private String id;
    private String date;
    private String title;
    private String message;

    private Entry() { }

    public String getUser() { return user; }

    public String getId() {
        return id;
    }

    public String getDate() { return date; }

    public String getTitle() { return title; }

    public String getMessage() { return message; }
}
