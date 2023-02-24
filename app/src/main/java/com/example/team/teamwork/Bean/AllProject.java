package com.example.team.teamwork.Bean;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class AllProject {
    private int code;
    private List<PData> data;
    private String message;

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public List<PData> getData() {
        return data;
    }

    public void setData(List<PData> data) {
        this.data = data;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public static class PData implements Serializable {
        /**
         "project_id": 3,
         "project_name": "项目一号",
         "creator_id": 2,
         "create_time": "2022-04-22 00:14:00",
         "start_time": "1995-10-24 18:45:18",
         "deadline": "2022-4-24 22:00:00",
         "remark": "作长别品",
         "team_id": 2,
         "step": [
         "irure consectetur",
         "esse"
         ]
         */

        private int project_id;
        private String project_name;
        private String create_time;
        private String start_time;
        private String deadline;
        private String remark;
        private String creator_id;
        private ArrayList<String> step;
        private int team_id;

        public int getProject_id() {
            return project_id;
        }

        public void setProject_id(int project_id) {
            this.project_id = project_id;
        }

        public String getProject_name() {
            return project_name;
        }

        public void setProject_name(String project_name) {
            this.project_name = project_name;
        }

        public String getCreate_time() {
            return create_time;
        }

        public void setCreate_time(String create_time) {
            this.create_time = create_time;
        }

        public String getStart_time() {
            return start_time;
        }

        public void setStart_time(String start_time) {
            this.start_time = start_time;
        }

        public String getDeadline() {
            return deadline;
        }

        public void setDeadline(String deadline) {
            this.deadline = deadline;
        }

        public String getRemark() {
            return remark;
        }

        public void setRemark(String remark) {
            this.remark = remark;
        }

        public String getCreator_id() {
            return creator_id;
        }

        public void setCreator_id(String creator_id) {
            this.creator_id = creator_id;
        }

        public ArrayList<String> getStep() {
            return step;
        }

        public void setStep(ArrayList<String> step) {
            this.step = step;
        }

        public int getTeam_id() {
            return team_id;
        }

        public void setTeam_id(int team_id) {
            this.team_id = team_id;
        }
    }

}
