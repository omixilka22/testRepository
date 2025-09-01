package com.example.webform.dao;

import com.example.webform.model.Person;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class Container {

    private final JdbcTemplate jdbcTemplate;

    public Container(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    // READ ALL
    public List<Person> getPeople() {
        return jdbcTemplate.query(
                "SELECT * FROM person",
                new BeanPropertyRowMapper<>(Person.class)
        );
    }

    // READ ONE
    public Person getPerson(int id) {
        return jdbcTemplate.queryForObject(
                "SELECT * FROM person WHERE id = ?",
                new BeanPropertyRowMapper<>(Person.class),
                id
        );
    }

    // CREATE
    public void save(Person person) {
        jdbcTemplate.update(
                "INSERT INTO person (name, age, email) VALUES (?, ?, ?)",
                person.getName(), person.getAge(), person.getEmail()
        );
    }

    // UPDATE
    public void update(Person person, int id) {
        jdbcTemplate.update(
                "UPDATE person SET name = ?, age = ?, email = ? WHERE id = ?",
                person.getName(), person.getAge(), person.getEmail(), id
        );
    }

    // DELETE
    public void delete(int id) {
        jdbcTemplate.update("DELETE FROM person WHERE id = ?", id);
    }
}

