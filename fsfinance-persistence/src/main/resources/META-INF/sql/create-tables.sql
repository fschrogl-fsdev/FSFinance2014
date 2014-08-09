CREATE TABLE users (
	id 		BIGINT PRIMARY KEY,
	username 	VARCHAR(50) NOT NULL UNIQUE,
	password	VARCHAR(255) NOT NULL,
	salt 		VARCHAR(255) NOT NULL,
	forename 	VARCHAR(255),
	surname 	VARCHAR(255),
	email 		VARCHAR(255)
);

CREATE SEQUENCE users_id_seq start 1 increment 1;
