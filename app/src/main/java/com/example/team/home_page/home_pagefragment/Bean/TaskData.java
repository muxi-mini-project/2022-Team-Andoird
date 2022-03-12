package com.example.team.home_page.home_pagefragment.Bean;

import java.util.Date;
import java.util.UUID;

public class TaskData {
    private UUID mId;
    private String mTeam;
    private String mTask;
    private String mProject;
    private Date mDate;
    private boolean mSolved;

    //唯一值id
    public TaskData(){
        mId=UUID.randomUUID();
    }

    public UUID getId() {
        return mId;
    }

    public void setId(UUID id) {
        mId = id;
    }

    public String getTeam() {
        return mTeam;
    }

    public void setTeam(String team) {
        mTeam = team;
    }

    public String getTask() {
        return mTask;
    }

    public void setTask(String task) {
        mTask = task;
    }

    public String getProject() {
        return mProject;
    }

    public void setProject(String project) {
        mProject = project;
    }

    public Date getDate() {
        return mDate;
    }

    public void setDate(Date date) {
        mDate = date;
    }

    public boolean isSolved() {
        return mSolved;
    }

    public void setSolved(boolean solved) {
        mSolved = solved;
    }
}
