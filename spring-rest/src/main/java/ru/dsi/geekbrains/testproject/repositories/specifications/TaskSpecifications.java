package ru.dsi.geekbrains.testproject.repositories.specifications;

import org.springframework.data.jpa.domain.Specification;
import ru.dsi.geekbrains.testproject.entities.Task;

public class TaskSpecifications {
    public static Specification<Task> statusEq(Integer status){
        return (root, criteriaQuery, criteriaBuilder) -> criteriaBuilder.equal(root.get("status"),status);
    }

    public static Specification<Task> assigneeEq(Long assignee_id){
        return (root, criteriaQuery, criteriaBuilder) -> criteriaBuilder.equal(root.get("assignee"), assignee_id);
    }
}
