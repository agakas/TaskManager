package com.agakas.testtask.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Getter
@Setter
public class WorkerAndShortTaskInfo {
    private Worker worker;
    private List<Task> tasks;

    public WorkerAndShortTaskInfo(){

    }
    public WorkerAndShortTaskInfo(Worker worker, List<Task> tasks){
        this.worker = worker;
        this.tasks = tasks;
    }
}
