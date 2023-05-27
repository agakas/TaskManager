package com.agakas.testtask.controller;

import com.agakas.testtask.model.Worker;
import com.agakas.testtask.model.WorkerAndShortTaskInfo;
import com.agakas.testtask.repository.WorkerRepositoryImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/worker")
public class WorkerController {
    @Autowired
    WorkerRepositoryImpl workerRepository;


    @GetMapping("/{id}")
    public ResponseEntity<WorkerAndShortTaskInfo> getWorkerAndInfoById(@PathVariable long id) {
        WorkerAndShortTaskInfo worker = workerRepository.getWorkerAndShortTask(id);

        if (worker != null) {
            return new ResponseEntity<>(worker, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @PostMapping("")
    public ResponseEntity<String> createWorker(@RequestBody Worker worker) {
        try {
            workerRepository.addWorker(new Worker(worker.getName(), worker.getPosition(), worker.getAvatar()));
            return new ResponseEntity<>("Worker was created successfully.", HttpStatus.CREATED);
        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
    //Получение списка всех рабочих
    @GetMapping("/allWorkers")
    public ResponseEntity<List<Worker>> getAllWorkers() {
        List<Worker> workers = workerRepository.getAllWorkers();
        return new ResponseEntity<>(workers, HttpStatus.OK);
    }
    @PutMapping("/{id}")
    public ResponseEntity<String> updateTutorial(@PathVariable("id") long id, @RequestBody Worker worker) {
        Worker _worker = workerRepository.findById(id);

        if (_worker != null) {
            _worker.setId(id);
            _worker.setName(worker.getName());
            _worker.setPosition(worker.getPosition());
            _worker.setAvatar(worker.getAvatar());

            workerRepository.updateWorker(_worker);
            return new ResponseEntity<>("Worker was updated successfully.", HttpStatus.OK);
        } else {
            return new ResponseEntity<>("Cannot find Worker with id=" + id, HttpStatus.NOT_FOUND);
        }
    }
    //Удаление рабочего по id
    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteAllTutorials(@PathVariable("id") long id) {
        try {
            int result = workerRepository.deleteWorkerById(id);
            if (result == 0) {
                return new ResponseEntity<>("Cannot find Worker with id=" + id, HttpStatus.OK);
            }
            return new ResponseEntity<>("Worker was deleted successfully.", HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>("Cannot delete Worker.", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
