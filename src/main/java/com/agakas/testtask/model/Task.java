package com.agakas.testtask.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

//Расширенная модель задачи
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Task extends GeneralTask{

    private String description;
    private LocalDateTime time;
    private Long performer;

    public Task(String title, String description, LocalDateTime time, String status){
        this.title = title;
        this.description = description;
        this.time = time;
        this.status = status;
    }

    @Override
    public String toString() {
        return "Task{" +
                "description='" + description + '\'' +
                ", time=" + time +
                ", performer=" + performer +
                ", id=" + id +
                ", title='" + title + '\'' +
                ", status='" + status + '\'' +
                '}';
    }
}
