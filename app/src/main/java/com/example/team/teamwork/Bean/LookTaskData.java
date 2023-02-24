package com.example.team.teamwork.Bean;


import java.io.Serializable;
import java.util.ArrayList;


public class LookTaskData {

    private int code;
    private TData data;
    private String message;
    private int task_id;

    public int getTask_id() {
        return task_id;
    }

    public void setTask_id(int task_id) {
        this.task_id = task_id;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public TData getData() {
        return data;
    }

    public void setData(TData data) {
        this.data = data;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public static class TData implements Serializable {
        /**
         * "task_id": 2,
         * "name": "报名木犀产品发布",
         * "creator_id": 2,
         * "createtime": "2022-04-22 16:01:00",
         * "start_time": "2017-11-30 19:08:06",
         * "deadline": "2022-11-30 19:08:06",
         * "remark": "产品组准备产品",
         * "step_id": 10,
         * "member": [
         * "宁先生"
         * ],
         * "project": "项目10号",
         * "step": "报名",
         * "team_id": 2
         */

        private int task_id;
        private String name;
        private String createtime;
        private String start_time;
        private String deadline;
        private String remark;
        private String step_id;
        private ArrayList<String> member;
        private String project;
        private String step;
        private int team_id;

        public int getTask_id() {
            return task_id;
        }

        public void setTask_id(int task_id) {
            this.task_id = task_id;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getCreatetime() {
            return createtime;
        }

        public void setCreatetime(String createtime) {
            this.createtime = createtime;
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

        public String getStep_id() {
            return step_id;
        }

        public void setStep_id(String step_id) {
            this.step_id = step_id;
        }

        public ArrayList<String> getMember() {
            return member;
        }

        public void setMember(ArrayList<String> member) {
            this.member = member;
        }

        public String getProject() {
            return project;
        }

        public void setProject(String project) {
            this.project = project;
        }

        public String getStep() {
            return step;
        }

        public void setStep(String step) {
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
