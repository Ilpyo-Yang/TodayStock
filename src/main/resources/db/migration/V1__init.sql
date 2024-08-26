-- Drop tables if they exist
drop table if exists collect cascade;
drop table if exists company cascade;
drop table if exists company_info cascade;
drop table if exists country cascade;
drop table if exists country_info cascade;
drop table if exists member cascade;
drop table if exists theme cascade;
drop table if exists theme_info cascade;

-- Create tables
create table collect (
    uuid uuid not null,
    member_uuid uuid,
    related_info_uuid uuid,
    type varchar(255),
    summary varchar(255),
    created_date timestamp(6),
    modified_date timestamp(6),
    deleted_date timestamp(6),
    primary key (uuid)
);

create table company (
    uuid uuid not null,
    ticker varchar(30) not null,
    name varchar(50) not null,
    profile varchar(300) not null,
    created_date timestamp(6),
    modified_date timestamp(6),
    deleted_date timestamp(6),
    primary key (uuid)
);

create table company_info (
    uuid uuid not null,
    company_uuid uuid not null,
    summary varchar(1000) not null,
    info varchar(100000) not null,
    created_date timestamp(6),
    modified_date timestamp(6),
    deleted_date timestamp(6),
    primary key (uuid)
);

create table country (
    uuid uuid not null,
    name varchar(50) not null,
    profile varchar(300) not null,
    created_date timestamp(6),
    modified_date timestamp(6),
    deleted_date timestamp(6),
    primary key (uuid)
);

create table country_info (
    uuid uuid not null,
    country_uuid uuid not null,
    summary varchar(1000) not null,
    info varchar(100000) not null,
    created_date timestamp(6),
    modified_date timestamp(6),
    deleted_date timestamp(6),
    primary key (uuid)
);

create table member (
    uuid uuid not null,
    email varchar(30) not null unique,
    nickname varchar(30) unique,
    name varchar(50) not null,
    password varchar(100) not null,
    deeplink varchar(300),
    role varchar(255) not null check (role in ('Member','Admin')),
    created_date timestamp(6),
    modified_date timestamp(6),
    deleted_date timestamp(6),
    primary key (uuid)
);

create table theme (
    uuid uuid not null,
    name varchar(50) not null,
    profile varchar(300) not null,
    created_date timestamp(6),
    modified_date timestamp(6),
    deleted_date timestamp(6),
    primary key (uuid)
);

create table theme_info (
    uuid uuid not null,
    theme_uuid uuid not null,
    summary varchar(1000) not null,
    info varchar(100000) not null,
    created_date timestamp(6),
    modified_date timestamp(6),
    deleted_date timestamp(6),
    primary key (uuid)
);

-- Add foreign key constraints
alter table if exists company_info add constraint FKtgu2bqs0b3ipr5smpqx0h3c6q foreign key (company_uuid) references company;
alter table if exists country_info add constraint FKjgggl55w0fwhxj2v77qi2afk7 foreign key (country_uuid) references country;
alter table if exists theme_info add constraint FK9wwc47nu761j94wpjmvojkpek foreign key (theme_uuid) references theme;
