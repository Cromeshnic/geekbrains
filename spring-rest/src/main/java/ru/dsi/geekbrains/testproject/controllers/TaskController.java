package ru.dsi.geekbrains.testproject.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.*;
import ru.dsi.geekbrains.testproject.common.TaskDto;
import ru.dsi.geekbrains.testproject.configs.JwtTokenUtil;
import ru.dsi.geekbrains.testproject.entities.Task;
import ru.dsi.geekbrains.testproject.exceptions.ResourceNotFoundException;
import ru.dsi.geekbrains.testproject.repositories.specifications.TaskSpecifications;
import ru.dsi.geekbrains.testproject.services.TaskService;
import ru.dsi.geekbrains.testproject.services.UserService;

import javax.validation.Valid;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@CrossOrigin("*")
@RequestMapping("/api/1.0/task")
public class TaskController {
    @Autowired
    private TaskService taskService;

    @Autowired
    private UserService userService;

    @Autowired
    private JwtTokenUtil jwtTokenUtil;

    @GetMapping
    public ResponseEntity<List<TaskDto>> list(
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
            return new ResponseEntity<>(taskService.getTasks(spec, Sort.by(sortDirection, sortBy)).stream().map(Task::toDto).collect(Collectors.toList()), HttpStatus.OK);
        }

        return new ResponseEntity<>(taskService.getTasks(spec, PageRequest.of(pageNumber - 1, tasksPerPage, sortDirection, sortBy)).getContent().stream().map(Task::toDto).collect(Collectors.toList()), HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<?> add(@RequestHeader("Authorization") String requestTokenHeader, @ModelAttribute("task") @Valid TaskDto taskDto, BindingResult bindingResult) {
        taskDto.setId(null);

        if (bindingResult.hasErrors()) {
            StringBuilder errorMessage = new StringBuilder();
            for (ObjectError o : bindingResult.getAllErrors()) {
                errorMessage.append(o.getDefaultMessage()).append(";\n");
            }
            return new ResponseEntity<>(errorMessage.toString(), HttpStatus.BAD_REQUEST);
        }
        
        if(taskDto.getOwnerId()==null || taskDto.getAssigneeId()==null) {//Если явно не указан владелец или исполнитель - подставляем текущего юзера
            Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
            if (authentication != null && authentication.isAuthenticated()) {
                String jwtToken = jwtTokenUtil.getTokenFromHeader(requestTokenHeader);
                Long userId = jwtTokenUtil.extractClaim(jwtToken, claims -> claims.get("userId", Long.class));
                if (userId != null) {
                    if(taskDto.getOwnerId()==null){
                        taskDto.setOwnerId(userId);
                    }
                    if(taskDto.getAssigneeId()==null){
                        taskDto.setAssigneeId(userId);
                    }
                }
            }
        }
        return new ResponseEntity<>(taskService.save(Task.valueOf(taskDto)).toDto(), HttpStatus.OK);
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> update(@RequestBody @Valid TaskDto taskDto, BindingResult bindingResult, @PathVariable Long id) {
        if (bindingResult.hasErrors()) {
            StringBuilder errorMessage = new StringBuilder();
            for (ObjectError o : bindingResult.getAllErrors()) {
                errorMessage.append(o.getDefaultMessage()).append(";\n");
            }
            return new ResponseEntity<>(errorMessage.toString(), HttpStatus.BAD_REQUEST);
        }
        taskDto.setId(id);
        return new ResponseEntity<>(taskService.save(Task.valueOf(taskDto)).toDto(), HttpStatus.CREATED);
    }

    @PostMapping("/{id}")//Продублирую на Post
    public ResponseEntity<?> updatePost(@RequestBody @Valid TaskDto taskDto, @PathVariable Long id, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            StringBuilder errorMessage = new StringBuilder();
            for (ObjectError o : bindingResult.getAllErrors()) {
                errorMessage.append(o.getDefaultMessage()).append(";\n");
            }
            return new ResponseEntity<>(errorMessage.toString(), HttpStatus.BAD_REQUEST);
        }
        taskDto.setId(id);
        return new ResponseEntity<>(taskService.save(Task.valueOf(taskDto)).toDto(), HttpStatus.CREATED);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> delete(@PathVariable Long id){
        taskService.delete(id);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<TaskDto> get(@PathVariable Long id) {
        Task task = taskService.getById(id);
        if(task==null){
            throw new ResourceNotFoundException("Task with id: " + id + " not found");
        }
        return new ResponseEntity<>(task.toDto(), HttpStatus.OK);
        //return new ResponseEntity<>(Optional.ofNullable(taskService.getById(id)).map(Task::toDto).orElse(null), HttpStatus.OK);
    }
}
