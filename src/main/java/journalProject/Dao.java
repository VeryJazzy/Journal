package journalProject;

import java.util.ArrayList;

public interface Dao<T> {

    void add(T entry);

    void delete(String id);

    ArrayList<Entry> getEntries();
}
