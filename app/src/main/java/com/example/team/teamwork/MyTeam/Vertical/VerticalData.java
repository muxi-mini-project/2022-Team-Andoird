package com.example.team.teamwork.MyTeam.Vertical;

import java.util.Date;
import java.util.UUID;

public class VerticalData {
    private UUID mId;
    private String mTeam;
    private String mProjectIntroduction;
    private String mProject;
    private Date mDate;
    private int NumOfTask;

    public int getNumOfTask() {
        return NumOfTask;
    }

    public void setNumOfTask(int numOfTask) {
        NumOfTask = numOfTask;
    }

    private boolean mSolved;

        //唯一值id//
    public VerticalData(){ mId=UUID.randomUUID(); }

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

    public String getProjectIntroduction() {
        return mProjectIntroduction;
    }

    public void setProjectIntroduction(String projectIntroduction) {
        mProjectIntroduction = projectIntroduction;
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
