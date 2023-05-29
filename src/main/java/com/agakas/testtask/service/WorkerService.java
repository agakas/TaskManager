package com.agakas.testtask.service;

import com.agakas.testtask.model.Worker;
import com.agakas.testtask.model.WorkerAndShortTaskInfo;
import com.agakas.testtask.repository.WorkerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
public class WorkerService {

    @Autowired
    WorkerRepository workerRepository;
    public void createWorker(Worker worker){
        //Генерирование ссылки на аватарку
        String pathToAvatar = "https://example.com/"+ UUID.randomUUID().toString() + ".png";
        worker.setAvatar(pathToAvatar);
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
        //Генерирование ссылки на аватарку
        String pathToAvatar = "https://example.com/"+ UUID.randomUUID().toString() + ".png";
        worker.setAvatar(pathToAvatar);
        workerRepository.updateWorker(worker);
    }
    public int deleteWorker(long id){
        return workerRepository.deleteWorkerById(id);
    }
}
