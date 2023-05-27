package com.agakas.testtask.repository;

import com.agakas.testtask.model.Worker;
import com.agakas.testtask.model.WorkerAndShortTaskInfo;

import java.util.List;

public interface WorkerRepository {

    WorkerAndShortTaskInfo getWorkerAndShortTask(long id);
    Worker findById(long id);
    int addWorker(Worker worker);
    List<Worker> getAllWorkers();

    int updateWorker(Worker worker);

    int deleteWorkerById(long id);
}
