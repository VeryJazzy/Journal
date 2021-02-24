package journalProject.Database;

public class Query {

    private final String user;
    private final String searchTerms;
    private final String searchFrom;
    private final String searchTo;
    private final String sort;

    public Query(String user, String searchTerms, String searchFrom, String searchTo, String sort) {
        this.user = user;
        this.searchTerms = searchTerms;
        this.searchFrom = searchFrom;
        this.searchTo = searchTo;
        this.sort = sort;
    }


    public String getUser() {
        return user;
    }
    public String getSearchTerms() {
        return searchTerms;
    }

    public String getSearchFrom() {
        return searchFrom;
    }

    public String getSearchTo() {
        return searchTo;
    }

    public String getSort() {
        return sort;
    }

}
