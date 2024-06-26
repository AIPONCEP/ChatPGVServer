package org.example.chatpgvserver.models.objects;

public class User {
    private int id;

    private String name;

    private String password;

    private int tlf;

    public User(int id, String name, String password, int tlf) {
        this.id = id;
        this.name = name;
        this.password = password;
        this.tlf = tlf;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public int getTlf() {
        return tlf;
    }

    public void setTlf(int tlf) {
        this.tlf = tlf;
    }
}