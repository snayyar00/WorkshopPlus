package com.example.workshop;

public class AssignmentMember {
    String assignmentName;

    public AssignmentMember() {
        this.assignmentName = "default";
    }

    public AssignmentMember(String assignmentName) {
        this.assignmentName = assignmentName;
    }

    public String getAssignmentName() {
        return assignmentName;
    }

    public void setAssignmentName(String assignmentName) {
        this.assignmentName = assignmentName;
    }
}
