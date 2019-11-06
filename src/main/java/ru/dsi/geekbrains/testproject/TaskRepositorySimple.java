package ru.dsi.geekbrains.testproject;

import ru.dsi.geekbrains.testproject.exceptions.MyException;
import ru.dsi.geekbrains.testproject.exceptions.MyRepositorySizeException;

public class TaskRepositorySimple implements TaskRepository {
    private Task[] tasks;

    public TaskRepositorySimple() {
        this.tasks = new Task[10];
    }

    public TaskRepositorySimple(int size) {
        this.tasks = new Task[size];
    }

    public Task[] getTasks() {
        return tasks.clone();
    }

    //2
    public void addTask(Task task) throws MyException {
        if(task==null){
            throw new MyException("task is null");
        }

        Task t = this.getTask(task.getId());
        if(t!=null){
            throw new MyException("Task '"+task+"' already exists in repository");
        }

        for (int i = 0; i < tasks.length; i++) {
            if (tasks[i] == null) {
                tasks[i] = task;
                return;
            }
        }
        throw new MyRepositorySizeException(this.tasks.length);
    }

    public Task getTask(long id){
        for (int i = 0; i < tasks.length; i++) {
            if(tasks[i]!=null && tasks[i].getId()==id){
                return tasks[i];
            }
        }
        return null;
    }

    //4
    public void removeTask(long id) throws MyException {
        for (int i = 0; i < tasks.length; i++) {
            if (tasks[i] != null && tasks[i].getId() == id) {
                tasks[i] = null;
                return;
            }
        }
        throw new MyException("Task id ="+id+" not found in repository");
    }

    public void removeTask(String title) throws MyException{
        if (title == null) {
            throw new MyException("Task title is null", new NullPointerException());
        }
        for (int i = 0; i < tasks.length; i++) {
            if (tasks[i] != null && title.equals(tasks[i].getTitle())) {
                tasks[i] = null;
                return;
            }
        }
        throw new MyException("Task with title ='"+title+"' not found in repository");
    }

    public void removeTask(Task task) throws MyException{
        if (task == null) {
            throw new MyException("Task is null", new NullPointerException());
        }

        for (int i = 0; i < tasks.length; i++) {
            if (task.equals(tasks[i])) {
                tasks[i] = null;
            }
        }
        throw new MyException("Task '"+task+"' not found in repository");
    }
}