CREATE DATABASE IF NOT EXISTS mydatabase;
USE mydatabase;
CREATE TABLE IF NOT EXISTS demo (ping VARCHAR(255));
INSERT INTO demo (ping) VALUES (CONCAT('pong_', @@hostname));