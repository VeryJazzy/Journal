package journalProject.Database;

import java.util.List;

public interface Dao {

    void add(Entry entry);

    void delete(String id);

    List<Entry> getEntries();

    List<Entry> getEntriesNew();

    List<Entry> getEntriesOld();
}
