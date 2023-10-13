--Whatever we have in data.sql will not be executed, if we are connecting to a real database.
--The queries which are present in data.sql are only executed if we're connecting to an in-memory database (like H2 Database)
insert into user_details(id,birth_date,name)
values(10001, current_date(), 'kirti');

insert into user_details(id,birth_date,name)
values(10002, current_date(), 'kirtiiii');

insert into user_details(id,birth_date,name)
values(10003, current_date(), 'kirtika');

insert into user_details(id,birth_date,name)
values(10004, current_date(), 'Ranga');

insert into user_details(id,birth_date,name)
values(10005, current_date(), 'Ravi');

insert into user_details(id,birth_date,name)
values(10006, current_date(), 'Sathish');

insert into post(id,description,user_id)
values(20001,'I want to learn AWS', 10001);

insert into post(id,description,user_id)
values(20002,'I want to learn DevOps', 10001);

insert into post(id,description,user_id)
values(20003,'I want to Get AWS Certified', 10002);

insert into post(id,description,user_id)
values(20004,'I want to learn Multi Cloud', 10002);

insert into post(id,description,user_id)
values(20005,'Microservices', 10004);