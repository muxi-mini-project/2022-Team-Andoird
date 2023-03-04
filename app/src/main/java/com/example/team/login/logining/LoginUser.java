package com.example.team.login.logining;

public class LoginUser {

    /**
     * code : 200
     * msg : 获取成功
     * data : {"id": 49,
     *         "student_id": "2021214599",
     *         "phone": "18674225182",
     *         "nickname": "任秀英",
     *         "password": "",
     *         "feedback": "dolor",
     *         "avatar": "http://dummyimage.com/100x100",
     *
     *         }
     */

    private int code;
    private String msg;
    private Data data;

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public Data getData() {
        return data;
    }

    public void setData(Data data) {
        this.data = data;
    }
    //内部类
    public static class Data {
        /**
         "id": 49,
         "student_id": "2021214599",
         "phone": "18674225182",
         "nickname": "任秀英",
         "password": "SjU4NDkxMzE0NTIxamp5",
         "feedback": "dolor",
         "avatar": "http://dummyimage.com/100x100",
         "sha": "Lorem dolore ipsum deserunt incididunt",
         "path": "amet sed magna"
         */

        private String student_id;
        private String password;
        private Integer id;
        private String nickname;
        private String phone;
        private String avatar;
        private String feedback;
        private String path;
        private String sha;

        public String getPath() {
            return path;
        }

        public String getAvatar() {
            return avatar;
        }

        public void setAvatar(String avatar) {
            this.avatar = avatar;
        }

        public String getFeedback() {
            return feedback;
        }

        public void setFeedback(String feedback) {
            this.feedback = feedback;
        }

        public String getSha() {
            return sha;
        }

        public void setSha(String sha) {
            this.sha = sha;
        }

        public void setPath(String path) {
            this.path = path;
        }

        public Data(String id, String password) {
            this.student_id = id;
            this.password = password;
        }

        public Data(String nickname) {
            this.nickname = nickname;
        }
        public String getNickname() {
            return nickname;
        }

        public void setNickname(String nickname) {
            this.nickname = nickname;
        }

        public String getPhone() {
            return phone;
        }

        public void setPhone(String phone) {
            this.phone = phone;
        }
        public String getStudent_id() {
            return student_id;
        }

        public Integer getId() {
            return id;
        }

        public void setId(Integer id) {
            this.id = id;
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
}

