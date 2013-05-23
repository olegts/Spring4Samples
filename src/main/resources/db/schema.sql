drop table BOOKSTORE if exists;

create table BOOKSTORE (
  ID numeric(32,0) primary key,
  NAME varchar(32) not null,
  AUTHOR varchar(32) not null
);
