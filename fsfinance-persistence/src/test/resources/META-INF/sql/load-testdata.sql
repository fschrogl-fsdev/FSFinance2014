INSERT INTO users (id, username, password, forename, surname, email, enabled) VALUES (1, 'user1', 'password', 'joe1', 'doe', 'joe1@doe.com', true);
INSERT INTO users (id, username, password, forename, surname, email, enabled) VALUES (2, 'user2', 'password', 'joe2', 'doe', 'joe2@doe.com', true);
INSERT INTO users (id, username, password, forename, surname, email, enabled) VALUES (3, 'user3', 'password', 'joe3', null, 'joe3@doe.com', false);

INSERT INTO accounts (id, name, description, color, id_user) VALUES (1, 'account1', 'description of ac.1', '#abcdef', 1);
INSERT INTO accounts (id, name, description, color, id_user) VALUES (2, 'account2', 'description of ac.2', '#abcdef', 2);
INSERT INTO accounts (id, name, description, color, id_user) VALUES (3, 'account3', 'description of ac.3', '#abcdef', 3);

INSERT INTO labels (id, name, description, color, id_user) VALUES (1, 'label1', 'description of lbl.1', '#abcdef', 1);
INSERT INTO labels (id, name, description, color, id_user) VALUES (2, 'label1', 'description of lbl.1 (copy)', '#abcdef', 1);
INSERT INTO labels (id, name, description, color, id_user) VALUES (3, 'label2', 'description of lbl.2', '#abcdef', 2);

INSERT INTO bookings (id, date, amount, note, id_label, id_account) VALUES (1, '2014-08-03 12:00:00', -12.54, 'note tx.1', 1, 1);
INSERT INTO bookings (id, date, amount, note, id_label, id_account) VALUES (2, '2014-08-28 12:05:00', 112.04, 'note tx.2', 2, 1);
INSERT INTO bookings (id, date, amount, note, id_label, id_account) VALUES (3, '2015-02-22 12:00:00', 12.34, 'note tx.3', 1, 2);
INSERT INTO bookings (id, date, amount, note, id_label, id_account) VALUES (4, '1984-07-14 12:00:00', 17.34, 'note tx.4', 1, 2);
INSERT INTO bookings (id, date, amount, note, id_label, id_account) VALUES (5, '2009-05-11 17:50:00', 1099.34, 'note tx.5', 3, 3);