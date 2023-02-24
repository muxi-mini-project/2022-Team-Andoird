package com.example.team.team.Bean;

import java.io.Serializable;
import java.util.List;

public class UserTeam {
    //List来存储数据
    private List<Team> team;
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

    public List<Team> getTeam() {
        return team;
    }

    public void setTeam(List<Team> team) {
        this.team = team;
    }

    public static class Team implements Serializable {
        /**
          "team_id": 1,
          "teamname": "团队一号",
          "creator_id": 2,
          "team_coding": "123456789",
          "avatar": "https://gitee.com/Alen-H/imagebed/raw/master/2团队一号.jpg"
         */
        private int team_id;
        private String teamname;
        private int creator_id;
        private String team_coding;
        private String avatar;

        public int getTeam_id() {
            return team_id;
        }

        public void setTeam_id(int team_id) {
            this.team_id = team_id;
        }

        public String getTeamname() {
            return teamname;
        }

        public void setTeamname(String teamname) {
            this.teamname = teamname;
        }

        public int getCreator_id() {
            return creator_id;
        }

        public void setCreator_id(int creator_id) {
            this.creator_id = creator_id;
        }

        public String getTeam_coding() {
            return team_coding;
        }

        public void setTeam_coding(String team_coding) {
            this.team_coding = team_coding;
        }

        public String getAvatar() {
            return avatar;
        }

        public void setAvatar(String avatar) {
            this.avatar = avatar;
        }
    }
}
