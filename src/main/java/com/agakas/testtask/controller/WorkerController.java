package com.agakas.testtask.controller;

import com.agakas.testtask.model.Worker;
import com.agakas.testtask.model.WorkerAndShortTaskInfo;
import com.agakas.testtask.service.WorkerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/worker")
public class WorkerController {
    private final WorkerService workerService;

    @Autowired
    public WorkerController(WorkerService workerService){
        this.workerService = workerService;
    }

    //Получение одного исполнителя
    @GetMapping("/{id}")
    public ResponseEntity<WorkerAndShortTaskInfo> getWorkerAndInfoById(@PathVariable long id) {
        WorkerAndShortTaskInfo worker = workerService.readInfoWorkerById(id);

        if (worker != null) {
            return new ResponseEntity<>(worker, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    //Создание исполнителя
    @PostMapping("")
    public ResponseEntity<String> createWorker(@RequestBody Worker worker) {
        try {
            workerService.createWorker(new Worker(worker.getName(), worker.getPosition()));
            return new ResponseEntity<>("Worker was created successfully.", HttpStatus.CREATED);
        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
    //Получение списка всех рабочих
    @GetMapping("/allWorkers")
    public ResponseEntity<List<Worker>> getAllWorkers() {
        List<Worker> workers = workerService.readAll();
        return new ResponseEntity<>(workers, HttpStatus.OK);
    }

    //Обновление данных рабочего
    @PatchMapping("/{id}")
    public ResponseEntity<String> updateTutorial(@PathVariable("id") long id, @RequestBody Worker worker) {
        Worker _worker = workerService.readOne(id);

        if (_worker != null) {
            _worker.setId(id);
            _worker.setName(worker.getName());
            _worker.setPosition(worker.getPosition());

            workerService.updateWorker(_worker);
            return new ResponseEntity<>("Worker was updated successfully.", HttpStatus.OK);
        } else {
            return new ResponseEntity<>("Cannot find Worker with id=" + id, HttpStatus.NOT_FOUND);
        }
    }

    //Удаление рабочего по id
    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteAllTutorials(@PathVariable("id") long id) {
        try {
            int result = workerService.deleteWorker(id);
            if (result == 0) {
                return new ResponseEntity<>("Cannot find Worker with id=" + id, HttpStatus.OK);
            }
            return new ResponseEntity<>("Worker was deleted successfully.", HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>("Cannot delete Worker.", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
