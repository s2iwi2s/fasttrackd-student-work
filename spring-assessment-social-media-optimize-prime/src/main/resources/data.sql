insert into users (id, active, username, password, joined, email, first_name, last_name, phone) 
values (nextval('user_id_seq'), true, 'test1', 'test1pwd', now(), 'test1@test.com', 'TestFirstName', 'TestLastName', '901-XXX-XXXX');

insert into users (id, active, username, password, joined, email, first_name, last_name, phone) 
values (nextval('user_id_seq'), true, 'test2', 'test2pwd', now(), 'test2@test.com', 'TestFirstName', 'TestLastName', '901-XXX-XXXX');

insert into users (id, active, username, password, joined, email, first_name, last_name, phone) 
values (nextval('user_id_seq'), true, 'test3', 'test3pwd', now(), 'test3@test.com', 'TestFirstName', 'TestLastName', '901-XXX-XXXX');

insert into users (id, active, username, password, joined, email, first_name, last_name, phone) 
values (nextval('user_id_seq'), true, 'test4', 'test4pwd', now(), 'test4@test.com', 'TestFirstName', 'TestLastName', '901-XXX-XXXX');

insert into users (id, active, username, password, joined, email, first_name, last_name, phone) 
values (nextval('user_id_seq'), true, 'test5', 'test5pwd', now(), 'test5@test.com', 'TestFirstName', 'TestLastName', '901-XXX-XXXX');


INSERT INTO tweet(id, content, deleted, posted, author_id, in_reply_to_id, repost_of_id)
VALUES (nextval('tweet_id_seq'), 'test 1 tweet content', false, '2020-10-05 14:01:10-08', (select id from users where username='test1'), null, null);

INSERT INTO tweet(id, content, deleted, posted, author_id, in_reply_to_id, repost_of_id)
VALUES (nextval('tweet_id_seq'), 'test 2 tweet content', false, '2020-10-05 15:01:10-08', (select id from users where username='test2'), null, null);

INSERT INTO tweet(id, content, deleted, posted, author_id, in_reply_to_id, repost_of_id)
VALUES (nextval('tweet_id_seq'), 'test 4 tweet content', false, '2020-10-05 16:01:10-08', (select id from users where username='test4'), null, null);

INSERT INTO tweet(id, content, deleted, posted, author_id, in_reply_to_id, repost_of_id)
VALUES (nextval('tweet_id_seq'), 'test 5 tweet content', false, '2020-11-05 17:01:10-08', (select id from users where username='test5'), null, null);

INSERT INTO tweet(id, content, deleted, posted, author_id, in_reply_to_id, repost_of_id)
VALUES (nextval('tweet_id_seq'), 'test2 tweet reply content', false, '2020-11-06 10:01:10-08', (select id from users where username='test2'), (select id from tweet where author_id in (select id from users where content='test 1 tweet content')), null);

INSERT INTO tweet(id, content, deleted, posted, author_id, in_reply_to_id, repost_of_id)
VALUES (nextval('tweet_id_seq'), 'test2 tweet repost content', false, '2020-11-06 11:01:10-08', (select id from users where username='test2'), null, (select id from tweet where author_id in (select id from users where content='test 1 tweet content')));

INSERT INTO tweet(content, deleted, posted, author_id, repost_of_id, in_reply_to_id, id)
VALUES ('test tweet InReplyTo content', false, '2020-11-06 11:01:10-08', (select id from users where username='test2'), null, 1, nextval('tweet_id_seq'));
INSERT INTO tweet(content, deleted, posted, author_id, repost_of_id, in_reply_to_id, id)
VALUES ('test tweet InReplyTo content ' || (SELECT last_value-1 FROM tweet_id_seq), false, '2020-11-06 11:02:10-08', (select id from users where username='test2'), null, (SELECT last_value-1 FROM tweet_id_seq), nextval('tweet_id_seq'));
INSERT INTO tweet(content, deleted, posted, author_id, repost_of_id, in_reply_to_id, id)
VALUES ('test tweet InReplyTo content ' || (SELECT last_value-1 FROM tweet_id_seq), false, '2020-11-06 11:033:10-08', (select id from users where username='test2'), null, (SELECT last_value-1 FROM tweet_id_seq), nextval('tweet_id_seq'));
INSERT INTO tweet(content, deleted, posted, author_id, repost_of_id, in_reply_to_id, id)
VALUES ('test tweet InReplyTo content ' || (SELECT last_value-1 FROM tweet_id_seq), false, '2020-11-06 11:04:10-08', (select id from users where username='test2'), null, (SELECT last_value-1 FROM tweet_id_seq), nextval('tweet_id_seq'));
INSERT INTO tweet(content, deleted, posted, author_id, repost_of_id, in_reply_to_id, id)
VALUES ('test tweet InReplyTo content ' || (SELECT last_value-1 FROM tweet_id_seq), false, '2020-11-06 11:05:10-08', (select id from users where username='test2'), null, (SELECT last_value-1 FROM tweet_id_seq), nextval('tweet_id_seq'));
INSERT INTO tweet(content, deleted, posted, author_id, repost_of_id, in_reply_to_id, id)
VALUES ('test tweet InReplyTo content ' || (SELECT last_value-1 FROM tweet_id_seq), false, '2020-11-06 11:06:10-08', (select id from users where username='test2'), null, (SELECT last_value-1 FROM tweet_id_seq), nextval('tweet_id_seq'));
INSERT INTO tweet(content, deleted, posted, author_id, repost_of_id, in_reply_to_id, id)
VALUES ('test tweet InReplyTo content ' || (SELECT last_value-1 FROM tweet_id_seq), false, '2020-11-06 11:07:10-08', (select id from users where username='test2'), null, (SELECT last_value-2 FROM tweet_id_seq), nextval('tweet_id_seq'));
INSERT INTO tweet(content, deleted, posted, author_id, repost_of_id, in_reply_to_id, id)
VALUES ('test tweet InReplyTo content ' || (SELECT last_value-1 FROM tweet_id_seq), false, '2020-11-06 11:08:10-08', (select id from users where username='test2'), null, (SELECT last_value-3 FROM tweet_id_seq), nextval('tweet_id_seq'));
INSERT INTO tweet(content, deleted, posted, author_id, repost_of_id, in_reply_to_id, id)
VALUES ('test tweet InReplyTo content ' || (SELECT last_value-1 FROM tweet_id_seq), false, '2020-11-06 11:09:10-08', (select id from users where username='test2'), null, (SELECT last_value-5 FROM tweet_id_seq), nextval('tweet_id_seq'));

update tweet set deleted=true where id in (7,13);

insert into tweet_mentions (tweet_mentions_id, 	mentions_id) 
values ((select id from tweet where content='test 1 tweet content'), (select id from users where username='test3'));

insert into tweet_mentions (tweet_mentions_id, 	mentions_id) 
values ((select id from tweet where content='test 2 tweet content'), (select id from users where username='test3'));

insert into tweet_mentions (tweet_mentions_id, 	mentions_id) 
values ((select id from tweet where content='test 1 tweet content'), (select id from users where username='test4'));

commit; 