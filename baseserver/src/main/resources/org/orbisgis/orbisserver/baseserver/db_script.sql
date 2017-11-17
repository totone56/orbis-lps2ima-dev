-- Script of the initiation of the database.
DROP TABLE IF EXISTS session_table;
CREATE TABLE session_table (username VARCHAR(50), password VARCHAR(50), expirationTime LONG, poolSize INT);
INSERT INTO session_table VALUES ('admin', 'admin', 172800000, 10);