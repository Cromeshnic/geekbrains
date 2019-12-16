package ru.dsi.geekbrains.testproject.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import ru.dsi.geekbrains.testproject.entities.Task;
import ru.dsi.geekbrains.testproject.repositories.specifications.TaskSpecifications;
import ru.dsi.geekbrains.testproject.services.TaskService;
import ru.dsi.geekbrains.testproject.services.UserService;

@Controller
@RequestMapping("/tasks")
public class TaskController {

    @Autowired
    private TaskService taskService;

    @Autowired
    private UserService userService;

    @GetMapping("/show")
    public String showAllTasks(
            @RequestParam(name = "status", required = false) Task.Status status,
            @RequestParam(name = "assignee", required = false) Long assignee_id,
            @RequestParam(defaultValue = "1") Long pageNumber,
            Model model
    ) {
        if (pageNumber < 1L) {
            pageNumber = 1L;
        }

        Specification<Task> spec = Specification.where(null);
        if(null!=status){
            spec = spec.and(TaskSpecifications.statusEq(status));
        }
        if(null!=assignee_id){
            spec = spec.and(TaskSpecifications.assigneeEq(assignee_id));
        }
        final int tasksPerPage = 5;//TODO
        model.addAttribute("tasks", taskService.getTasks(spec, PageRequest.of(pageNumber.intValue() - 1, tasksPerPage, Sort.Direction.ASC, "status")));
        model.addAttribute("status", status);
        model.addAttribute("assignee_id", assignee_id);
        //Справочник юзеров. Если будет огромный, то нужно видимо делать форму поиска и подгружать динамически
        // через Ajax при поиске, как в Jira
        model.addAttribute("users", userService.getAll());

        return "tasks/list";
    }

    @GetMapping("/add")
    public String showAddForm(Model model) {
        Task task = new Task();
        task.setTitle("New task");
        model.addAttribute("task", task);
        model.addAttribute("users", userService.getAll());
        return "tasks/add";
    }

    @PostMapping("/add")
    public String processAddForm(@ModelAttribute("task") Task task, Model model) {
        taskService.save(task);
        //model.addAttribute("newId", task.getId());
        return "redirect:/tasks/add";
    }

    @GetMapping("/detail/{id}")
    public String taskDetail(@PathVariable long id, Model model) {
        model.addAttribute("task", taskService.getById(id));
        return "tasks/detail";
    }

}
