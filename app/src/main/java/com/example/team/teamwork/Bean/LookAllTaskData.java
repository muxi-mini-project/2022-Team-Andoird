package com.example.team.teamwork.Bean;

import java.util.List;

public class LookAllTaskData {
    private int code;
    private List<LookTaskData.TData> data;
    private String message;

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public List<LookTaskData.TData> getData() {
        return data;
    }

    public void setData(List<LookTaskData.TData> data) {
        this.data = data;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
