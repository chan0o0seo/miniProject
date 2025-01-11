package model;

public class User {
    private int idx;
    private String email;
    private String password;
    private String name;
    private String type;

    public User(String email, String password, String name, String type) {
        this.email = email;
        this.password = password;
        this.name = name;
        this.type = type;
    }
    public User(int idx,String email, String password, String name, String type) {
        this.idx = idx;
        this.email = email;
        this.password = password;
        this.name = name;
        this.type = type;
    }

    public User(String email, String password) {
        this.email = email;
        this.password = password;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getType() {return this.type;}

    public int getIdx() {
        return idx;
    }
}
