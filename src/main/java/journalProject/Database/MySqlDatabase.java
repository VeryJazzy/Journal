package journalProject.Database;

import org.springframework.context.annotation.Bean;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
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
    public boolean registerNewUser(String username, String password) {
        String userID = UUID.randomUUID().toString();
        try {
            jdbcTemplate.update("INSERT INTO users VALUES (?, ?, ?, ?)",
                    userID,
                    username,
                    passwordEncoder().encode(password),
                    1);
            jdbcTemplate.update("INSERT INTO user_authorities VALUES (?, ?, ?)",
                    UUID.randomUUID().toString(),
                    userID,
                    "USER");
        } catch (Exception e) {
            return false;
        }
        return true;
    }

    @Override
    public void add(Entry entry) {
        jdbcTemplate.update("INSERT INTO ENTRIES VALUES (?, ?, ?, ?, ?)", entry.getUser(), entry.getId(), entry.getTitle(), entry.getMessage(), entry.getDate());
    }

    @Override
    public void delete(String id) {
        jdbcTemplate.update("DELETE FROM ENTRIES WHERE id=?", id);
    }

    @Override
    public List<Entry> getEntries(String searchTerm, String searchFrom, String searchTo, String sort) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String user = authentication.getName();

        Query queryObj = new Query(user, searchTerm, searchFrom, searchTo, sort);
        String queryString = queryBuilder(queryObj);
        return jdbcTemplate.query(queryString, new EntryRowMapper());
    }

    @Override
    public String queryBuilder(Query query) {
        StringBuilder queryString = new StringBuilder("SELECT * FROM ENTRIES WHERE USER = '" + query.getUser() + "'");

        if (!query.getSearchTerms().equals("")) {
            queryString
                    .append(" AND MATCH(title, message) AGAINST ('+")
                    .append(query.getSearchTerms())
                    .append("' IN BOOLEAN MODE)");
        }

        else if (!query.getSearchFrom().equals("")) {
            queryString
                    .append(" AND date BETWEEN '")
                    .append(query.getSearchFrom())
                    .append("' and '").append(query.getSearchTo())
                    .append("'");
        }

        if (!query.getSort().equals("")) {
            queryString.append(checkSort(query.getSort()));
        }

        return queryString.toString();
    }

    public String checkSort(String sort) {
        if (sort.equals("NEWEST")) {
            return " ORDER BY date DESC";
        } else if (sort.equals("OLDEST")) {
            return " ORDER BY date";
        }
        return "";
    }

}
