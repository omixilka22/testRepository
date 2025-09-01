package com.example.webform.model;

import jakarta.validation.constraints.Email;
import org.hibernate.validator.constraints.NotEmpty;
import org.hibernate.validator.constraints.Length;

public class Person {

    @NotEmpty(message = "Name should not be empty")
    @Length(min = 2 , max = 30 , message = "from 2 to 30 characters")
    private String name;

    @NotEmpty(message = "Email should not be empty")
    @Email(message = "email should have @ and domain")
    private String email;

    private int id;
    private int age;

    public Person() {}

    public Person(String name, int age, String email) {
        this.name = name;
        this.age = age;
        this.email = email;
    }

    public int getId() { return id; }
    public String getName() { return name; }
    public int getAge() { return age; }
    public String getEmail() { return email; }

    public void setId(int id) { this.id = id; }
    public void setName(String name) { this.name = name; }
    public void setAge(int age) { this.age = age; }
    public void setEmail(String email) { this.email = email; }
}
