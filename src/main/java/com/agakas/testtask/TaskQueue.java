package com.agakas.testtask;

import com.agakas.testtask.model.Task;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.stereotype.Component;

import java.util.concurrent.ConcurrentLinkedQueue;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Component
public class TaskQueue {
    //Класс очереди ориентированный на многопоточность
    ConcurrentLinkedQueue<Task> taskQueue = new ConcurrentLinkedQueue<>();;

    //Метод извлечения и удаления элемента
    public Task pop(){
        Task el = taskQueue.peek();
        taskQueue.poll();
        return el;
    }
    public boolean isEmpty(){
        return taskQueue.isEmpty();
    }
    public int size(){return taskQueue.size();}
    public void add(Task task){
        taskQueue.offer(task);
    }
}
