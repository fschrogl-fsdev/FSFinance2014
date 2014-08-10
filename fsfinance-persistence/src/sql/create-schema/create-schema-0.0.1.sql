-- Project:	FSFinance Persistence
-- Info:	Creates the database, tables, sequences, etc.
-- DBMS:	PostgreSQL 9.1+
-- Version:	0.0.1

/*
 * Create TABLES, SEQUENCES, etc.
 */
DROP SEQUENCE IF EXISTS hibernate_sequence;
CREATE SEQUENCE hibernate_sequence start 1 increment 1;

-- Table USERS
DROP TABLE IF EXISTS users CASCADE;
CREATE TABLE users (
	id 		BIGINT PRIMARY KEY,
	username 	VARCHAR(50) NOT NULL UNIQUE,
	password	VARCHAR(255) NOT NULL,
	salt 		VARCHAR(255) NOT NULL,
	forename 	VARCHAR(255),
	surname 	VARCHAR(255),
	email 		VARCHAR(255)
);

-- Table ACCOUNTS
DROP TABLE IF EXISTS accounts CASCADE;
CREATE TABLE accounts (
	id		BIGINT PRIMARY KEY,
	name		VARCHAR(255) NOT NULL,
	description	VARCHAR(255),
	color		CHAR(7),
	id_user		BIGINT NOT NULL
);

-- Table TRANSACTIONS
DROP TABLE IF EXISTS transactions CASCADE;
CREATE TABLE transactions (
	id		BIGINT PRIMARY KEY,
	amount		NUMERIC(19,4) NOT NULL,
	date		TIMESTAMP NOT NULL,
	note		VARCHAR(255),
	id_account	BIGINT NOT NULL,
	id_label	BIGINT
);

-- Table LABELS
DROP TABLE IF EXISTS labels CASCADE;
CREATE TABLE labels (
	id		BIGINT PRIMARY KEY,
	name		VARCHAR(255) NOT NULL,
	description	VARCHAR(255),
	color		CHAR(7),
	id_label	BIGINT,
	id_user		BIGINT NOT NULL
);

/*
 * Create FOREIGN KEYS
 */

ALTER TABLE accounts
  ADD CONSTRAINT accounts_id_user_fkey FOREIGN KEY (id_user) REFERENCES users;

ALTER TABLE transactions 
  ADD CONSTRAINT transactions_id_account_fkey FOREIGN KEY (id_account) REFERENCES accounts,
  ADD CONSTRAINT transactions_id_label_fkey FOREIGN KEY (id_label) REFERENCES labels;
  
ALTER TABLE labels
  ADD CONSTRAINT labels_id_label_fkey FOREIGN KEY (id_label) REFERENCES labels,
  ADD CONSTRAINT labels_id_user_fkey FOREIGN KEY (id_user) REFERENCES labels;
