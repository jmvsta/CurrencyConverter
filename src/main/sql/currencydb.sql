CREATE DATABASE currency
    WITH
    OWNER = postgres
    ENCODING = 'UTF8'
    LC_COLLATE = 'Russian_Russia.1251'
    LC_CTYPE = 'Russian_Russia.1251'
    TABLESPACE = pg_default
    CONNECTION LIMIT = -1;

create sequence exchange_rate_seq
    NO MAXVALUE
    START WITH 1
    INCREMENT BY 1
    NO CYCLE;
alter sequence exchange_rate_seq owner to postgres;

create sequence history_seq
    NO MAXVALUE
    START WITH 1
    INCREMENT BY 1
    NO CYCLE;
alter sequence history_seq owner to postgres;

create sequence user_seq
    NO MAXVALUE
    START WITH 1
    INCREMENT BY 1
    NO CYCLE;
alter sequence user_seq owner to postgres;

create table currency_user
(
    id       bigint not null
        constraint currency_user_pk
            primary key,
    username varchar(60),
    password varchar(255)
);

insert into currency_user (id, username, password)
values (1, 'user', '$2a$10$Nos3kpnPFLau1hvbBW5gT.ruvuXeInoI4DGNf/W1c6pkoE.lZD5O6');

alter table currency_user
    owner to postgres;

create table authority
(
    authority varchar(20) not null
        constraint authority_pkey
            primary key,
    title     varchar(20) not null
);

insert into authority (authority, title)
values ('ROLE_USER', 'USER');


alter table authority
    owner to postgres;

create table currency
(
    valute_id varchar(10)   not null
        constraint currency_pkey
            primary key,
    char_code varchar(10)   not null,
    name      varchar(60)   not null,
    num_code  integer       not null
);

alter table currency
    owner to postgres;

create table exchangerate
(
    id        bigint         not null
        constraint exchangerate_pkey
            primary key,
    valute_id varchar(10)   not null
        constraint fk_exchangerate_currencyid
            references currency(valute_id),
    value     numeric(20, 4) not null,
    date      date
);

alter table exchangerate
    owner to postgres;

create table user_authorities
(
    currency_user_id bigint       not null
        constraint fk_5v8ew4tr4wf5py0ep0o3tt1f3
            references currency_user(id),
    authority        varchar(60) not null
        constraint fk_mtx1akw8m2apxe87q0nvol7ms
            references authority(authority),
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

    source_currency_id varchar(10)   not null
        constraint fk_history_user_source_currency
            references currency(valute_id),
    target_currency_id varchar(60)   not null
        constraint fk_history_user_target_currency
            references currency(valute_id),
    target_sum            numeric(20, 4) not null,
    source_sum            numeric(20, 4) not null,
    date                  timestamp      not null,
    user_id bigint not null
        constraint fk_history_user
        references currency_user(id)
);

alter table history
    owner to postgres;


