drop table if exists anniversary
drop table if exists anniversary
drop table if exists notice
drop table if exists notice
drop table if exists notice_device
drop table if exists notice_device
create table anniversary (
                             lunar_date date not null,
                             solar_date date not null,
                             id bigint not null auto_increment,
                             content varchar(255),
                             device_uuid varchar(255) not null,
                             title varchar(255) not null,
                             primary key (id)
) engine=InnoDB
create table anniversary (
                             lunar_date date not null,
                             solar_date date not null,
                             id bigint not null auto_increment,
                             content varchar(255),
                             device_uuid varchar(255) not null,
                             title varchar(255) not null,
                             primary key (id)
) engine=InnoDB
create table notice (
                        notice_type tinyint not null,
                        anniversary_id bigint not null,
                        id bigint not null auto_increment,
                        primary key (id)
) engine=InnoDB
create table notice (
                        notice_type tinyint not null,
                        anniversary_id bigint not null,
                        id bigint not null auto_increment,
                        primary key (id)
) engine=InnoDB
create table notice_device (
                               id bigint not null auto_increment,
                               device_uuid varchar(255) not null,
                               device_status enum ('ON','OFF') not null,
                               primary key (id)
) engine=InnoDB
create table notice_device (
                               id bigint not null auto_increment,
                               device_uuid varchar(255) not null,
                               device_status enum ('ON','OFF') not null,
                               primary key (id)
) engine=InnoDB
