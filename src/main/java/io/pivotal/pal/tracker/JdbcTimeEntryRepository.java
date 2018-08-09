package io.pivotal.pal.tracker;

import com.mysql.cj.jdbc.MysqlDataSource;
import org.springframework.http.ResponseEntity;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.ResultSetExtractor;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;

import javax.sql.DataSource;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.List;

import static java.sql.Statement.RETURN_GENERATED_KEYS;

public class JdbcTimeEntryRepository implements TimeEntryRepository{

    private JdbcTemplate template;



    public JdbcTimeEntryRepository(DataSource dataSource){
    this.template=new JdbcTemplate(dataSource);
    }

    @Override
    public TimeEntry create(TimeEntry te) {
             KeyHolder keyholder=new GeneratedKeyHolder();
        template.update(connection -> {
        PreparedStatement statement = connection.prepareStatement(
                "INSERT INTO time_entries (project_id, user_id, date, hours) " +
                        "VALUES (?, ?, ?, ?)",
                RETURN_GENERATED_KEYS
        );

            statement.setLong(1,te.getProjectId());
            statement.setLong(2,te.getUserId());
            statement.setDate(3, Date.valueOf(te.getDate()));
            statement.setLong(4,te.getHours());
            return statement;

        },keyholder);

        return find(keyholder.getKey().longValue());

    }

    @Override
    public TimeEntry find(long id) {
        return template.query(
                "SELECT id, project_id, user_id, date, hours FROM time_entries WHERE id = ?",
                new Object[]{id},
                extractor);
    }

    @Override
    public List<TimeEntry> list() {
       return template.query("SELECT id, project_id, user_id, date, hours FROM time_entries",mapper);

    }

    @Override
    public TimeEntry update(long id, TimeEntry te) {

         template.update("Update time_entries "+
                "SET  project_id = ?, user_id = ?, date = ?,  hours = ? "+ "where id=?",te.getProjectId(),
                te.getUserId(),Date.valueOf(te.getDate()),te.getHours(),id);
    return find(id);
    }

    @Override
    public void delete(long id) {
template.update("Delete from time_entries where id=?",id);
    }

    @Override
    public ResponseEntity<TimeEntry> read(long id) {
        return null;
    }

    private final RowMapper<TimeEntry> mapper = (rs, rowNum) -> new TimeEntry(
            rs.getLong("id"),
            rs.getLong("project_id"),
            rs.getLong("user_id"),
            rs.getDate("date").toLocalDate(),
            rs.getInt("hours")
    );

    private final ResultSetExtractor<TimeEntry> extractor =
            (rs) -> rs.next() ? mapper.mapRow(rs, 1) : null;
}
