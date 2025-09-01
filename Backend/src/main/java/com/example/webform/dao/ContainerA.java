package com.example.webform.dao;

import org.springframework.stereotype.Component;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

import com.example.webform.model.Person;

@Component
public class ContainerA {
    private int ID;

    private static final String URL = "jdbc:postgresql://localhost:5432/mydb";
    private static final String username = "myuser";
    private static final String password = "mypassword";
    private static final String driver = "org.postgresql.Driver";

    private static Connection connection;

    static {
        try { Class.forName(driver); }
        catch (ClassNotFoundException e) { e.printStackTrace(); }

        try { connection = DriverManager.getConnection(URL, username, password); }
        catch (SQLException e) { e.printStackTrace(); }
    }

    public List<Person> getPeople()  {
        List<Person> persons = new ArrayList<>();
        try {
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery("select * from person");

            while (resultSet.next()) {
                Person person = new Person();
                person.setId(resultSet.getInt("id"));
                person.setName(resultSet.getString("name"));
                person.setAge(resultSet.getInt("age"));
                person.setEmail(resultSet.getString("email"));
                persons.add(person);
            }
        }
        catch (SQLException e) { e.printStackTrace(); }

        System.out.println("Total persons: " + persons.size());
        return persons;
    }

    public Person getPerson(int id) {
        Person person = null;
            try
            {
                PreparedStatement preparedStatement =
                        connection.prepareStatement("Select * from Person where id = ?");
                preparedStatement.setInt(1, id);
                ResultSet resultSet = preparedStatement.executeQuery();

                if (resultSet.next()) {
                    person = new Person();
                    person.setId(resultSet.getInt("id"));
                    person.setName(resultSet.getString("name"));
                    person.setAge(resultSet.getInt("age"));
                    person.setEmail(resultSet.getString("email"));
                }

            }
            catch (SQLException e) {
                e.printStackTrace();
            }
        return person;
    }

    public void save(Person person) {

        try {
            PreparedStatement preparedStatement =
                    connection.prepareStatement("INSERT INTO Person (name, age, email) VALUES (?, ?, ?)");
            preparedStatement.setString(1, person.getName());
            preparedStatement.setInt(2, person.getAge());
            preparedStatement.setString(3, person.getEmail());
            preparedStatement.executeUpdate();

        }
        catch (SQLException e) { e.printStackTrace(); }
    }

    public void update(Person person , int id) {
        try
        {
            PreparedStatement preparedStatement =
                    connection.prepareStatement("UPDATE Person SET name = ? , age = ? , email = ? WHERE id = ?");
            preparedStatement.setString(1, person.getName());
            preparedStatement.setInt(2, person.getAge());
            preparedStatement.setString(3, person.getEmail());
            preparedStatement.setInt(4, id);

            int rowsUpdated = preparedStatement.executeUpdate();
            if (rowsUpdated > 0) {
                System.out.println("Person updated successfully.");
            } else {
                System.out.println("No person found with the given id.");
            }
        }
        catch (SQLException e) { e.printStackTrace(); }
    }

    public void delete(int id) {

        try {
        PreparedStatement preparedStatement =
                connection.prepareStatement("DELETE From Person where id = ?") ;
            preparedStatement.setInt(1, id);
            int rowsDeleted = preparedStatement.executeUpdate();
            if (rowsDeleted > 0) {
                System.out.println("Person deleted successfully.");
            }
        }
        catch (SQLException e) { e.printStackTrace(); }
    }
}

