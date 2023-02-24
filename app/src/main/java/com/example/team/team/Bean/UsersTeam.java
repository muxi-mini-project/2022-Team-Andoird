package com.example.team.team.Bean;


import java.io.Serializable;
import java.util.List;

public class UsersTeam {
    //List来存储数据
    private UserTeam.Team team;
    private List<Users> user;
    private String message;
    private int code;


    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public UserTeam.Team getTeam() {
        return team;
    }

    public void setTeam(UserTeam.Team team) {
        this.team = team;
    }

    public List<Users> getUser() {
        return user;
    }

    public void setUser(List<Users> user) {
        this.user = user;
    }

    public static class Users implements Serializable {
        /**
         "id": 2,
         "student_id": "2021214599",
         "phone": "12345678901",
         "nickname": "宁先生",
         "password": "123456789",
         "feedback": "",
         "avatar": "https://gitee.com/Alen-H/imagebed/raw/master/220212145992022-04-21 14:03:00.jpg",
         "sha": "d469c8b9cee620a4f5475e61f4ae21d56b510c1b",
         "path": "220212145992022-04-21 14:03:00.jpg"
         */

        private int id;
        private String student_id;
        private String phone;
        private String nickname;
        private String password;
        private String avatar;

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public String getStudent_id() {
            return student_id;
        }

        public void setStudent_id(String student_id) {
            this.student_id = student_id;
        }

        public String getPhone() {
            return phone;
        }

        public void setPhone(String phone) {
            this.phone = phone;
        }

        public String getNickname() {
            return nickname;
        }

        public void setNickname(String nickname) {
            this.nickname = nickname;
        }

        public String getPassword() {
            return password;
        }

        public void setPassword(String password) {
            this.password = password;
        }

        public String getAvatar() {
            return avatar;
        }

        public void setAvatar(String avatar) {
            this.avatar = avatar;
        }
    }

}
