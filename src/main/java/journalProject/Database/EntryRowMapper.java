package journalProject.Database;

import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

class EntryRowMapper implements RowMapper<Entry> {

    @Override
    public Entry mapRow(ResultSet rs, int rowNum) throws SQLException {
        String date = formatDate(rs.getString("date"));

        return new Entry.Builder(rs.getString("id"))
                .withUser(rs.getString("user"))
                .withTitle(rs.getString("title"))
                .withDate(date)
                .withMessage(rs.getString("message"))
                .build();
    }

    private String formatDate(String date) {
        String formattedDate = date.substring(8) + "/" + date.substring(5, 7) + "/" + date.substring(0, 4);
        return formattedDate.toString();
    }
}
