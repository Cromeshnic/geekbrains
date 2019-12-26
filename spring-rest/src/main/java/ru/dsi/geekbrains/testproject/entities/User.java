package ru.dsi.geekbrains.testproject.entities;

import lombok.Data;
import lombok.NoArgsConstructor;
import ru.dsi.geekbrains.testproject.common.UserDto;

import javax.persistence.*;
import java.util.Collection;

@NoArgsConstructor
@Data
@Entity
@Table(name = "user")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;
    private String password;

    @ManyToMany
    @JoinTable(name = "users_roles",
            joinColumns = @JoinColumn(name = "user_id"),
            inverseJoinColumns = @JoinColumn(name = "role_id"))
    private Collection<Role> roles;

    public User(Long id){
        this.id = id;
    }

    public UserDto toDto(){
        UserDto userDto = new UserDto();
        userDto.setId(this.id);
        userDto.setTitle(this.name);
        userDto.setPassword(this.password);
        return userDto;
    }
}
