--DROP TABLE IF EXISTS task
--CREATE TABLE task (id INTEGER PRIMARY KEY AUTOINCREMENT, title TEXT, owner TEXT, assignee TEXT, description TEXT, status TEXT )
--insert into sqlite_sequence (name,seq) values ("task_id",1)
insert into user(name) values('Alice');
insert into user(name) values('Bob');
insert into user(name) values('Clark');
insert into task(title, assignee_id, description, status) values('first', (select id from user where name='Alice') , 'test 1', 0);
insert into task(title, assignee_id, description, status) values('second', (select id from user where name='Bob'), 'test 2', 0);
insert into task(title, assignee_id, description, status) values('third', (select id from user where name='Clark'), 'test 3', 1);