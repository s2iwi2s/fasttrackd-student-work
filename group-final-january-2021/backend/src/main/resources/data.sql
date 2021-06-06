delete from users_projects;
delete from users;
delete from role;
delete from project;
delete from team;
delete from company;

insert into role (id, description, name) 
values (nextval('role_id_seq'), 'This is Admin role', 'admin');
insert into role (id, description, name) 
values (nextval('role_id_seq'), 'This is Internal role', 'internal');

insert into company (id, name, text) 
values (nextval('company_id_seq'), 'Cook Systems. Inc.', 'Cook Systems. Inc.');
insert into company (id, name, text) 
values (nextval('company_id_seq'), 'FedEx', 'FedEx');
insert into company (id, name, text) 
values (nextval('company_id_seq'), 'JB Hunt', 'JB Hunt');
insert into company (id, name, text) 
values (nextval('company_id_seq'), 'Chick-fil-A', 'Chick-fil-A');
insert into company (id, name, text) 
values (nextval('company_id_seq'), 'Walmart', 'Walmart');

insert into team (id, name, text, company_id)  
values (nextval('team_id_seq'), 'Cooksys Admins',  'Administrators in Cook Systems. Inc.', (select id from company where name='Cook Systems. Inc.'));
insert into team (id, name, text, company_id)  
values (nextval('team_id_seq'), 'Wiki Team',  'Wiki Team in Cook Systems. Inc.', (select id from company where name='Cook Systems. Inc.'));

insert into team (id, name, text, company_id)  
values (nextval('team_id_seq'), 'Admin Team',  'Admin Team in FedEx', (select id from company where name='FedEx'));

insert into team (id, name, text, company_id)  
values (nextval('team_id_seq'), 'Fullstack Team',  'Fullstack Team in FedEx', (select id from company where name='FedEx'));


insert into project (id, name, text, team_id)  
values (nextval('project_id_seq'), 'Wiki Application',  'Wiki Application in Cook Systems. Inc.', (select id from team where name='Wiki Team'));

insert into project (id, name, text, team_id)  
values (nextval('project_id_seq'), 'Forms Systems',  'Forms Systems - Fullstack Team in FedEx', (select id from team where name='Fullstack Team'));

insert into project (id, name, text, team_id)  
values (nextval('project_id_seq'), 'Forms Ordering System',  'Forms Ordering System - Fullstack Team in FedEx', (select id from team where name='Fullstack Team'));

insert into users (id, username, password, first_name, last_name, company_id, team_id, role_id) 
values (nextval('user_id_seq'), 'cooksys_admin', 'test',  'Admin', 'Cooksys', (select id from company where name='Cook Systems. Inc.'), (select id from team where name='Cooksys Admins'), (select id from role where name='admin'));

insert into users (id, username, password, first_name, last_name, company_id, team_id, role_id) 
values (nextval('user_id_seq'), 'wpidor', 'test',  'Winston', 'Pidor', (select id from company where name='Cook Systems. Inc.'), (select id from team where name='Wiki Team'), (select id from role where name='internal'));

insert into users (id, username, password, first_name, last_name, company_id, team_id, role_id) 
values (nextval('user_id_seq'), 'mtate', 'test',  'Marcus', 'Tate', (select id from company where name='Cook Systems. Inc.'), (select id from team where name='Wiki Team'), (select id from role where name='internal'));

insert into users (id, username, password, first_name, last_name, company_id, team_id, role_id)  
values (nextval('user_id_seq'), 'skays', 'test',  'Shawn', 'Kays', (select id from company where name='Cook Systems. Inc.'), (select id from team where name='Wiki Team'), (select id from role where name='internal'));

insert into users (id, username, password, first_name, last_name, company_id, team_id, role_id) 
values (nextval('user_id_seq'), 'jwhite', 'test',  'Joel', 'White', (select id from company where name='Cook Systems. Inc.'), (select id from team where name='Wiki Team'), (select id from role where name='internal'));


insert into users (id, username, password, first_name, last_name, company_id, team_id, role_id) 
values (nextval('user_id_seq'), 'fedex_admin', 'test',  'Admin', 'FedEx', (select id from company where name='FedEx'), (select id from team where name='Admin Team'), (select id from role where name='admin'));

insert into users (id, username, password, first_name, last_name, company_id, team_id, role_id) 
values (nextval('user_id_seq'), 'jbond', 'test',  'James', 'Bond', (select id from company where name='FedEx'), (select id from team where name='Fullstack Team'), (select id from role where name='internal'));


commit; 