--DROP TABLE IF EXISTS task
--CREATE TABLE task (id INTEGER PRIMARY KEY AUTOINCREMENT, title TEXT, owner TEXT, assignee TEXT, description TEXT, status TEXT )
--insert into sqlite_sequence (name,seq) values ("task_id",1)
insert into task(title, assignee, description, status) values('first', 'Alice', 'test 1', 'open');
insert into task(title, assignee, description, status) values('second', 'Bob', 'test 2', 'open');
insert into task(title, assignee, description, status) values('third', 'Clark', 'test 3', 'in progress');