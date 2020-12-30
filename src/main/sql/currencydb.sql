CREATE DATABASE currency
    WITH
    OWNER = postgres
    ENCODING = 'UTF8'
    LC_COLLATE = 'Russian_Russia.1251'
    LC_CTYPE = 'Russian_Russia.1251'
    TABLESPACE = pg_default
    CONNECTION LIMIT = -1;

create sequence currency_seq START 1;
alter sequence currency_seq owner to postgres;

create sequence history_seq START 1;
alter sequence history_seq owner to postgres;

create sequence user_seq START 1;
alter sequence user_seq owner to postgres;

create table currency_user
(
    id       bigint not null
        constraint currency_user_pk
            primary key,
    username varchar(255),
    password varchar(255)
);

insert into currency_user (id, username, password)
values (1, 'user', '$2a$10$Nos3kpnPFLau1hvbBW5gT.ruvuXeInoI4DGNf/W1c6pkoE.lZD5O6');

alter table currency_user
    owner to postgres;

create table authority
(
    authority varchar(255) not null
        constraint authority_pkey
            primary key,
    title     varchar(255) not null
);

insert into authority (authority, title)
values ('ROLE_USER', 'USER');


alter table authority
    owner to postgres;

create table currency
(
    id        bigint         not null
        constraint currency_pkey
            primary key,
    char_code varchar(255)   not null,
    name      varchar(255)   not null,
    num_code  integer        not null,
    value     numeric(20, 4) not null,
    valute_id varchar(255)   not null
);

alter table currency
    owner to postgres;


create table user_authorities
(
    currency_user_id bigint       not null
        constraint fk_5v8ew4tr4wf5py0ep0o3tt1f3
            references currency_user,
    authority        varchar(255) not null
        constraint fk_mtx1akw8m2apxe87q0nvol7ms
            references authority,
    constraint user_authorities_pkey
        primary key (currency_user_id, authority)
);

insert into user_authorities (currency_user_id, authority)
values (1, 'ROLE_USER');

alter table user_authorities
    owner to postgres;


create table history
(
    id                    bigint         not null
        constraint history_pkey
            primary key,
    date                  timestamp      not null,
    source_currency_title varchar(60)   not null,
    source_sum            numeric(20, 4) not null,
    target_currency_title varchar(60)   not null,
    target_sum            numeric(20, 4) not null
);

alter table history
    owner to postgres;


