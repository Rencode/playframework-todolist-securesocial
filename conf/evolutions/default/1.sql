# --- Created by Ebean DDL
# To stop Ebean DDL generation, remove this comment and start using Evolutions

# --- !Ups

create table app_user (
  id                        bigint not null,
  user_id                   varchar(255),
  provider_id               varchar(255),
  first_name                varchar(255),
  last_name                 varchar(255),
  avatar_url                varchar(255),
  email                     varchar(255),
  full_name                 varchar(255),
  hasher                    varchar(255),
  password                  varchar(255),
  salt                      varchar(255),
  constraint pk_app_user primary key (id))
;

create table task (
  id                        bigint not null,
  label                     varchar(255),
  user_name                 varchar(255),
  constraint pk_task primary key (id))
;

create sequence app_user_seq;

create sequence task_seq;




# --- !Downs

SET REFERENTIAL_INTEGRITY FALSE;

drop table if exists app_user;

drop table if exists task;

SET REFERENTIAL_INTEGRITY TRUE;

drop sequence if exists app_user_seq;

drop sequence if exists task_seq;

