package journalProject.Database;

public class Query {

    public static class Builder {

        private StringBuilder query = new StringBuilder("SELECT * FROM ENTRIES");

        public Builder withUser(String user) {
            if (!user.equals("")) {
                query.append(" WHERE USER = '").append(user).append("'");
            }
            return this;  //By returning the builder each time, we can create a fluent interface.
        }

        public Builder withSearchTerms(String searchTerms) {
            if (!searchTerms.equals("")) {
                query.append(" AND MATCH(title, message) AGAINST ('+").append(searchTerms).append("' IN BOOLEAN MODE)");
            }
            return this;
        }

        public Builder searchDates(String searchFrom, String searchTo) {
            if (!searchFrom.equals("")) {
                query.append(" AND date BETWEEN '").append(searchFrom).append("' and '").append(searchTo).append("'");
            }
            return this;
        }

        public Builder sort(String sort) {
            if (!sort.equals("")) {
                String sortQ = sort.equals("NEWEST") ? "ORDER BY date DESC" : " ORDER BY date";
                query.append(sortQ);
            }
            return this;
        }

        public String build() {
            return query.toString();
        }
    }


    private Query() { }
}
