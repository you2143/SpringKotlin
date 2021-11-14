CREATE DATABASE IF NOT EXISTS springDatabase;

CREATE TABLE IF NOT EXISTS task (
    id      INT AUTO_INCREMENT, 
    content TEXT,
    done    BOOLEAN,
    PRIMARY KEY (id)    
);
