package com.example.team.teamwork.MyTeam.Horizontal;

import java.util.List;


public class HorizontalData {
    private Integer code;
    private TeamDTO team;
    private List<UserDTO> user;

    public List<UserDTO> getUser() {
        return user;
    }

    public void setUser(List<UserDTO> user) {
        this.user = user;
    }

    public static class TeamDTO {
        private Integer teamId;
        private String teamname;
        private Integer creatorId;
        private String teamCoding;
        private String avatar;
    }

    public static class UserDTO {
        private Integer id;
        private String studentId;
        private String phone;
        private String nickname;
        private String password;
        private String feedback;
        private String avatar;
        private String sha;
        private String path;

        public String getAvatar() {
            return avatar;
        }

        public void setAvatar(String avatar) {
            this.avatar = avatar;
        }
    }

}
