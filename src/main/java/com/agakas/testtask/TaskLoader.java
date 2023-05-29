package com.agakas.testtask;

import com.agakas.testtask.model.Task;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

public class TaskLoader extends Thread {
    private Connection conn;
    private final Task task;

    public TaskLoader(Task task){
        this.task = task;
    }
    @Override
    public void run() {
        try {
            //Создаю коннекцию
            conn = DriverManager.getConnection("jdbc:postgresql://localhost:5432/testtask_db", "root", "123");
            if (!conn.isClosed()){
                Statement statement = conn.createStatement();
                String sql = "INSERT INTO tasks (title, description, time, status) VALUES('"+ task.getTitle() +"','"+task.getDescription()+"','"+task.getTime().toString()+"','"+task.getStatus()+"');";
                //Отправление запроса
                statement.execute(sql);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
