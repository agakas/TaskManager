# TestTask

Простая реализация менеджера задач на технологиях Java, SpringBoot, JDBC и 
PostgreSQL

### Соответствие техзаданию💻

>## 1. Организовать простую структуру БД при запуске приложения: связанные таблицы Tasks и Workers.
> 
> ### Решение:
> 
> Файлы в директории **src/main/resources** отвечают за поделючение к базе данных и создание сущностей, если их ещё не существует
> 
> **application.properties**
> 
> Подключение к БД
> 
> ```
> spring.datasource.driver-class-name=org.postgresql.Driver
> spring.datasource.url= jdbc:postgresql://localhost:5432/testtask_db
> spring.datasource.username= root
> spring.datasource.password= 123
>```
> 
> Генерация базовой структуры БД
>```
>spring.datasource.initialization-mode=ALWAYS
>spring.datasource.schema=classpath*:database/initDB.sql
>```
> 
>**database/initDB.sql**
> 
> Скрипт для генерации таблиц
> 
> ```roomsql
> CREATE TABLE IF NOT EXISTS workers (
> id serial PRIMARY KEY,
> name varchar(255) NOT NULL,
> position varchar(255) NOT NULL,
> avatar varchar(255) NOT NULL
> );
>
> CREATE TABLE IF NOT EXISTS tasks (
> id serial PRIMARY KEY,
> title varchar(255) NOT NULL,
> description varchar(255) NOT NULL,
> time timestamp NOT NULL,
> status varchar(255) NOT NULL,
> performer integer REFERENCES workers(id)
> );
>```

>## 2. Принимать задачу и складывать в очередь, реализованную в сервисе, инструментами java.(без внешних MQ и т.д.) 
> 
> ### Решение:
> 
> Реализуется с помощью запроса:
> 
>```
>POST /task/addTask
>```
>В теле запроса передаётся объект Task
> 
> ```json
> {
>   "title": "task1",
>   "description": "Hello world",
>   "time": "2023-06-07T16:30:00",
>   "status": "test"
> }
>```
> 
> При создании задачи исполнитель **не назначается**
> 
>В сервисе TaskService задачи помещаются в очередь класса TaskQueue, реализованной на основе
> класса очереди **ConcurrentLinkedQueue**, которая ориентирована на многопоточное исполнение
> 

>## 3. Считывать 3 задачи из реализованной очереди и складывать их в БД несколькими потоками
>
> ### Решение:
> 
> Реализуется с помощью запроса:
> 
> ```
> GET /task/loadTasks
> ```
> 
> Данный запрос создаёт в сервисе TaskService создаёт три экземпляра класса TaskLoader созданный на основе класса Thread.  Каждому из них присваивается по элементу очереди и затем потоки запускаются
> 
> ```java
> public ResponseEntity<String>loadTasks(){
> if (myQueue.size()<3){
>       return new ResponseEntity<>("Not enough items in the queue", HttpStatus.FORBIDDEN);
> }
> TaskLoader thread1 = new TaskLoader(myQueue.pop());
> TaskLoader thread2 = new TaskLoader(myQueue.pop());
> TaskLoader thread3 = new TaskLoader(myQueue.pop());
> thread1.run();
> thread2.run();
> thread3.run();
> return new ResponseEntity<>("Items were loaded to DB", HttpStatus.OK);
> }
>```
> В экземплярах класса TaskService создаются коннекции к базе данных, а затем в неё отправляются данные (элемент очереди), которым мы инициализировали каждый экземпляр класса
> 
> ```java
> package com.agakas.testtask;
> import com.agakas.testtask.model.Task;
> import org.springframework.jdbc.core.JdbcTemplate;
>
> import java.sql.Connection;
> import java.sql.DriverManager;
> import java.sql.SQLException;
> import java.sql.Statement;
>
> public class TaskLoader extends Thread {
> private Connection conn;
> private final Task task;
> 
> public TaskLoader(Task task){
>   this.task = task;
> }
> @Override
> public void run() {
>   try {
>       conn = DriverManager.getConnection("jdbc:postgresql://localhost:5432/testtask_db", "root", "123");
>           if (!conn.isClosed()){
>               Statement statement = conn.createStatement();
>               String sql = "INSERT INTO tasks (title, description, time, status) VALUES('"+ task.getTitle() +"','"+task.getDescription()+"','"+task.getTime().toString()+"','"+task.getStatus()+"');";
>               statement.execute(sql);
>           }
>   } catch (SQLException e) {
>       throw new RuntimeException(e);
>       }
>    }
>   }
>```

> ## 4. Выдать все задачи из базы в списке с сокращенными данными (id, title, status).
> 
> ### Решение:
> 
> Реализуется с помощью запроса:
> 
> ```
> GET /task/allTaskShort
>```
> 
> Пример ответа на запрос:
> 
> ```json
>   [
>     {
>     "id": 1,
>     "title": "Tasker",
>     "status": "dev"
>     },
>     {
>     "id": 2,
>     "title": "Managment",
>     "status": "test"
>     }
>   ] 
> ```

> ## 5. Выдавать задачу по id с полным описанием.
> 
> ### Решение:
> 
> Реализуется с помощью запроса
> ```
> GET /task/allTaskFull
> ```
> 
> Пример ответа на запрос
> ```json
>     {
>     "id": 1,
>     "title": "Tasker",
>     "status": "dev",
>     "description": "blablabla",
>     "time": "2023-06-05T15:25:00",
>     "performer": 1
>     }
>     
> ```

> ## 6. Изменить задачу по id (все кроме id и performer).
> 
> ### Решение:
> 
> Реализуется с помощью запроса:
>  ```
> PATCH /task/update/{id} - задачи
> ```
> 
> Пример тела запроса:
> ```json
>     {
>     "title": "Delivery",
>     "status": "done",
>     "description": "blablabla",
>     "time": "2023-06-05T15:25:00",
>     }
> ```

> ## 7. Назначить на задачу исполнителя.
>
> ### Решение:
>
> Реализуется с помощью запроса:
>  ```
> PATCH /assign/{id} - задачи
> ```
> Пример тела запроса:
> ```json
>     {
>     "performer": 3
>     }
> ```
> Необходимо отметить, что назначить на задачу исполнителя можно только один раз 
> на каждую задачу. Если у задачи уже есть исполнитель следующий код не даст изменить его в БД
> 
> ```java
>       if (_task.getPerformer() == null) {
>           _task.setPerformer(task.getPerformer());
>           taskService.assignTask(_task);
>           return new ResponseEntity<>("Task was assigned successfully.", HttpStatus.OK);
>           }
>           else{
>               return new ResponseEntity<>("There is already an worker for the task id=" + id, HttpStatus.FORBIDDEN);
>           }
>       }
> ```

