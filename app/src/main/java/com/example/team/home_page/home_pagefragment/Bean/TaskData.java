package com.example.team.home_page.home_pagefragment.Bean;

import com.example.team.team.Bean.UserTeam;
import com.example.team.teamwork.Bean.LookTaskData;

import java.util.Date;
import java.util.List;
import java.util.UUID;

public class TaskData {
    private String message;

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    private int code;
    private List<LookTaskData.TData> task;
    private List<UserTeam.Team> team;

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public List<LookTaskData.TData> getTask() {
        return task;
    }

    public void setTask(List<LookTaskData.TData> task) {
        this.task = task;
    }

    public List<UserTeam.Team> getTeam() {
        return team;
    }

    public void setTeam(List<UserTeam.Team> team) {
        this.team = team;
    }
}
