INSERT INTO users (id, username, password, salt, forename, surname, email) VALUES (1, 'user1', 'password', 'salt', 'joe1', 'doe', 'joe1@doe.com');
INSERT INTO users (id, username, password, salt, forename, surname, email) VALUES (2, 'user2', 'password', 'salt', 'joe2', 'doe', 'joe2@doe.com');

INSERT INTO accounts (id, name, description, color, id_user) VALUES (1, 'account1', 'description of ac.1', '#abcdef', 1);
INSERT INTO accounts (id, name, description, color, id_user) VALUES (2, 'account2', 'description of ac.2', '#abcdef', 2);

INSERT INTO labels (id, name, description, color, id_user) VALUES (1, 'label1', 'description of lbl.1', '#abcdef', 1);
INSERT INTO labels (id, name, description, color, id_user) VALUES (2, 'label2', 'description of lbl.2', '#abcdef', 1);

INSERT INTO transactions (id, date, amount, note, id_label, id_account) VALUES (1, '2014-08-31 12:00:00', 12.54, 'note tx.1', 1, 1);
INSERT INTO transactions (id, date, amount, note, id_label, id_account) VALUES (2, '2014-08-31 12:00:00', 112.04, 'note tx.1', 2, 1);
INSERT INTO transactions (id, date, amount, note, id_label, id_account) VALUES (3, '2014-08-31 12:00:00', 12.34, 'note tx.1', 1, 2);