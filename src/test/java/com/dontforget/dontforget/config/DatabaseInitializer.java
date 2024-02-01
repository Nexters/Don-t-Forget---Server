package com.dontforget.dontforget.config;

import jakarta.annotation.PostConstruct;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.stream.Collectors;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;
import org.springframework.test.context.ActiveProfiles;

@Component
@ActiveProfiles("test")
public class DatabaseInitializer {

    private final JdbcTemplate jdbcTemplate;

    @Autowired
    public DatabaseInitializer(final JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @PostConstruct
    public void initializeDatabase() {
    }

    private void executeSqlScript(final String scriptPath) {
        try {
            final Path path = Path.of(getClass().getResource(scriptPath).toURI());
            final String sql = Files.lines(path).collect(Collectors.joining(" "));
            jdbcTemplate.execute(sql);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
