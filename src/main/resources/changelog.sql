--liquibase formatted sql

--changeset default:1
create table StepExecuteResult (
  id integer not null primary key autoincrement,
  runId varchar(80),
  startTime INTEGER,
  endTime INTEGER,
  result TEXT,
  ex TEXT
);