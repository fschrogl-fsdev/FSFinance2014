--
-- FSFinance SQL Scratchpad
--

/*
BEGIN;
ROLLBACK;
COMMIT;
*/

-- USERS
delete from users;

select * from users;
insert into users (id, username, password, salt, forename, surname, email)
  values (1, 'user1', 'password', 'salt', 'joe1', 'doe1', 'joe@doe.com');


-- ACCOUNTS
select * from accounts;
insert into accounts (id, name, description, color, id_user)
  values (1, 'Account1', 'description1', '#abcdef', 1);


-- LABELS
delete from labels;
delete from labels_labels;

select * from labels;
select * from labels_labels;

update labels set id=5 where id=1;

insert into labels (id, name, description, color, id_user) values
  (1, 'Label_Parent', 'Parent-Label', '#abcdef', 1),
  (2, 'Label_Child', 'Child-Label', '#abcdef', 1);

insert into labels_labels (id_label_parent, id_label_child) values
  (1, 2);


-- TRANSACTIONS
delete from transactions;

select * from transactions;

INSERT INTO transactions (id, date, amount, note, id_label, id_account) VALUES (1, '2014-08-31 12:00:00', 12.54, 'note', null, 1);
