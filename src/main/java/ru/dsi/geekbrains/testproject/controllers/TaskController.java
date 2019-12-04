package ru.dsi.geekbrains.testproject.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;
import ru.dsi.geekbrains.testproject.entities.Task;
import ru.dsi.geekbrains.testproject.exceptions.MyException;
import ru.dsi.geekbrains.testproject.services.TaskService;

@Controller
@RequestMapping("/tasks")
public class TaskController {
    @Autowired
    private TaskService taskService;

    @RequestMapping(path = "/show", method = RequestMethod.GET)
    public String showAllTasks(@RequestParam(name = "status", defaultValue = "") String status, @RequestParam(name = "assignee", defaultValue = "") String assignee, Model model) {
        try {
            model.addAttribute("tasks", taskService.getTasksByStatusAndAssignee(status, assignee));
        } catch (MyException e) {
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Ошибка поиска задач");
        }
        model.addAttribute("status", status);
        model.addAttribute("assignee", assignee);

        return "tasks/list";
    }

    @GetMapping("/new")
    public String showAddForm(Model model) {
        Task task = new Task();
        task.setTitle("New task");
        model.addAttribute("task", task);
        return "tasks/new";
    }

    @PostMapping("/add")
    public String processAddForm(@ModelAttribute("task") Task task, Model model) {
        try {
            taskService.addTask(task);
        } catch (MyException e) {
            e.printStackTrace();
        }
        //model.addAttribute("newId", task.getId());
        return "redirect:/tasks/new";
    }

    @GetMapping("/detail/{id}")
    public String taskDetail(@PathVariable long id, Model model) {
        try {
            model.addAttribute("task", taskService.getTaskById(id));
        } catch (MyException e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Task with id = "+id+" not found");
        }
        return "tasks/detail";
    }

}
