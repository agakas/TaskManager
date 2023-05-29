package com.agakas.testtask.repository;

import com.agakas.testtask.model.GeneralTask;
import com.agakas.testtask.model.Task;
import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.IncorrectResultSizeDataAccessException;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;

@AllArgsConstructor
@RequiredArgsConstructor
@Repository
public class TaskRepositoryImpl implements TaskRepository{

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Override
    public int loadToDB(Task task){
        return jdbcTemplate.update("INSERT INTO tasks (title, description, time, status) VALUES(?,?,?,?)",
                task.getTitle(), task.getDescription(), task.getTime(), task.getStatus());
    }
    @Override
    public List<GeneralTask> getAllShortTask(){
        String q = "SELECT id, title, status FROM tasks;";
        return jdbcTemplate.query(q, BeanPropertyRowMapper.newInstance(GeneralTask.class));
    }
    @Override
    public Task findById(long id){
        try {
            return jdbcTemplate.queryForObject("SELECT * FROM tasks WHERE id=?",
                    BeanPropertyRowMapper.newInstance(Task.class), id);
        } catch (IncorrectResultSizeDataAccessException e) {
            return null;
        }
    }
    @Override
    public int updateTask(Task task) {
        return jdbcTemplate.update("UPDATE tasks SET title=?, description=?, time=?, status=? WHERE id=?",
                task.getTitle(), task.getDescription(), task.getTime(), task.getStatus(),task.getId());
    }
    @Override
    public int assignTask(Task task) {
        return jdbcTemplate.update("UPDATE tasks SET performer=? WHERE id=?",
                task.getPerformer(),task.getId());
    }
}
