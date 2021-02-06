package journalProject.Database;

import org.springframework.jdbc.core.JdbcTemplate;

import javax.sql.DataSource;
import java.util.List;

public class MySqlDao implements Dao {

    private JdbcTemplate jdbcTemplate;

    public MySqlDao(DataSource ds) {
        this.jdbcTemplate = new JdbcTemplate(ds);
    }

    @Override
    public void add(Entry entry) {
        jdbcTemplate.update("INSERT INTO ENTRIES VALUES (?, ?, ?, ?)",
                entry.getId(), entry.getTitle(), entry.getMessage(), entry.getDate());
    }

    @Override
    public void delete(String id) {
        jdbcTemplate.update("DELETE FROM ENTRIES WHERE id=?", id);
    }

    @Override
    public List<Entry> getEntries() {
        String query = "SELECT * FROM ENTRIES";
        return jdbcTemplate.query(query, new EntryRowMapper());
    }



}
