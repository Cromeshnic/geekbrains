package ru.dsi.geekbrains.testproject;

import ru.dsi.geekbrains.testproject.exceptions.MyException;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

public class LinkedListTaskRepository implements TaskRepository {
    private LinkedList<Task> tasks;

    public LinkedListTaskRepository() {
        this.tasks = new LinkedList<>();
    }

    @Override
    public List<Task> getTasks() {
        return new ArrayList<>(tasks);
    }

    @Override
    public void addTask(Task task) throws MyException {
        this.tasks.add(task);
    }

    @Override
    public Task getTask(long id) {
        for(Task task : this.tasks){
            if(task!=null && task.getId()==id){
                return task;
            }
        }
        return null;
    }

    @Override
    public void removeTask(long id) throws MyException {
        if(!tasks.removeIf(task -> task!=null && task.getId() == id)){
            throw new MyException("Task id ="+id+" not found in repository");
        }
    }

    @Override
    public void removeTask(String title) throws MyException {
        if(title==null){
            throw new MyException("Task title is null", new NullPointerException());
        }
        if(!tasks.removeIf(task -> task!=null && title.equals(task.getTitle()))){
            throw new MyException("Task with title ='"+title+"' not found in repository");
        }
    }

    @Override
    public void removeTask(Task task) throws MyException {
        if(!tasks.remove(task)){
            throw new MyException("Task '"+task+"' not found in repository");
        }
    }
}
