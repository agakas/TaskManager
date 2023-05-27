package com.agakas.testtask.model;

import lombok.*;
import org.springframework.data.relational.core.mapping.Table;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Worker {

    private Long id;
    private String name;
    private String position;
    private String avatar;

    public Worker(String name, String position, String avatar) {
        this.name = name;
        this.position = position;
        this.avatar = avatar;
    }
}
