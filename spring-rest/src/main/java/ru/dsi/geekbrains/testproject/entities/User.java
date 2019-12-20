package ru.dsi.geekbrains.testproject.entities;

import lombok.Data;
import lombok.NoArgsConstructor;
import ru.dsi.geekbrains.testproject.common.UserDto;

import javax.persistence.*;

@NoArgsConstructor
@Data
@Entity
@Table(name = "user")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    public User(Long id){
        this.id = id;
    }

    public UserDto toDto(){
        UserDto userDto = new UserDto();
        userDto.setId(this.id);
        userDto.setTitle(this.name);
        return userDto;
    }
}
