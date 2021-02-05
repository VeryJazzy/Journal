package journalProject;

import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

class EntryRowMapper implements RowMapper<Entry> {

    @Override
    public Entry mapRow(ResultSet rs, int rowNum) throws SQLException {
        return new Entry(rs.getString("id"),
                rs.getString("date"),
                rs.getString("title"),
                rs.getString("message"));
    }
}
