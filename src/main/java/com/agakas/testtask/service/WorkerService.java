package com.agakas.testtask.service;

import com.agakas.testtask.model.Worker;
import com.agakas.testtask.model.WorkerAndShortTaskInfo;
import com.agakas.testtask.repository.WorkerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class WorkerService {

    @Autowired
    WorkerRepository workerRepository;
    public void createWorker(Worker worker){
        workerRepository.addWorker(worker);
    }
    public Worker readOne(long id){
        return workerRepository.findById(id);
    }
    public List<Worker> readAll(){
        return workerRepository.getAllWorkers();
    }
    public WorkerAndShortTaskInfo readInfoWorkerById(long id){
        return workerRepository.getWorkerAndShortTask(id);
    }
    public void updateWorker(Worker worker){
        workerRepository.updateWorker(worker);
    }
    public int deleteWorker(long id){
        return workerRepository.deleteWorkerById(id);
    }
}
