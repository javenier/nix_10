-- drop table banks if exists;
-- drop table clients if exists;
-- drop table bank_client if exists;

create table banks
(
    id         bigint auto_increment
        primary key,
    created    datetime(6) null,
    updated    datetime(6) null,
    name varchar(255) not null,
    year_of_foundation int not null,
    bank_type varchar(255) not null
);

create table clients
(
    id               bigint auto_increment
        primary key,
    created          datetime(6)  null,
    updated          datetime(6)  null,
    first_name varchar(255) not null,
    last_name  varchar(255) not null,
    age int not null
);

create table bank_client
(
    bank_id bigint not null,
    client_id   bigint not null,
    primary key (bank_id, client_id),
    foreign key (bank_id) references banks (id) on delete cascade,
    foreign key (client_id) references clients (id) on delete cascade
);