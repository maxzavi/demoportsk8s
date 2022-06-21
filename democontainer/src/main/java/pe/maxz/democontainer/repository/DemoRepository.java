package pe.maxz.democontainer.repository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

@Repository
public class DemoRepository {
    @Autowired
    private JdbcTemplate jdbcTemplate;

    public void execute() {
        jdbcTemplate.update("update demo set field1=sysdate");
    }

}
