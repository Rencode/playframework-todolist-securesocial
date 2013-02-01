# --- Created by Ebean DDL
# To stop Ebean DDL generation, remove this comment and start using Evolutions

# --- !Ups

create table app_user (
  id                        bigint not null,
  user_id                   varchar(255),
  provider_id               varchar(255),
  first_name                varchar(255),
  last_name                 varchar(255),
  email                     varchar(255),
  full_name                 varchar(255),
  hasher                    varchar(255),
  password                  varchar(255),
  constraint pk_app_user primary key (id))
;

create table task (
  id                        bigint not null,
  label                     varchar(255),
  app_user_id               bigint,
  constraint pk_task primary key (id))
;

create sequence app_user_seq;

create sequence task_seq;

alter table task add constraint fk_task_appUser_1 foreign key (app_user_id) references app_user (id) on delete restrict on update restrict;
create index ix_task_appUser_1 on task (app_user_id);



# --- !Downs

SET REFERENTIAL_INTEGRITY FALSE;

drop table if exists app_user;

drop table if exists task;

SET REFERENTIAL_INTEGRITY TRUE;

drop sequence if exists app_user_seq;

drop sequence if exists task_seq;

