package com.example.team.teamwork.Bean;

import java.util.ArrayList;

public class CreatPData {
    /**
     * "remark": "officia",
     * "project_name": "提书民反农长",
     * "deadline": "do ut officia velit ea",
     * "start_time": "1992-04-05 03:27:23",
     * "step": [
     * "aliquip Ut",
     * "qui sint",
     * "id tempor consectetur aliquip non",
     * "ad anim minim sint qui"
     * ]
     */
    private String project_name;
    private String remark;
    private String start_time;
    private String deadline;
    private ArrayList<String> step;

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public String getProject_name() {
        return project_name;
    }

    public void setProject_name(String project_name) {
        this.project_name = project_name;
    }

    public String getDeadline() {
        return deadline;
    }

    public void setDeadline(String deadline) {
        this.deadline = deadline;
    }

    public String getStart_time() {
        return start_time;
    }

    public void setStart_time(String start_time) {
        this.start_time = start_time;
    }

    public ArrayList<String> getStep() {
        return step;
    }

    public void setStep(ArrayList<String> step) {
        this.step = step;
    }

}
