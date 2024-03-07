package com.primshits.stepan;

import lombok.extern.java.Log;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.jdbc.core.JdbcTemplate;

import javax.sql.DataSource;
import java.util.List;
import java.util.Map;

@SpringBootApplication
@Log
public class FileStorageApplication implements CommandLineRunner {

	private final DataSource dataSource;

	public FileStorageApplication(DataSource dataSource) {
		this.dataSource = dataSource;
	}

	public static void main(String[] args) {
		SpringApplication.run(FileStorageApplication.class, args);
	}

	@Override
	public void run(final String... args) {
		log.info("Datasource: " + dataSource.toString());
		final JdbcTemplate jdbcTemplate = new JdbcTemplate(dataSource);
		jdbcTemplate.execute("CREATE TABLE IF NOT EXISTS users (id INT AUTO_INCREMENT PRIMARY KEY, name VARCHAR(255))");

		// Добавляем нового пользователя
		String name = "John";
		jdbcTemplate.update("INSERT INTO users (name) VALUES (?)", name);

		// Получаем список пользователей
		List<Map<String, Object>> users = jdbcTemplate.queryForList("SELECT * FROM users");
		log.info("Users: " + users);
	}

}
