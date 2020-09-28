
DROP USER 'desafio'@'localhost';

CREATE USER 'desafio'@'%' IDENTIFIED BY 'desafio';

GRANT ALL PRIVILEGES ON *.* TO 'desafio'@'%'  WITH GRANT OPTION;

GRANT ALL PRIVILEGES ON *.* TO 'monty'@'%' WITH GRANT OPTION;

create database desafioluizalabs;

FLUSH PRIVILEGES;