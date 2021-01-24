package ru.dsi.geekbrains.testproject.common;

import javax.validation.constraints.Size;

public class TaskDto{
    private Long id;
    @Size(min = 4, message = "Task title is too short")
    private String title;
    private String description;
    private Long ownerId;
    private IdTitle owner;
    private Long assigneeId;
    private Integer statusId;
    private IdTitle assignee;
    private IdTitle status;

    public TaskDto() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
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

    public Integer getStatusId() {
        return statusId;
    }

    public void setStatusId(Integer statusId) {
        this.statusId = statusId;
    }
}
