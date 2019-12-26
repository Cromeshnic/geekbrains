--DROP TABLE IF EXISTS task
--CREATE TABLE task (id INTEGER PRIMARY KEY AUTOINCREMENT, title TEXT, owner TEXT, assignee TEXT, description TEXT, status TEXT )
--insert into sqlite_sequence (name,seq) values ("task_id",1)
INSERT INTO roles (name) VALUES ('ROLE_USER'), ('ROLE_MANAGER'), ('ROLE_ADMIN');
insert into user(name, password) values('Alice', '$2a$04$Fx/SX9.BAvtPlMyIIqqFx.hLY2Xp8nnhpzvEEVINvVpwIPbA3v/.i');
insert into user(name, password) values('Bob', '$2a$04$Fx/SX9.BAvtPlMyIIqqFx.hLY2Xp8nnhpzvEEVINvVpwIPbA3v/.i');
insert into user(name, password) values('Clark', '$2a$04$Fx/SX9.BAvtPlMyIIqqFx.hLY2Xp8nnhpzvEEVINvVpwIPbA3v/.i');

INSERT INTO users_roles (user_id, role_id) VALUES (1, 1), (1, 2), (1, 3), (2, 1);

insert into task(title, assignee_id, description, status) values('first', (select id from user where name='Alice') , 'test 1', 0);
insert into task(title, assignee_id, description, status) values('second', (select id from user where name='Bob'), 'test 2', 0);
insert into task(title, assignee_id, description, status) values('third', (select id from user where name='Clark'), 'test 3', 1);