package com.agakas.testtask.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Task {

    private Long id;
    private String title;
    private String description;
    private LocalDateTime time;
    private String status;
    private Long performer;

    public Task(long id, String title, String status){
        this.id = id;
        this.title = title;
        this.status = status;
    }
}
