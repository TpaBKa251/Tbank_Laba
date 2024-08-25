create table if not exists check_create_table(id bigserial not null);

create table translator (
                            id bigserial not null primary key,
                            ip varchar(100) not null,
                            request varchar(10000000) not null ,
                            answer varchar(10000000) not null
);