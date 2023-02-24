package com.example.team.team.Bean;


public class TeamData {
    private int code;
    private String message;
    private TeamData.DataDTO data;

    public DataDTO getData() {
        return data;
    }

    public void setData(DataDTO data) {
        this.data = data;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
    //内部类
    public static class DataDTO {
        private String team_coding;
        private int team_id;
        private String url;

        public String getUrl() {
            return url;
        }

        public void setUrl(String url) {
            this.url = url;
        }

        public DataDTO(String team_coding){
            this.team_coding=team_coding;
        }
        public DataDTO(String team_coding,String url){
            this.team_coding=team_coding;
            this.url=url;
        }


        public String getTeam_coding() {
            return team_coding;
        }

        public void setTeam_coding(String team_coding) {
            this.team_coding = team_coding;
        }

        public int getTeam_id() {
            return team_id;
        }

        public void setTeam_id(int team_id) {
            this.team_id = team_id;
        }
    }

}
