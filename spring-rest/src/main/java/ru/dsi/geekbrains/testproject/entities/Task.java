package ru.dsi.geekbrains.testproject.entities;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import lombok.Data;
import ru.dsi.geekbrains.testproject.common.IdTitle;
import ru.dsi.geekbrains.testproject.common.TaskDto;

import javax.persistence.*;

//@NoArgsConstructor
@Data
@Entity
@Table(name = "task")
public class Task{
    public enum Status {
        OPEN (0, "Open"), IN_PROGRESS(1, "In progress"), CLOSED(2, "Closed");
        private int value;
        private String name;

        Status(int value, String name) {
            this.value = value;
            this.name = name;
        }
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String title;
    @ManyToOne()
    @JoinColumn(name = "owner_id")
    @JsonManagedReference
    private User owner;

    @ManyToOne()
    @JoinColumn(name = "assignee_id")
    @JsonManagedReference
    private User assignee;
    private String description;
    
    @Enumerated(EnumType.STRING)
    @Column(name = "status")
    private Status status;

    public Task(){
        this.title="";
        this.description="";
        this.status=Status.OPEN;
    }

    @Override
    public String toString(){
        return "Task #"+id+" ["+title+"]";
    }

    @Override
    public boolean equals(Object o){
        if(!(o instanceof Task)){
            return false;
        }
        return this.id.equals(((Task) o).getId());
    }

    //Не уверен, что это здесь должно быть
    //С одной стороны, удобно - при заведении новых полей сложнее забыть поправить конвертер в Dto
    //С другой стороны, должны ли Task и TaskService знать про TaskDto?
    //А в TaskDto (в common) не хочется тащить логику конвертации
    public TaskDto toDto(){
        TaskDto taskDto = new TaskDto();
        taskDto.setId(this.getId());
        taskDto.setTitle(this.title);
        taskDto.setStatus(new IdTitle((long) this.status.value, this.status.name));
        taskDto.setDescription(this.description);

        if(this.getOwner()!=null){
            taskDto.setOwner(new IdTitle(this.getOwner().getId(), this.getOwner().getName()));
        }
        if(this.getAssignee()!=null) {
            taskDto.setAssignee(new IdTitle(this.getAssignee().getId(), this.getAssignee().getName()));
        }

        return taskDto;
    }

    public static Task valueOf(TaskDto taskDto){
        Task task = new Task();
        task.setId(taskDto.getId());
        task.setTitle(taskDto.getTitle());
        /*if(taskDto.getAssigneeId()!=null){
            task.setAssignee(user);
        }*/
        return task;
    }

}
