package roomescape.dao;

import java.time.LocalTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.stereotype.Repository;
import roomescape.domain.Time;
import roomescape.exception.TimeNotFoundException;

@Repository
public class TimeDao {

    private final JdbcTemplate jdbcTemplate;
    private final SimpleJdbcInsert simpleJdbcInsert;

    private final RowMapper<Time> timeRowMapper = (rs, rowNum) ->
        new Time(
            rs.getLong("id"),
            LocalTime.parse(rs.getString("time"))
        );

    public TimeDao(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
        this.simpleJdbcInsert = new SimpleJdbcInsert(jdbcTemplate)
            .withTableName("time")
            .usingGeneratedKeyColumns("id")
            .usingColumns("time");
    }

    public List<Time> findAll() {
        String sql = "SELECT id, time FROM time";
        return jdbcTemplate.query(sql, timeRowMapper);
    }

    public Time findById(Long id) {
        String sql = "SELECT id, time FROM time where id = ?";
        try {
            return jdbcTemplate.queryForObject(sql, timeRowMapper, id);
        } catch (EmptyResultDataAccessException e) {
            throw new TimeNotFoundException(id);
        }
    }

    public Time save(Time time) {
        Map<String, Object> params = new HashMap<>();
        params.put("time", time.getTime().toString());

        Number key = simpleJdbcInsert.executeAndReturnKey(new MapSqlParameterSource(params));
        return new Time(key.longValue(), time.getTime());
    }

    public void deleteById(long id) {
        String sql = "DELETE FROM time WHERE id = ?";
        int deleted = jdbcTemplate.update(sql, id);
        if (deleted == 0) {
            throw new TimeNotFoundException(id);
        }
    }
}
