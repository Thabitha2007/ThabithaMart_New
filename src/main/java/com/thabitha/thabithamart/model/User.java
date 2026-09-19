package com.thabitha.thabithamart.model;

public class User {
    public long id;
    public String username;
    public String passwordHash;
    public String email;
    public String role;

    // Default constructor
    public User() {
    }

    // Parameterized constructor
    public User(long id, String username, String passwordHash, String email, String role) {
        this.id = id;
        this.username = username;
        this.passwordHash = passwordHash;
        this.email = email;
        this.role = role;
    }
}