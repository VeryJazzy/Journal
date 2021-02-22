package journalProject.Database;

import org.springframework.context.annotation.Bean;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.RequestParam;

import javax.sql.DataSource;
import java.util.List;
import java.util.UUID;

public class MySqlDatabase implements Dao {

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    private JdbcTemplate jdbcTemplate;

    public MySqlDatabase(DataSource ds) {
        this.jdbcTemplate = new JdbcTemplate(ds);
    }

    @Override
    public void add(Entry entry) {
        jdbcTemplate.update("INSERT INTO ENTRIES VALUES (?, ?, ?, ?)", entry.getId(), entry.getTitle(), entry.getMessage(), entry.getDate());
    }

    @Override
    public void delete(String id) {
        jdbcTemplate.update("DELETE FROM ENTRIES WHERE id=?", id);
    }

    @Override
    public List<Entry> getEntries(String searchTerm, String searchFrom, String searchTo, String sort) {
        String query = queryBuilder(searchTerm, searchFrom, searchTo, sort);
        return jdbcTemplate.query(query, new EntryRowMapper());
    }

    @Override
    public String queryBuilder(String searchTerms, String searchFrom, String searchTo, String sort) {
        StringBuilder query = new StringBuilder("SELECT * FROM ENTRIES");

        if (!searchTerms.equals("")) {
            query.append(" WHERE MATCH(title, message) AGAINST('+").append(searchTerms).append("' IN BOOLEAN MODE)");
        } else if (!searchFrom.equals("")) {
            query.append(" WHERE date BETWEEN '").append(searchFrom).append("' and '").append(searchTo).append("'");
        }
        if (!sort.equals("")) {
            query.append(checkSort(sort));
        }
        return query.toString();
    }

    public String checkSort(String sort) {
        if (sort.equals("NEWEST")) {
            return " ORDER BY date DESC";
        } else if (sort.equals("OLDEST")) {
            return " ORDER BY date";
        }
        return "";
    }

    @Override
    public String registerNewUser(String username,String password) {
        String userID = UUID.randomUUID().toString();
        jdbcTemplate.update("INSERT INTO users VALUES (?, ?, ?, ?)",
                userID,
                username,
                passwordEncoder().encode(password),
                1);
        jdbcTemplate.update("INSERT INTO user_authorities VALUES (?, ?, ?)",
                UUID.randomUUID().toString(),
                userID,
                "USER");

        return "/login";
    }



}
