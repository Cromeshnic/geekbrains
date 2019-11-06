package ru.dsi.geekbrains.testproject;

import ru.dsi.geekbrains.testproject.exceptions.MyException;

public interface TaskRepository {
    Task[] getTasks();
    void addTask(Task task) throws MyException;
    Task getTask(long id);
    void removeTask(long id) throws MyException;
    void removeTask(String title) throws MyException;
    void removeTask(Task task) throws MyException;
}