package ru.dsi.geekbrains.testproject.common;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@EqualsAndHashCode(callSuper = true)
@NoArgsConstructor
@Data
public class TaskDto extends IdTitle{
    private String description;
    private IdTitle owner;
    private IdTitle assignee;
    private IdTitle status;
}
