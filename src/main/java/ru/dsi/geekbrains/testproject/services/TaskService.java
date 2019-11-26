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


    public void addTask(Task task){
        try {
            this.taskRepository.addTask(task);
        } catch (MyException e) {
            e.printStackTrace();
        }
    }

    public void removeTask(long id){
        try {
            this.taskRepository.removeTask(id);
        } catch (MyException e) {
            e.printStackTrace();
        }
    }

    public void removeTask(String title){
        try {
            this.taskRepository.removeTask(title);
        } catch (MyException e) {
            e.printStackTrace();
        }
    }

    public void removeTask(Task task){
        try {
            this.taskRepository.removeTask(task);
        } catch (MyException e) {
            e.printStackTrace();
        }
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

    //a.
    public List<Task> getTasksByStatus(String status){
        try {
            return this.taskRepository.getTasks().stream()
                    .filter(task -> status==null ? task.getStatus()==null : status.equals(task.getStatus()))
                    .collect(Collectors.toList());
        } catch (MyException e) {
            e.printStackTrace();
        }
        return Collections.emptyList();
    }
    //b.
    public boolean taskExists(long id){
        try {
            return this.taskRepository.getTasks().stream().anyMatch(task -> task.getId()==id);
        } catch (MyException e) {
            e.printStackTrace();
        }
        return false;
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
                    .filter(task -> status==null ? task.getStatus()==null : status.equals(task.getStatus()))
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