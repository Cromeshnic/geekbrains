package ru.dsi.geekbrains.testproject.common;

public class TaskDto extends IdTitle{
    private String description;
    private Long ownerId;
    private IdTitle owner;
    private Long assigneeId;
    private IdTitle assignee;
    private int statusId;
    private IdTitle status;

    public TaskDto() {
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public IdTitle getOwner() {
        return owner;
    }

    public void setOwner(IdTitle owner) {
        this.owner = owner;
    }

    public IdTitle getAssignee() {
        return assignee;
    }

    public void setAssignee(IdTitle assignee) {
        this.assignee = assignee;
    }

    public IdTitle getStatus() {
        return status;
    }

    public void setStatus(IdTitle status) {
        this.status = status;
    }

    public Long getOwnerId() {
        return ownerId;
    }

    public void setOwnerId(Long ownerId) {
        this.ownerId = ownerId;
    }

    public Long getAssigneeId() {
        return assigneeId;
    }

    public void setAssigneeId(Long assigneeId) {
        this.assigneeId = assigneeId;
    }

    public int getStatusId() {
        return statusId;
    }

    public void setStatusId(int statusId) {
        this.statusId = statusId;
    }
}
