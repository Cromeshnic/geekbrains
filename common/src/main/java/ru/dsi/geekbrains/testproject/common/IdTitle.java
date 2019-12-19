package ru.dsi.geekbrains.testproject.common;

import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@Data
public class IdTitle {
    private Long id;
    private String title;

    public IdTitle(Long id, String title){
        this.id=id;
        this.title=title;
    }
}
