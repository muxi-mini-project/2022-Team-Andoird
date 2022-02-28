package com.example.team.login.logining;

public class LoginUser {
    private String student_id;
    private String password;
    private String id;


    public LoginUser(String id, String password) {
        this.student_id = id;
        this.password = password;
    }

    public String getStudent_id() {
        return student_id;
    }

    public void setStudent_id(String student_id) {
        this.student_id = student_id;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
