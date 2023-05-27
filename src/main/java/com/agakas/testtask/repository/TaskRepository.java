package com.agakas.testtask.repository;

import com.agakas.testtask.model.Task;

import java.util.List;

public interface TaskRepository {
    void addTask(Task task);

    void updateTask(Long id, Task task);

    void assignTask(Long taskId, Long workerId);

    List<Task> getAllTasks();

    Task getTaskById(Long id);

}
