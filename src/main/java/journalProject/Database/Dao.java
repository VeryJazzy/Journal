package journalProject.Database;

import java.util.List;

public interface Dao {
    void add(Entry entry);

    void delete(String id);

    List<Entry> getEntries(String searchTerm, String searchFrom, String searchTo, String sort);

    boolean registerNewUser(String username,String password);
}
