package ru.dsi.geekbrains.testproject.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.dsi.geekbrains.testproject.entities.Task;
import ru.dsi.geekbrains.testproject.exceptions.MyException;
import ru.dsi.geekbrains.testproject.repositories.TaskRepository;

import java.io.*;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class TaskService{
    private TaskRepository taskRepository;

    public TaskService(){

    }

    @Autowired
    public void setTaskRepository(TaskRepository taskRepository) {
        this.taskRepository = taskRepository;
    }

    public Task getTaskById(long id) throws MyException {
        return this.taskRepository.getTask(id);
    }

    public void addTask(Task task) throws MyException {
        this.taskRepository.addTask(task);
    }

    public void removeTask(long id) throws MyException {
        this.taskRepository.removeTask(id);
    }

    public void removeTask(String title) throws MyException {
        this.taskRepository.removeTask(title);
    }

    public void removeTask(Task task) throws MyException {
        this.taskRepository.removeTask(task);
    }

    public void printTasks(){
        System.out.println("Tasks:");
        List<Task> tasks = null;
        try {
            tasks = taskRepository.getTasks();
        } catch (MyException e) {
            e.printStackTrace();
        }
        if(tasks!=null){
            tasks.forEach(System.out::println);
        }
        System.out.println("---");
    }

    //Тут вообще нужно делать движок поиска по произвольному набору полей, доступных для поиска
    public List<Task> getTasksByStatusAndAssignee(String status, String assignee) throws MyException {
        //Нужно по-хорошему перенести применение фильтров на уровень репозитория
        //Для этого скорее всего нужен отдельный класс-набор фильтров, который будем прокидывать
        return this.taskRepository.getTasks().stream()
                .filter(task -> status==null || "".equals(status) || status.equals(task.getStatus().name()))
                .filter(task -> assignee==null || "".equals(assignee) || assignee.equals(task.getAssignee()))
                .collect(Collectors.toList());
    }

    //a.
    public List<Task> getTasksByStatus(String status) throws MyException {
        return this.taskRepository.getTasks().stream()
                    .filter(task -> status==null ? task.getStatus()==null : status.equals(task.getStatus().name()))
                    .collect(Collectors.toList());
    }
    //b.
    public boolean taskExists(long id) throws MyException {
        return this.taskRepository.getTasks().stream().anyMatch(task -> task.getId()==id);
    }
    //c.
    public List<Task> getTasksSortedByStatus(){
        try {
            return this.taskRepository.getTasks().stream()
                    .sorted(Comparator.comparing(Task::getStatus))
                    .collect(Collectors.toList());
        } catch (MyException e) {
            e.printStackTrace();
        }
        return Collections.emptyList();
    }
    //d.
    public long getTaskCountByStatus(String status){
        try {
            return this.taskRepository.getTasks().stream()
                    .filter(task -> status==null ? task.getStatus()==null : status.equals(task.getStatus().name()))
                    .count();
        } catch (MyException e) {
            e.printStackTrace();
        }
        return -1;
    }

    public void exportToFile(List<Task> tasks, File file) throws IOException {
        try(ObjectOutputStream out = new ObjectOutputStream(new FileOutputStream(file))){
            out.writeObject(tasks);
        }
    }

    public List<Task> loadFromFile(File file) throws IOException, ClassNotFoundException {
        try(ObjectInputStream in = new ObjectInputStream(new FileInputStream(file))){
            return (List<Task>) in.readObject();
        }
    }
}