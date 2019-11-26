package ru.dsi.geekbrains.testproject.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import ru.dsi.geekbrains.testproject.entities.Task;
import ru.dsi.geekbrains.testproject.services.TaskService;

import java.util.List;

@Controller
@RequestMapping("/tasks")
public class TaskController {
    @Autowired
    private TaskService taskService;

    @RequestMapping(path = "/show", method = RequestMethod.GET)
    public String showAllStudents(Model model) {
        List<Task> tasks = taskService.getTasksSortedByStatus();
        model.addAttribute("tasks", tasks);
        return "tasks/list";
    }

    @GetMapping("/add")
    public String showAddForm(Model model) {
        Task task = new Task();
        task.setTitle("New task");
        task.setStatus("Open");
        model.addAttribute("task", task);
        return "tasks/add";
    }

    @PostMapping("/add")
    public String processAddForm(@ModelAttribute("task") Task task, Model model) {
        taskService.addTask(task);
        model.addAttribute("newId", task.getId());

        task = new Task();
        task.setTitle("New task");
        task.setStatus("Open");
        model.addAttribute("task", task);
        return "tasks/add";
    }

}
