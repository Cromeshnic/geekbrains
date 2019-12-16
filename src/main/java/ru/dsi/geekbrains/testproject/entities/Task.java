package ru.dsi.geekbrains.testproject.entities;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import lombok.Data;

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

        public String getName() {
            return name;
        }

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
        return this.id == ((Task) o).getId();
    }

    public Long getId() {
        return id;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public User getAssignee() {
        return assignee;
    }

}
