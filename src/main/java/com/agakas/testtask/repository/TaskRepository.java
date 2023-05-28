package com.agakas.testtask.repository;

import com.agakas.testtask.model.GeneralTask;
import com.agakas.testtask.model.Task;
import com.agakas.testtask.model.Worker;

import java.util.List;

public interface TaskRepository {
    List<GeneralTask> getAllShortTask();
    int loadToDB(Task task);
    List<Task> getAllFullTask();
    Task findById(long id);
    int updateTask(Task task);
    int assignTask(Task task);
}
