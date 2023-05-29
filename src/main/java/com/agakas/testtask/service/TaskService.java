package com.agakas.testtask.service;

import com.agakas.testtask.TaskLoader;
import com.agakas.testtask.model.GeneralTask;
import com.agakas.testtask.model.Task;
import com.agakas.testtask.TaskQueue;
import com.agakas.testtask.repository.TaskRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TaskService {

    //Экземпляр очереди
    @Autowired
    final private TaskQueue myQueue = new TaskQueue();

    @Autowired
    TaskRepository taskRepository;


    public void addTask(Task task){
        myQueue.add(task);
    }
    public ResponseEntity<String>loadTasks(){
        //Если меньше 3 элементов в очереди, то посылки не будет
        if (myQueue.size()<3){
            return new ResponseEntity<>("Not enough items in the queue", HttpStatus.FORBIDDEN);
        }
        //инициализирую 3 потомка с 3 разными элементами очереди и запускаю их (в методе star происходит отсылка)
        TaskLoader thread1 = new TaskLoader(myQueue.pop());
        TaskLoader thread2 = new TaskLoader(myQueue.pop());
        TaskLoader thread3 = new TaskLoader(myQueue.pop());
        thread1.run();
        thread2.run();
        thread3.run();
        return new ResponseEntity<>("Items were loaded to DB", HttpStatus.OK);
    }
    public List<GeneralTask> shortInfoAboutAll(){
        return taskRepository.getAllShortTask();
    }
    public Task fullInfoTask(long id){
        return taskRepository.findById(id);
    }
    public Task readOne(long id){
        return taskRepository.findById(id);
    }
    public int updateTask(Task task){
        return taskRepository.updateTask(task);
    }
    public int assignTask(Task task){
        return taskRepository.assignTask(task);
    }
}
