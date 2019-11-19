package ru.dsi.geekbrains.testproject;

import ru.dsi.geekbrains.testproject.exceptions.MyException;

import java.io.*;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

class TaskService{
    private TaskRepository taskRepository;

    public TaskService() {
        this.taskRepository = new LinkedListTaskRepository();
    }

    public void addTask(Task task){
        try {
            this.taskRepository.addTask(task);
        } catch (MyException e) {
            System.err.println(e);
        }
    }

    public void removeTask(long id){
        try {
            this.taskRepository.removeTask(id);
        } catch (MyException e) {
            System.err.println(e);
        }
    }

    public void removeTask(String title){
        try {
            this.taskRepository.removeTask(title);
        } catch (MyException e) {
            System.err.println(e);
        }
    }

    public void removeTask(Task task){
        try {
            this.taskRepository.removeTask(task);
        } catch (MyException e) {
            System.err.println(e);
        }
    }

    public void printTasks(){
        System.out.println("Tasks:");
        List<Task> tasks = taskRepository.getTasks();
        if(tasks!=null){
            tasks.forEach(System.out::println);
        }
        /*for (int i = 0; i < tasks.length; i++) {
            System.out.println(tasks[i]);
        }*/
        System.out.println("---");
    }

    //a.
    public List<Task> getTasksByStatus(String status){
        return this.taskRepository.getTasks().stream()
                .filter(task -> status==null ? task.getStatus()==null : status.equals(task.getStatus()))
                .collect(Collectors.toList());
    }
    //b.
    public boolean taskExists(long id){
        return this.taskRepository.getTasks().stream().anyMatch(task -> task.getId()==id);
    }
    //c.
    public List<Task> getTasksSortedByStatus(){
        return this.taskRepository.getTasks().stream()
                .sorted(Comparator.comparing(task -> Utils.maskNull(task.getStatus())))
                .collect(Collectors.toList());
    }
    //d.
    public long getTaskCountByStatus(String status){
        return this.taskRepository.getTasks().stream()
                .filter(task -> status==null ? task.getStatus()==null : status.equals(task.getStatus()))
                .count();
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