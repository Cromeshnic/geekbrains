package ru.dsi.geekbrains.testproject;

import ru.dsi.geekbrains.testproject.exceptions.MyException;

import java.util.List;

public interface TaskRepository {
    List<Task> getTasks() throws MyException;
    void addTask(Task task) throws MyException;
    Task getTask(long id) throws MyException;
    void removeTask(long id) throws MyException;
    void removeTask(String title) throws MyException;
    void removeTask(Task task) throws MyException;
    void close() throws MyException;
}