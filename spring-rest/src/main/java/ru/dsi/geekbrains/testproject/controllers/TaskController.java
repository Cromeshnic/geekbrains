package ru.dsi.geekbrains.testproject.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.dsi.geekbrains.testproject.common.TaskDto;
import ru.dsi.geekbrains.testproject.entities.Task;
import ru.dsi.geekbrains.testproject.repositories.specifications.TaskSpecifications;
import ru.dsi.geekbrains.testproject.services.TaskService;
import ru.dsi.geekbrains.testproject.services.UserService;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@RestController
@CrossOrigin("*")
@RequestMapping("/api/1.0/task")
public class TaskController {
    @Autowired
    private TaskService taskService;

    @Autowired
    private UserService userService;

    @GetMapping
    public List<TaskDto> list(
            @RequestParam(name = "status", required = false) Integer status,
            @RequestParam(name = "assignee", required = false) Long assignee_id,
            @RequestParam(defaultValue = "1") Integer pageNumber,
            @RequestParam(defaultValue = "0") Integer tasksPerPage,
            @RequestParam(defaultValue = "status") String sortBy,
            @RequestParam(defaultValue = "asc") String direction
    ) {
        Specification<Task> spec = Specification.where(null);
        if(null!=status){
            spec = spec.and(TaskSpecifications.statusEq(status));
        }
        if(null!=assignee_id){
            spec = spec.and(TaskSpecifications.assigneeEq(assignee_id));
        }

        if (pageNumber < 1) {
            pageNumber = 1;
        }
        if(tasksPerPage<1){
            tasksPerPage = null;
        }

        Sort.Direction sortDirection = ("asc".equals(direction) ? Sort.Direction.ASC : Sort.Direction.DESC);
        if(null==tasksPerPage){//Если количество элементов на странице не указано, то показываем все
            return taskService.getTasks(spec, Sort.by(sortDirection, sortBy)).stream().map(Task::toDto).collect(Collectors.toList());
        }

        return taskService.getTasks(spec, PageRequest.of(pageNumber - 1, tasksPerPage, sortDirection, sortBy)).getContent().stream().map(Task::toDto).collect(Collectors.toList());
    }

    @PostMapping
    public TaskDto add(@ModelAttribute("task") TaskDto taskDto) {
        taskDto.setId(null);//Надо ли?
        return taskService.save(Task.valueOf(taskDto)).toDto();
    }

    @PutMapping("/{id}")
    public TaskDto update(@ModelAttribute("task") TaskDto taskDto, @PathVariable Long id) {
        taskDto.setId(id);//Как тут правильно?
        return taskService.save(Task.valueOf(taskDto)).toDto();
    }

    @PostMapping("/{id}")//Продусблирую на Post
    public TaskDto updatePost(@ModelAttribute("task") TaskDto taskDto, @PathVariable Long id) {
        taskDto.setId(id);//Как тут правильно?
        return taskService.save(Task.valueOf(taskDto)).toDto();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> delete(@PathVariable Long id){
        taskService.delete(id);
        return new ResponseEntity<String>("Successfully removed", HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public TaskDto get(@PathVariable Long id) {
        return Optional.ofNullable(taskService.getById(id)).map(Task::toDto).orElse(null);
    }
}
