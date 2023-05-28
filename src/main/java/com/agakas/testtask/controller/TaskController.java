package com.agakas.testtask.controller;

import com.agakas.testtask.model.GeneralTask;
import com.agakas.testtask.model.Task;
import com.agakas.testtask.service.TaskService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/task")
public class TaskController {

    private final TaskService taskService;

    @Autowired
    public TaskController(TaskService taskService){
        this.taskService = taskService;
    }


    @PostMapping("/addTask")
    public ResponseEntity<String> createTask(@RequestBody Task task) {
        try {
            taskService.addTask(new Task(task.getTitle(), task.getDescription(), task.getTime(), task.getStatus()));
            return new ResponseEntity<>("The task has been added to the queue", HttpStatus.CREATED);
        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
    @GetMapping("/loadTasks")
    public ResponseEntity<String> loadTasks() {
        return taskService.loadTasks();
    }
    //Эндпоинт для краткой информации по всем задачам
    @GetMapping("/allTaskShort")
    public ResponseEntity<List<GeneralTask>> getAllTaskShort() {
        List<GeneralTask> tasks = taskService.shortInfoAboutAll();
        return new ResponseEntity<>(tasks, HttpStatus.OK);
    }
    //Эндпоинт для полной информации по всем задачам
    @GetMapping("/allTaskFull")
    public ResponseEntity<List<Task>> getAllTaskFull() {
        List<Task> tasks = taskService.fullInfoAboutAll();
        return new ResponseEntity<>(tasks, HttpStatus.OK);
    }
    //Эндпойнт для обновления задачи (кроме id и performer)
    @PatchMapping("/update/{id}")
    public ResponseEntity<String> updateTask(@PathVariable("id") long id, @RequestBody Task task) {
        Task _task = taskService.readOne(id);

        if (_task != null) {
            _task.setTitle(task.getTitle());
            _task.setDescription(task.getDescription());
            _task.setTime(task.getTime());
            _task.setStatus(task.getStatus());

            taskService.updateTask(_task);
            return new ResponseEntity<>("Task was updated successfully.", HttpStatus.OK);
        } else {
            return new ResponseEntity<>("Cannot find Task with id=" + id, HttpStatus.NOT_FOUND);
        }
    }

    //Эндпоинт для назначение рабочего на задачу (работает только с задачей без исполнителя)
    @PatchMapping("/assign/{id}")
    public ResponseEntity<String> assignTask(@PathVariable("id") long id, @RequestBody Task task) {
        Task _task = taskService.readOne(id);
        if (_task != null) {
            if (_task.getPerformer() == null) {
                _task.setPerformer(task.getPerformer());
                taskService.assignTask(_task);
                return new ResponseEntity<>("Task was assigned successfully.", HttpStatus.OK);
            }
            else{
                return new ResponseEntity<>("There is already an worker for the task id=" + id, HttpStatus.FORBIDDEN);
            }
        }
        else {
            return new ResponseEntity<>("Cannot find Task with id=" + id, HttpStatus.NOT_FOUND);
        }
    }
}
