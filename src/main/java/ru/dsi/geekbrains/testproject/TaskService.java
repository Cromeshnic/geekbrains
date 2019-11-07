package ru.dsi.geekbrains.testproject;

import ru.dsi.geekbrains.testproject.exceptions.MyException;

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
        Task[] tasks = taskRepository.getTasks();
        for (int i = 0; i < tasks.length; i++) {
            System.out.println(tasks[i]);
        }
        System.out.println("---");
    }

}