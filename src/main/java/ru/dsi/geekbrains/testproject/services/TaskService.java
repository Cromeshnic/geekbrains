package ru.dsi.geekbrains.testproject.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import ru.dsi.geekbrains.testproject.entities.Task;
import ru.dsi.geekbrains.testproject.repositories.TaskRepository;

@Service
public class TaskService{
    private TaskRepository taskRepository;

    public TaskService(){

    }

    @Autowired
    public void setTaskRepository(TaskRepository taskRepository) {
        this.taskRepository = taskRepository;
    }

    public Task getById(long id){
        return this.taskRepository.findById(id).get();
    }

    public void save(Task task){
        this.taskRepository.save(task);
    }

    public void remove(long id){
        this.taskRepository.deleteById(id);
    }

    public void delete(Task task){
        this.taskRepository.delete(task);
    }

    public Page<Task> getTasks(Specification<Task> spec, Pageable pageable){
        return taskRepository.findAll(spec, pageable);
    }
}