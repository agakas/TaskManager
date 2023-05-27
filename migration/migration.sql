CREATE TABLE IF NOT EXISTS workers (
  id serial PRIMARY KEY,
  name varchar(255) NOT NULL,
  position varchar(255) NOT NULL,
  avatar varchar(255) NOT NULL
);

CREATE TABLE IF NOT EXISTS tasks (
  id serial PRIMARY KEY,
  title varchar(255) NOT NULL,
  description varchar(255) NOT NULL,
  time timestamp NOT NULL,
  status varchar(255) NOT NULL,
  performer integer REFERENCES workers(id)
);

INSERT INTO workers (name, position, avatar) VALUES('Sergey','Relaxer','sdfvdc')
INSERT INTO tasks (title, description, time, status, performer) VALUES('Task3', 'bulbulbul', to_timestamp('2023-06-07 16:25:00', 'YYYY-MM-DD HH24:MI:SS'), 'dev', 3)
