package spring.test.jdbc;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import spring.test.jdbc.model.Student;
import spring.test.jdbc.repository.StudentDao;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.SQLException;

/**
 * H2DemoApplication
 *
 * @author YangQi
 */
@SpringBootApplication
@Slf4j
public class H2DemoApplication implements CommandLineRunner {

    @Autowired
    private DataSource dataSource;

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Autowired
    private StudentDao studentDao;

    public static void main(String[] args) {
        SpringApplication.run(H2DemoApplication.class, args);
    }

    @Override
    public void run(String... args) throws Exception {
        showConn();
        showData();
//        Student student = new
        studentDao.insertStudent(Student.builder().id(3).name("姓名").build());
        studentDao.listData();
    }

    @Bean
    @Autowired
    public SimpleJdbcInsert simpleJdbcInsert(JdbcTemplate jdbcTemplate) {
        return new SimpleJdbcInsert(jdbcTemplate)
                .withTableName("STUDENT").usingGeneratedKeyColumns("ID");
    }

    private void showConn() throws SQLException {
        log.info(dataSource.toString());
        Connection conn = dataSource.getConnection();
        log.info(conn.toString());
        conn.close();
    }

    private void showData() {
        jdbcTemplate.queryForList("SELECT * FROM STUDENT").forEach(row -> log.info(row.toString()));
    }
}
