drop table if exists users;

create table users (
    id int generated always as identity primary key,
    username varchar(30) unique,
    password varchar(255)
);