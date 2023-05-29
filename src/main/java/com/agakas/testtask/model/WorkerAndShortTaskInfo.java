package com.agakas.testtask.model;

import lombok.Getter;
import lombok.Setter;

import java.util.List;


@Getter
@Setter
public class WorkerAndShortTaskInfo {
    private Worker worker;
    private List<GeneralTask> tasks;

    public WorkerAndShortTaskInfo(){

    }
    public WorkerAndShortTaskInfo(Worker worker, List<GeneralTask> tasks){
        this.worker = worker;
        this.tasks = tasks;
    }
}
