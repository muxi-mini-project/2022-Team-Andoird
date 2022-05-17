package com.example.team.teamwork.Bean;

import java.util.ArrayList;

public class CreateTaskData {
    /**
     *  "deadline": "2022-11-30 19:08:06",
     *     "remark": "产品组准备产品",
     *     "step": "报名",
     *     "member": [
     *         "宁先生"
     *
     *     ],
     *     "start_time": "2017-11-30 19:08:06",
     *     "project": "项目10号",
     *     "name": "报名木犀产品发布"
     */

    private String deadline;
    private String start_time;
    private String remark;
    private String step;
    private ArrayList<String> member;
    private String project;
    private String name;


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

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public String getStep() {
        return step;
    }

    public void setStep(String step) {
        this.step = step;
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

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

}
