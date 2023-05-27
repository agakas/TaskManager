package com.agakas.testtask.repository;

import com.agakas.testtask.model.Task;
import com.agakas.testtask.model.Worker;
import com.agakas.testtask.model.WorkerAndShortTaskInfo;
import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.IncorrectResultSizeDataAccessException;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;

@AllArgsConstructor
@RequiredArgsConstructor
@Repository
public class WorkerRepositoryImpl implements WorkerRepository {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Override
    public WorkerAndShortTaskInfo getWorkerAndShortTask(long id){
        String query1 = "SELECT * FROM workers WHERE id=?";
        String query2 = "SELECT id, title, status FROM tasks WHERE performer=?";

        WorkerAndShortTaskInfo worker = new WorkerAndShortTaskInfo();
        worker.setWorker(jdbcTemplate.queryForObject(query1, BeanPropertyRowMapper.newInstance(Worker.class), id));
        worker.setTasks(jdbcTemplate.query(query2, BeanPropertyRowMapper.newInstance(Task.class), id));

        return worker;
    }
    //Добавление нового работника
    @Override
    public int addWorker(Worker worker){
        return jdbcTemplate.update("INSERT INTO workers (name, position, avatar) VALUES(?,?,?)",
                worker.getName(), worker.getPosition(), worker.getAvatar());
    }
    //Получение данных работника и его задач по id работника
    @Override
    public Worker findById(long id) {
        try {
            return jdbcTemplate.queryForObject("SELECT * FROM workers WHERE id=?",
                    BeanPropertyRowMapper.newInstance(Worker.class), id);
        } catch (IncorrectResultSizeDataAccessException e) {
            return null;
        }
    }
    //Получение списка всех работников
    @Override
    public List<Worker> getAllWorkers() {
        String q = "SELECT * FROM workers;";
        return jdbcTemplate.query(q, BeanPropertyRowMapper.newInstance(Worker.class));
    }

    //Обновление данных работника
    @Override
    public int updateWorker(Worker worker) {
        return jdbcTemplate.update("UPDATE workers SET name=?, position=?, avatar=? WHERE id=?",
                worker.getName(), worker.getPosition(), worker.getAvatar(), worker.getId());
    }

    //Удаление работника по id
    @Override
    public int deleteWorkerById(long id) {
        return jdbcTemplate.update("DELETE from workers WHERE id=?", id);
    }
}

