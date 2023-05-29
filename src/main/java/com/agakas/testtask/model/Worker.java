package com.agakas.testtask.model;

import lombok.*;

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
    public Worker(String name, String position) {
        this.name = name;
        this.position = position;
    }
}
