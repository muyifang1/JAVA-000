package spring.test.jdbc.repository;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.stereotype.Repository;
import spring.test.jdbc.model.Student;

import java.util.HashMap;

/**
 * StudentDao
 *
 * @author YangQi
 */
@Slf4j
@Repository
public class StudentDao {

    @Autowired
    private JdbcTemplate jdbcTemplate;
    @Autowired
    private SimpleJdbcInsert simpleJdbcInsert;

    public void insertStudent(Student record) {
        HashMap<String, String> row = new HashMap<>();

        row.put("NAME", record.getName());
        Number id = simpleJdbcInsert.executeAndReturnKey(row);
        log.info("Insert complete the ID of d: {}", id.longValue());

    }

    public void listData() {
        jdbcTemplate.queryForList("SELECT * FROM STUDENT").forEach(row -> log.info(row.toString()));
    }
}
