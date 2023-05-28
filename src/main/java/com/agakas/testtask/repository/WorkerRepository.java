package com.agakas.testtask.repository;

import com.agakas.testtask.model.Task;
import com.agakas.testtask.model.Worker;
import com.agakas.testtask.model.WorkerAndShortTaskInfo;

import java.util.List;

public interface WorkerRepository {

    int addWorker(Worker worker);
    WorkerAndShortTaskInfo getWorkerAndShortTask(long id);
    List<Worker> getAllWorkers();
    Worker findById(long id);
    int updateWorker(Worker worker);
    int deleteWorkerById(long id);
}
