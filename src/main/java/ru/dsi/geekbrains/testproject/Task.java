package ru.dsi.geekbrains.testproject;

import java.io.Serializable;

class Task implements Serializable {
    private static final long serialVersionUID = 8213894426665978662L;

    private long id;
    private String title;
    private String owner;
    private String assignee;
    private String description;
    private String status;

    public Task(long id, String title, String owner) {
        this.id = id;
        this.title = title;
        this.owner = owner;
        this.status="";
    }

    public Task(long id, String title, String owner, String status) {
        this.id = id;
        this.title = title;
        this.owner = owner;
        this.status = status;
    }

    @Override
    public String toString(){
        return "Task #"+id+" ["+title+"]";
    }

    @Override
    public boolean equals(Object o){
        if(!(o instanceof Task)){
            return false;
        }
        return this.id == ((Task) o).getId();
    }

    public long getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getOwner() {
        return owner;
    }

    public void setOwner(String owner) {
        this.owner = owner;
    }

    public String getAssignee() {
        return assignee;
    }

    public void setAssignee(String assignee) {
        this.assignee = assignee;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
