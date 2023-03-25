package com.example.team.teamwork.MyTeam.Vertical;

import java.util.List;

public class VerticalData {

    private Integer code;
    private List<DataDTO> data;
    private String message;

    public List<DataDTO> getData() {
        return data;
    }

    public void setData(List<DataDTO> data) {
        this.data = data;
    }

    public static class DataDTO {
        private Integer projectId;
        private String projectName;
        private Integer creatorId;
        private String createTime;
        private String startTime;
        private String deadline;
        private String remark;
        private Integer teamId;
        private Object step;

        public Integer getProjectId() {
            return projectId;
        }

        public void setProjectId(Integer projectId) {
            this.projectId = projectId;
        }

        public String getProjectName() {
            return projectName;
        }

        public void setProjectName(String projectName) {
            this.projectName = projectName;
        }

        public String getDeadline() {
            return deadline;
        }

        public void setDeadline(String deadline) {
            this.deadline = deadline;
        }
    }
}
